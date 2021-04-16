package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.Watcher;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleWatcher implements Watcher {

    @Override
    public void put(String key, String value) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().hasSaidHello(player)) {
                continue;
            }

            put(player, key, value);
        }
    }

    @Override
    public void put(Player player, String key, String value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("watcher-put");
            output.writeUTF(key);
            output.writeUTF(value);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }

    @Override
    public void remove(String key) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().hasSaidHello(player)) {
                continue;
            }

            remove(player, key);
        }
    }

    @Override
    public void remove(Player player, String key) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("watcher-remove");
            output.writeUTF(key);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }
}
