package me.ihdeveloper.spigot.devtools.api;

import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface SpigotDevTools {

    SPTContainer hello(Player player);

    void setAuthorizationHandler(AuthorizationHandler handler);

    boolean hasSaidHello(Player player);

    Plugin getPlugin();

    SPTContainer getContainer(Player player);

}
