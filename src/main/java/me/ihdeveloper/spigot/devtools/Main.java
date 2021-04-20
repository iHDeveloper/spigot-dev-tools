package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.SDTContainer;
import me.ihdeveloper.spigot.devtools.api.SpigotDevTools;
import me.ihdeveloper.spigot.devtools.api.Watcher;
import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import me.ihdeveloper.spigot.devtools.api.message.MessageHandler;
import me.ihdeveloper.spigot.devtools.auth.OPAuthorizationHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin implements SpigotDevTools, Listener {
    private static final byte protocolMajor = 0;
    private static final byte protocolMinor = 1;

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private final Map<UUID, SDTContainer> containers = new HashMap<>();
    private final Map<String, List<MessageHandler>> messageHandlers = new HashMap<>();
    private final SimpleWatcher simpleWatcher = new SimpleWatcher();
    private AuthorizationHandler authorizationHandler;

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

        SDTContainer container = new SimpleSDTContainer(player.getUniqueId());
        containers.put(player.getUniqueId(), container);
        return container;
    }

    @Override
    public void registerHandler(String name, MessageHandler handler) {
        messageHandlers.computeIfAbsent(name, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public void processMessage(String name, Player player, DataInputStream input) {
        List<MessageHandler> handlers = messageHandlers.get(name);

        if (handlers == null) {
            return;
        }

        for (MessageHandler handler : handlers) {
            handler.processMessage(this, player, input);
        }
    }

    @Override
    public void setAuthorizationHandler(AuthorizationHandler authorizationHandler) {
        this.authorizationHandler = authorizationHandler;
    }

    @Override
    public boolean hasSaidHello(Player player) {
        return containers.containsKey(player.getUniqueId());
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public Watcher getWatcher() {
        return simpleWatcher;
    }

    @Override
    public SDTContainer getContainer(Player player) {
        return containers.get(player.getUniqueId());
    }

    @Override
    public Collection<SDTContainer> getContainers() {
        return containers.values();
    }

    @Override
    public void onEnable() {
        /* Default Setup */
        setAuthorizationHandler(new OPAuthorizationHandler());

        instance = this;
        DevTools.setInstance(this);

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getScheduler().runTaskTimer(this, new TPSTask(), 0L, 30 * 20L);

        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§a is enabled!§e Plugin By§3 @iHDeveloper");
        getServer().getConsoleSender().sendMessage("§bINFO!§e Protocol Version:§7 v" + protocolMajor + "." + protocolMinor);
        getServer().getConsoleSender().sendMessage("§6WARNING!§e The authorization method: " + authorizationHandler.toString());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "Spigot|DevTools");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Spigot|DevTools", new ChannelListener(this));
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        containers.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
