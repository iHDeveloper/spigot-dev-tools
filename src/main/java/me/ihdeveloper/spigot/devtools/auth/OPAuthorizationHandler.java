package me.ihdeveloper.spigot.devtools.auth;

import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import org.bukkit.entity.Player;

public class OPAuthorizationHandler implements AuthorizationHandler {

    @Override
    public boolean accept(Player player) {
        return player.isOp();
    }

    @Override
    public String toString() {
        return "§4ONLY OP";
    }
}
