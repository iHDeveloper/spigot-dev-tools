package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SPTContainer;
import org.bukkit.entity.Player;

public class SimpleSPTContainer implements SPTContainer {

    private final Player player;

    public SimpleSPTContainer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
