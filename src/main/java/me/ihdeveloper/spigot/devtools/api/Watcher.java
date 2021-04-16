package me.ihdeveloper.spigot.devtools.api;

import org.bukkit.entity.Player;

public interface Watcher {

     void put(String key, String value);

     void put(Player player, String key, String value);

     void remove(String key);

     void remove(Player player, String key);

}
