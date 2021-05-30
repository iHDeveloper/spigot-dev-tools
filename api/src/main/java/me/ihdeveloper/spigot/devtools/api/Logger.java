package me.ihdeveloper.spigot.devtools.api;

import org.bukkit.entity.Player;

public interface Logger {

    void info(String message);

    void warn(String message);

    void err(String message);

    void debug(String message);

    void sendCache(Player player);

    void setMaximumCacheSize(int max);

}
