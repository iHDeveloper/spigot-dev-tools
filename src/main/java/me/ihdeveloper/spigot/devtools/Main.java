package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import me.ihdeveloper.spigot.devtools.auth.OPAuthorizationHandler;
import me.ihdeveloper.spigot.devtools.task.ProfilerTask;
import me.ihdeveloper.spigot.devtools.task.TPSTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin implements Listener {
    private static final byte PROTOCOL_MAJOR = 0;
    private static final byte PROTOCOL_MINOR = 3;

    private SimpleSpigotDevTools simpleSpigotDevTools;

    @Override
    public void onEnable() {
        simpleSpigotDevTools = new SimpleSpigotDevTools(this, PROTOCOL_MAJOR, PROTOCOL_MINOR);

        /* Default Setup */
        simpleSpigotDevTools.setAuthorizationHandler(new OPAuthorizationHandler());
        simpleSpigotDevTools.setAutoDiscovery(true);

        DevTools.setInstance(simpleSpigotDevTools);

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getScheduler().runTaskTimer(this, new TPSTask(), 0L, 30 * 20L);
        getServer().getScheduler().runTaskTimer(this, new ProfilerTask(), 0L, 20L);

        DevTools.pin("§eSDT Protocol Version", "v" + PROTOCOL_MAJOR + "." + PROTOCOL_MINOR);
        DevTools.pin("§eServer Version", getServer().getVersion());
        DevTools.pin("§eBukkit Version", getServer().getBukkitVersion());
        DevTools.pin("§eMax Players", "" + getServer().getMaxPlayers());
        DevTools.pin("§eAllow Nether", "" + getServer().getAllowEnd());
        DevTools.pin("§eAllow The End", "" + getServer().getAllowEnd());

        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§a is enabled!§e Plugin By§3 @iHDeveloper");
        getServer().getConsoleSender().sendMessage("§4!!! §eThis project is in the §cALPHA§e state §4!!!");
        getServer().getConsoleSender().sendMessage("§bINFO!§e Protocol Version:§7 v" + PROTOCOL_MAJOR + "." + PROTOCOL_MINOR);
        getServer().getConsoleSender().sendMessage("§6WARNING!§e The authorization method: " + simpleSpigotDevTools.getAuthorizationHandler().toString());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "Spigot|DevTools");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Spigot|DevTools", new ChannelListener(simpleSpigotDevTools));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (simpleSpigotDevTools.isAutoDiscovery()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);

            try {
                out.writeUTF("discovery");
                out.writeByte(PROTOCOL_MAJOR);
                out.writeByte(PROTOCOL_MINOR);
            } catch (IOException exception) {
                DevTools.getInstance().getPlugin().getLogger().warning("Failed to write discovery packet data! (not enough memory?)");
                exception.printStackTrace();
            }

	    event.getPlayer().sendMessage("Send the auto discovery!");
            getServer().getScheduler().runTaskLater(this, () -> simpleSpigotDevTools.forceSend(event.getPlayer(), stream.toByteArray()), 1L);
        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        simpleSpigotDevTools.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public void onDisable() {
        ((SimpleLogger)simpleSpigotDevTools.logger()).dispose();

        simpleSpigotDevTools.dispose();
        simpleSpigotDevTools = null;

        getServer().getConsoleSender().sendMessage("§eSpigot Dev Tools§c is disabled!§e Plugin By§3 @iHDeveloper");
    }

}
