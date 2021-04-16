package me.ihdeveloper.spigot.devtools.api;

import org.bukkit.entity.Player;

public final class DevTools {

    private static SpigotDevTools instance;

    public static void watch(String key, String value) {
        instance.getWatcher().put(key, value);
    }

    public static void watch(Player player, String key, String value) {
        instance.getWatcher().put(player, key, value);
    }

    public static void unwatch(String key) {
        instance.getWatcher().remove(key);
    }

    public static void unwatch(Player player, String key) {
        instance.getWatcher().remove(player, key);
    }

    public static void setInstance(SpigotDevTools instance) {
        DevTools.instance = instance;
    }

    public static SpigotDevTools getInstance() {
        return instance;
    }

}
