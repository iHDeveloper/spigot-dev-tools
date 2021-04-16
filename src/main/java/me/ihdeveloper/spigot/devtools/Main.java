package me.ihdeveloper.spigot.devtools;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§a is enabled!§e Plugin By§3 @iHDeveloper");
        getServer().getConsoleSender().sendMessage("§6WARNING!§e The authorization method:§4 ONLY OP!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
