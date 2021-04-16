package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.SpigotDevTools;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements SpigotDevTools {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§a is enabled!§e Plugin By§3 @iHDeveloper");
        getServer().getConsoleSender().sendMessage("§6WARNING!§e The authorization method:§4 ONLY OP!");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "Spigot|DevTools");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Spigot|DevTools", new ChannelListener(this));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
