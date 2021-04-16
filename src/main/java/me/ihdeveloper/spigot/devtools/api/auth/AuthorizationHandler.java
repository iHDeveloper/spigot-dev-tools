package me.ihdeveloper.spigot.devtools.api.auth;

import org.bukkit.entity.Player;

public interface AuthorizationHandler {

    boolean accept(Player player);

}
