package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SPTContainer;
import me.ihdeveloper.spigot.devtools.api.SpigotDevTools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelListener implements PluginMessageListener {
    private static final String CHANNEL_NAME = "Spigot|DevTools";

    private final SpigotDevTools spigotDevTools;

    public ChannelListener(SpigotDevTools instance) {
        this.spigotDevTools = instance;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals(CHANNEL_NAME)) {
            return;
        }

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(message))) {
            processMessage(player, input);
        } catch (IOException exception) {
            player.kickPlayer("Spigot Dev Tools: Something wrong happened! Check the console for logs!");
            exception.printStackTrace();
        }
    }

    private void processMessage(Player player, DataInputStream input) throws IOException {
        String type = input.readUTF();

        /* Process hello message */
        if (type.equals("hello")) {
            SPTContainer container = spigotDevTools.hello(player);

            if (container == null) {
                /* Kick unauthorized players */
                Bukkit.getConsoleSender().sendMessage("§6WARNING!§e Player§7 " + player.getName() + "§c tried to say hello without access!");
                player.kickPlayer("Spigot Dev Tools: Unauthorized!");
                return;
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(stream);
            output.writeUTF("hello");
            player.sendPluginMessage(spigotDevTools.getPlugin(), "Spigot|DevTools", stream.toByteArray());
            return;
        }

        /* Kick unwelcome players */
        if (!spigotDevTools.hasSaidHello(player)) {
            Bukkit.getConsoleSender().sendMessage("§6WARNING!§e Player§7 " + player.getName() + "§c tried to do something without saying hello!");
            player.kickPlayer("Spigot Dev Tools: Unauthorized action!");
            return;
        }

        /* Process other types of messages */
        spigotDevTools.processMessage(type, player, input);
    }

}
