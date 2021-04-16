package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SPTContainer;
import me.ihdeveloper.spigot.devtools.api.SpigotDevTools;
import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import me.ihdeveloper.spigot.devtools.auth.OPAuthorizationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements SpigotDevTools {

    private final Map<UUID, SPTContainer> containers = new HashMap<>();
    private AuthorizationHandler authorizationHandler;

    @Override
    public SPTContainer hello(Player player) {
        if (authorizationHandler == null)
            return null;

        if (!authorizationHandler.accept(player))
            return null;


        return containers.put(player.getUniqueId(), new BasicSPTContainer(player));
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
    public SPTContainer getContainer(Player player) {
        return containers.get(player.getUniqueId());
    }

    @Override
    public void onEnable() {
        /* Default Setup */
        setAuthorizationHandler(new OPAuthorizationHandler());

        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§a is enabled!§e Plugin By§3 @iHDeveloper");
        getServer().getConsoleSender().sendMessage("§6WARNING!§e The authorization method: " + authorizationHandler.toString());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "Spigot|DevTools");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Spigot|DevTools", new ChannelListener(this));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
