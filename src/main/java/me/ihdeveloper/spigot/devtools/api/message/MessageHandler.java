package me.ihdeveloper.spigot.devtools.api.message;

import me.ihdeveloper.spigot.devtools.api.SpigotDevTools;
import org.bukkit.entity.Player;

import java.io.DataInputStream;

public interface MessageHandler {

    void processMessage(SpigotDevTools spigotDevTools, Player player, DataInputStream input);

}
