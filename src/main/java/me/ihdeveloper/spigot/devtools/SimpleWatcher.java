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
        byte[] data = buildPut(key, value);
        Main.getInstance().broadcast(data);
    }

    @Override
    public void put(Player player, String key, String value) {
        byte[] data = buildPut(key, value);
        Main.getInstance().sendData(player, data);
    }

    @Override
    public void remove(String key) {
        byte[] data = buildRemove(key);
        Main.getInstance().broadcast(data);
    }

    @Override
    public void remove(Player player, String key) {
        byte[] data = buildRemove(key);
        Main.getInstance().sendData(player, data);
    }

    private byte[] buildPut(String key, String value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("watcher-put");
            output.writeUTF(key);
            output.writeUTF(value);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stream.toByteArray();
    }

    private byte[] buildRemove(String key) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("watcher-remove");
            output.writeUTF(key);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stream.toByteArray();
    }
}
