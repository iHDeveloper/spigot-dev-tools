package me.ihdeveloper.spigot.devtools.api;

import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import me.ihdeveloper.spigot.devtools.api.message.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public abstract class AbstractSpigotDevTools implements SpigotDevTools {

    protected final byte protocolMajor;
    protected final byte protocolMinor;
    protected final Plugin plugin;
    protected final Map<UUID, SDTContainer> containers;
    protected final Map<String, List<MessageHandler>> messageHandlers;
    protected AuthorizationHandler authorizationHandler;
    protected boolean autoDiscovery = true;

    private Watcher watcher;
    private SDTProfiler profiler;
    private SDTServerWall serverWall;
    private Logger logger;

    public AbstractSpigotDevTools(Plugin plugin, byte major, byte minor) {
        this.plugin = plugin;
        this.protocolMajor = major;
        this.protocolMinor = minor;
        this.containers = new TreeMap<>();
        this.messageHandlers = new TreeMap<>();
    }

    /**
     * Should be called to dispose all the references
     */
    public void dispose() {
        this.containers.clear();
        this.messageHandlers.clear();
    }

    @Override
    public SDTContainer hello(Player player, byte major, byte minor) {
        if (major != protocolMajor)
            return null;

        if (minor < protocolMinor)
            return null;

        if (authorizationHandler == null)
            return null;

        if (!authorizationHandler.accept(player))
            return null;

        SDTContainer container = getNewContainer(player.getUniqueId());
        this.containers.put(player.getUniqueId(), container);
        this.sendServerWall(player);
        this.sendLoggerCache(player);
        return container;
    }

    @Override
    public void registerHandler(String name, MessageHandler handler) {
        messageHandlers.computeIfAbsent(name, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public void processMessage(String name, Player player, DataInputStream input) {
        List<MessageHandler> handlers = messageHandlers.get(name);

        if (handlers == null)
            return;

        for (MessageHandler handler : handlers) {
            handler.processMessage(this, player, input);
        }
    }

    @Override
    public void setAuthorizationHandler(AuthorizationHandler handler) {
        this.authorizationHandler = handler;
    }

    @Override
    public void setAutoDiscovery(boolean autoDiscovery) {
        this.autoDiscovery = autoDiscovery;
    }

    @Override
    public boolean hasSaidHello(Player player) {
        return containers.containsKey(player.getUniqueId());
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public Watcher getWatcher() {
        return (watcher == null) ? (watcher = getNewWatcher()) : watcher;
    }

    @Override
    public SDTProfiler getProfiler() {
        return (profiler == null) ? (profiler = getNewProfiler()) : profiler;
    }

    @Override
    public SDTServerWall getServerWall() {
        return (serverWall == null) ? (serverWall = getNewServerWall()) : serverWall;
    }

    @Override
    public Logger logger() {
        return (logger == null) ? (logger = getNewLogger()) : logger;
    }

    @Override
    public SDTContainer getContainer(Player player) {
        return containers.get(player.getUniqueId());
    }

    @Override
    public Collection<SDTContainer> getContainers() {
        return containers.values();
    }

    /**
     * Functionality that will be useful for other implementations
     */

    @Override
    public void send(SDTContainer container, byte[] data) {
        send(container.getPlayer(), data);
    }

    @Override
    public void send(Player player, byte[] data) {
        if (!containers.containsKey(player.getUniqueId()))
            return;

        player.sendPluginMessage(getPlugin(), "Spigot|DevTools", data);
    }

    @Override
    public void broadcast(byte[] data) {
        for (SDTContainer container : getContainers()) {
            send(container, data);
        }
    }

    /**
     * Required to be implemented by the subclass.
     * It provides the ability to choose the implementation of the tools you want to use.
     */
    public abstract Watcher getNewWatcher();
    public abstract SDTProfiler getNewProfiler();
    public abstract SDTServerWall getNewServerWall();
    public abstract Logger getNewLogger();

    /**
     * Required to be implemented by the subclass in order to function.
     * It provides flexibility in the implementation
     */
    public abstract SDTContainer getNewContainer(UUID uniqueId);
    public abstract void sendServerWall(Player player);
    public abstract void sendLoggerCache(Player player);
}
