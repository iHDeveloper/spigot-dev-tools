package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SPTContainer;
import org.bukkit.entity.Player;

public class BasicSPTContainer implements SPTContainer {

    private final Player player;

    public BasicSPTContainer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
