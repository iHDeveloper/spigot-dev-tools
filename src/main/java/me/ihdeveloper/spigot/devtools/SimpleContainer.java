package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SDTContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SimpleContainer implements SDTContainer {

    private final UUID uuid;

    public SimpleContainer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
