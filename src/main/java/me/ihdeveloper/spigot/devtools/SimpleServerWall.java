package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.SDTServerWall;
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

        byte[] data = buildChunkOfWall(name, value);
        DevTools.getInstance().broadcast(data);
    }

    @Override
    public void remove(String name) {
        this.wall.remove(name);

        byte[] data = buildRemovePartOfWall(name);
        DevTools.getInstance().broadcast(data);
    }

    @Override
    public void clear() {
        this.wall.clear();

        byte[] data = buildClear();
        DevTools.getInstance().broadcast(data);
    }

    @Override
    public Set<Map.Entry<String, String>> get() {
        return this.wall.entrySet();
    }

    public void sendWall(Player player) {
        byte[] data = buildWall();
        DevTools.getInstance().send(player, data);
    }

    private byte[] buildWall() {
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

        return stream.toByteArray();
    }

    private byte[] buildChunkOfWall(String name, String value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–put");
            output.writeInt(1);
            output.writeUTF(name);
            output.writeUTF(value);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stream.toByteArray();
    }

    private byte[] buildRemovePartOfWall(String name) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–remove");
            output.writeUTF(name);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stream.toByteArray();
    }

    private byte[] buildClear() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        try {
            output.writeUTF("server-wall–reset");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stream.toByteArray();
    }

}
