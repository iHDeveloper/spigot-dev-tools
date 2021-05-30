package me.ihdeveloper.spigot.devtools.task;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TPSTask implements Runnable {
    private static final double[] FAKE_TPS = { 0, 0, 0 };

    @Override
    public void run() {
        double[] recentTPS;
        try {
            Object server = Bukkit.getServer();
            Method server$getServer = server.getClass().getMethod("getServer");

            Object nmsServer = server$getServer.invoke(server);
            Field nmsServer$recentTps = nmsServer.getClass().getField("recentTps");

            nmsServer$recentTps.setAccessible(true);
            recentTPS = ((double[]) nmsServer$recentTps.get(nmsServer));
        } catch (Exception exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to read TPS data! (using reflection) Assigning fake TPS...");
            recentTPS = FAKE_TPS;
            exception.printStackTrace();
        }

        if (recentTPS == null) {
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("tps");
            for (int i = 0; i < 3; i++)
                out.writeDouble(recentTPS[i]);
        } catch (IOException exception) {
            DevTools.getInstance().getPlugin().getLogger().warning("Failed to write TPS packet data! (not enough memory?)");
            exception.printStackTrace();
            return;
        }

        DevTools.getInstance().broadcast(stream.toByteArray());
    }
}
