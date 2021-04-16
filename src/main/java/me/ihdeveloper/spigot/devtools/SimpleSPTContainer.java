package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SPTContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SimpleSPTContainer implements SPTContainer {

    private final UUID uuid;

    public SimpleSPTContainer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
