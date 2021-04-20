package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.SDTContainer;

import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TPSTask implements Runnable {

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
            exception.printStackTrace();
            return;
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
            exception.printStackTrace();
            return;
        }

        byte[] data = stream.toByteArray();
        for (SDTContainer container : DevTools.getInstance().getContainers()) {
            container.getPlayer().sendPluginMessage(Main.getInstance(), "Spigot|DevTools", data);
        }
    }
}
