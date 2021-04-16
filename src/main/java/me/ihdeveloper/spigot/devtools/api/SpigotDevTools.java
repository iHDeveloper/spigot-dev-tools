package me.ihdeveloper.spigot.devtools.api;

import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import me.ihdeveloper.spigot.devtools.api.message.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.DataInputStream;

public interface SpigotDevTools {

    SPTContainer hello(Player player);

    void registerHandler(String name, MessageHandler handler);

    void processMessage(String name, Player player, DataInputStream input);

    void setAuthorizationHandler(AuthorizationHandler handler);

    boolean hasSaidHello(Player player);

    Plugin getPlugin();

    Watcher getWatcher();

    SPTContainer getContainer(Player player);

}
