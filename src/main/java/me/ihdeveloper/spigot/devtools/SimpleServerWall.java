package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.SDTServerWall;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SimpleServerWall implements SDTServerWall {

    private final Map<String, String> wall = new TreeMap<>();

    @Override
    public void put(String name, String value) {
        this.wall.put(name, value);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().hasSaidHello(player)) {
                continue;
            }

            sendWallPut(player, name, value);
        }
    }

    @Override
    public void remove(String name) {
        this.wall.remove(name);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().hasSaidHello(player)) {
                continue;
            }

            sendWallRemove(player, name);
        }
    }

    @Override
    public void clear() {
        this.wall.clear();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().hasSaidHello(player)) {
                continue;
            }

            sendWallClear(player);
        }
    }

    @Override
    public Set<Map.Entry<String, String>> get() {
        return this.wall.entrySet();
    }

    public void sendWall(Player player) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–put");
            output.writeInt(get().size());

            for (Map.Entry<String, String> entry : get()) {
                output.writeUTF(entry.getKey());
                output.writeUTF(entry.getValue());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }

    private void sendWallPut(Player player, String name, String value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–put");
            output.writeInt(0);
            output.writeUTF(name);
            output.writeUTF(value);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }

    private void sendWallRemove(Player player, String name) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–remove");
            output.writeUTF(name);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }

    private void sendWallClear(Player player) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–reset");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
    }

}
