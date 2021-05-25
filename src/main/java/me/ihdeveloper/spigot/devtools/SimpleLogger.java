package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleLogger implements Logger {
    private static final byte TYPE_INFO = 4;
    private static final byte TYPE_WARNING = 3;
    private static final byte TYPE_ERROR = 2;
    private static final byte TYPE_DEBUG = 1;

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
        Main.getInstance().broadcast(data);
    }

}
