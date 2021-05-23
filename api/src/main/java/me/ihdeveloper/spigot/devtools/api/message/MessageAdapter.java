package me.ihdeveloper.spigot.devtools.api.message;

import me.ihdeveloper.spigot.devtools.api.SpigotDevTools;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public abstract class MessageAdapter implements MessageHandler {

    protected ByteArrayOutputStream newOutputStream() {
        return new ByteArrayOutputStream();
    }

    protected DataOutputStream toDataOutput(ByteArrayOutputStream stream) {
        return new DataOutputStream(stream);
    }

    protected void sendMessage(SpigotDevTools spigotDevTools, Player player, ByteArrayOutputStream stream) {
        player.sendPluginMessage(spigotDevTools.getPlugin(), "Spigot|DevTools", stream.toByteArray());
    }

}
