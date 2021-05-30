package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.Logger;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleLogger implements Logger {
    private static final int DEFAULT_MAXIMUM_CACHE = 50;

    private static final byte TYPE_INFO = 4;
    private static final byte TYPE_WARNING = 3;
    private static final byte TYPE_ERROR = 2;
    private static final byte TYPE_DEBUG = 1;

    /**
     * Internal function for providing faster way to read/write the log cache file
     */

    private static Method $internalWriteUTF;
    private static int internalWriteUTF(String str, DataOutput out) throws InvocationTargetException, IllegalAccessException {
        if ($internalWriteUTF == null)
            return 0;

        $internalWriteUTF.setAccessible(true);
        return (int) $internalWriteUTF.invoke(null, str, out);
    }

    static {
        try {
            $internalWriteUTF = DataOutputStream.class.getDeclaredMethod("writeUTF", String.class, DataOutput.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Failed to setup an internal method for cache logging faster!");
            e.printStackTrace();
        }
    }

    private final File cacheFile;
    private RandomAccessFile cacheStream;
    private int max = DEFAULT_MAXIMUM_CACHE;
    private int current = 0;

    private long cachePointer = 0L;
    private long nextBytes = 0L;
    private boolean moveCachePointer = false;

    public SimpleLogger(File dataFolder) {
        this.cacheFile = new File(dataFolder, "log.cache");

        try {
            dataFolder.mkdir();

            if (this.cacheFile.exists())
                this.cacheFile.delete();

            this.cacheFile.createNewFile();

            this.cacheStream = new RandomAccessFile(cacheFile, "rw");
        } catch (IOException exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to create/access the logger cache file (log.cache)");
            exception.printStackTrace();
        }
    }

    public void dispose() {
        try {
            if (this.cacheFile.exists()) {
                this.cacheStream.close();

                this.cacheFile.delete();
            }
        } catch (IOException exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to delete the logger cache file (log.cache)");
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
            if (moveCachePointer) {
                out.writeInt(max);
            } else {
                out.writeInt(current);
            }

            this.cacheStream.seek(this.cachePointer);
            byte type = (byte) this.cacheStream.read();
            while (type >= 0) {
                out.writeByte(type);
                out.writeUTF(this.cacheStream.readUTF());

                type = (byte) this.cacheStream.read();
            }

            DevTools.getInstance().send(player, stream.toByteArray());
        } catch (IOException exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to send the cached logs! (corrupted cache file format/not enough memory?)");
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
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to broadcast the log! ");
            DevTools.getInstance().getPlugin().getLogger().warning("Type: " + type + " | Log Message: " + message);
            exception.printStackTrace();
        }

        byte[] data = stream.toByteArray();
        DevTools.getInstance().broadcast(data);

        current++;
        try {
            this.cacheStream.writeByte(type);
            int writtenBytes = internalWriteUTF(message, this.cacheStream) + 1;

            if (current > max) {
                moveCachePointer = true;
            }

            if (!moveCachePointer) {
                if (current == 1) {
                    this.nextBytes = writtenBytes;
                }
            } else {
                this.cachePointer += nextBytes;
                long lastWritePointer = this.cacheStream.getFilePointer();
                this.cacheStream.seek(this.cachePointer);
                this.cacheStream.readByte();
                this.cacheStream.readUTF();
                this.nextBytes = this.cacheStream.getFilePointer() - this.cachePointer;
                this.cacheStream.seek(lastWritePointer);
            }
        } catch (IOException exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to write the new log in the cache file! (not enough storage?)");
            exception.printStackTrace();
        } catch (IllegalAccessException e) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to write the new log in the cache file! (can't access internal method)");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to write the new log in the cache file! (internal method doesn't exist)");
            e.printStackTrace();
        }
    }

}
