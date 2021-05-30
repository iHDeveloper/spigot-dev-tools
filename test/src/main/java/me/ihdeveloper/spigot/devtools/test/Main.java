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

        long period = 5L;
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Light Operation", 1_000), 0L, period);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Medium Operation", 500_000), 0L, period);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Heavy Operation 1", 1_000_000), 0L, period);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Heavy Operation 2", 10_000_000), 0L, period);

        // Lazy Operations
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Lazy Light Operation", 50_000_000), 0L, 7 * 20L);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Lazy Medium Operation", 75_000_000), 0L, 20 * 20L);
        getServer().getScheduler().runTaskTimer(this, new ProfileTesterTask("Lazy Heavy Operation", 100_000_000), 0L, 30 * 20L);

        DevTools.logger().info("This server is using the test plugin of §eSpigot Dev Tools!");
        DevTools.logger().info("§ePlugin By §c@iHDeveloper");
        DevTools.logger().info("Example of info log message!");
        DevTools.logger().warn("Example of warn log message!");
        DevTools.logger().err("Example of error log message!");
        DevTools.logger().debug("Example of debug log message!");
        DevTools.logger().debug("Example of debug log message!");

        getCommand("hello").setExecutor(new HelloCommand());
        getCommand("dev-test").setExecutor(new TestCommand());
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§4 TEST§a is enabled!§e Plugin By§3 @iHDeveloper");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§4 TEST§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
