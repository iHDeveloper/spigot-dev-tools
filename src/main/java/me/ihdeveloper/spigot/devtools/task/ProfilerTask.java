package me.ihdeveloper.spigot.devtools.task;

import me.ihdeveloper.spigot.devtools.Main;
import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.api.SDTContainer;
import me.ihdeveloper.spigot.devtools.api.SDTProfiler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProfilerTask implements Runnable {

    @Override
    public void run() {
        SDTProfiler profiler = DevTools.getInstance().getProfiler();

        profiler.build();
        SDTProfiler.Item[] items = profiler.getItems();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("profiler");

            if (items != null) {
                int length = items.length;
                out.writeInt(length);

                for (int i = 0; i < length; i++) {
                    SDTProfiler.Item item = items[i];
                    out.writeUTF(item.getName());
                    out.writeBoolean(item.isUpdated());
                    out.writeLong(item.getTicks());
                    out.writeDouble(item.getPercent());
                }
            } else {
                out.writeInt(0);
            }
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
