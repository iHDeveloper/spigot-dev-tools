package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.AbstractSpigotDevTools;
import me.ihdeveloper.spigot.devtools.api.Logger;
import me.ihdeveloper.spigot.devtools.api.SDTContainer;
import me.ihdeveloper.spigot.devtools.api.SDTProfiler;
import me.ihdeveloper.spigot.devtools.api.SDTServerWall;
import me.ihdeveloper.spigot.devtools.api.Watcher;
import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class SimpleSpigotDevTools extends AbstractSpigotDevTools {

    public SimpleSpigotDevTools(Plugin plugin, byte major, byte minor) {
        super(plugin, major, minor);
    }

    /**
     * Internal functions
     */
    public AuthorizationHandler getAuthorizationHandler() {
        return authorizationHandler;
    }

    public boolean isAutoDiscovery() {
        return autoDiscovery;
    }

    public void remove(UUID uniqueId) {
        containers.remove(uniqueId);
    }

    public void forceSend(Player player, byte[] data) { player.sendPluginMessage(getPlugin(), "Spigot|DevTools", data); }

    @Override
    public Watcher getNewWatcher() {
        return new SimpleWatcher();
    }

    @Override
    public SDTProfiler getNewProfiler() {
        return new SimpleProfiler();
    }

    @Override
    public SDTServerWall getNewServerWall() {
        return new SimpleServerWall();
    }

    @Override
    public Logger getNewLogger() {
        return new SimpleLogger(getPlugin().getDataFolder());
    }

    @Override
    public SDTContainer getNewContainer(UUID uniqueId) {
        return new SimpleContainer(uniqueId);
    }

    @Override
    public void sendServerWall(Player player) {
        ((SimpleServerWall)getServerWall()).sendWall(player);
    }

    @Override
    public void sendLoggerCache(Player player) {
        logger().sendCache(player);
    }

}
