package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.Logger;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SimpleLogger implements Logger {
    private static final int DEFAULT_MAXIMUM_CACHE = 50;

    private static final byte TYPE_INFO = 4;
    private static final byte TYPE_WARNING = 3;
    private static final byte TYPE_ERROR = 2;
    private static final byte TYPE_DEBUG = 1;

    private final File cacheFile;
    private RandomAccessFile cacheStream;
    private int max = DEFAULT_MAXIMUM_CACHE;
    private int current = 0;

    public SimpleLogger(File dataFolder) {
        this.cacheFile = new File(dataFolder, "log.cache");

        try {
            this.cacheFile.delete();
            this.cacheFile.createNewFile();

            this.cacheStream = new RandomAccessFile(cacheFile, "rw");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void dispose() {
        try {
            this.cacheStream.close();

            this.cacheFile.delete();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void info(String message) {
        broadcast(TYPE_INFO, message);
    }

    @Override
    public void warn(String message) {
        broadcast(TYPE_WARNING, message);
    }

    @Override
    public void err(String message) {
        broadcast(TYPE_ERROR, message);
    }

    @Override
    public void debug(String message) {
        broadcast(TYPE_DEBUG, message);
    }

    @Override
    public void sendCache(Player player) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("logger-chunk");
            out.writeInt(current);

            this.cacheStream.seek(0L);
            for (int i = current; i >= 0; i--) {
                byte type = this.cacheStream.readByte();
                String message = this.cacheStream.readUTF();

                out.writeByte(type);
                out.writeUTF(message);
            }

            DevTools.getInstance().send(player, stream.toByteArray());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setMaximumCacheSize(int max) {
        this.max = max;
        // TODO apply the new maximum cache size
    }

    private void broadcast(byte type, String message) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("logger");
            out.writeByte(type);
            out.writeUTF(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        byte[] data = stream.toByteArray();
        DevTools.getInstance().broadcast(data);

        current = Math.min(current, 50);
        try {
            this.cacheStream.seek(0L);
            this.cacheStream.writeByte(type);
            this.cacheStream.writeUTF(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
