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

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("profiler");

            out.writeInt(profiler.getItems().size());
            out.writeLong(profiler.getTotalTicks());
            out.writeLong(profiler.getTotalMilliseconds());
            for (SDTProfiler.Item item : profiler.getItems()) {
                out.writeUTF(item.getName());
                out.writeBoolean(item.isUpdated());
                out.writeLong(item.getTicks());
                out.writeLong(item.getMilliseconds());
                out.writeDouble(item.getPercent());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        Main.getInstance().broadcast(stream.toByteArray());
    }

}
