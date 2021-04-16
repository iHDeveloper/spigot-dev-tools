package me.ihdeveloper.spigot.devtools;

import me.ihdeveloper.spigot.SpigotDevTools;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChannelListener implements PluginMessageListener {
    private static final String CHANNEL_NAME = "Spigot|DevTools";

    private final SpigotDevTools instance;

    public ChannelListener(SpigotDevTools instance) {
        this.instance = instance;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals(CHANNEL_NAME)) {
            return;
        }

        // TODO Implement authorization method
    }

}
