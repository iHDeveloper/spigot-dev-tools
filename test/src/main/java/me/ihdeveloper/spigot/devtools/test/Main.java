package me.ihdeveloper.spigot.devtools.test;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.test.command.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        if (DevTools.getInstance() == null) {
            getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§4 TEST§c failed to enable! Disabling...§7 (DevTools is null)");
            setEnabled(false);
            return;
        }

        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Light Operation", 1_000), 0L, 1L);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Medium Operation", 500_000), 0L, 1L);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Heavy Operation 1", 1_000_000), 0L, 1L);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Heavy Operation 2", 10_000_000), 0L, 5L);


        getCommand("hello").setExecutor(new HelloCommand());
        getCommand("dev-test").setExecutor(new TestCommand());
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§4 TEST§a is enabled!§e Plugin By§3 @iHDeveloper");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§4 TEST§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
