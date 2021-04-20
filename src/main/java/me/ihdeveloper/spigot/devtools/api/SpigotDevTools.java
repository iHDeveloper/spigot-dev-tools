package me.ihdeveloper.spigot.devtools.api;

import me.ihdeveloper.spigot.devtools.api.auth.AuthorizationHandler;
import me.ihdeveloper.spigot.devtools.api.message.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.DataInputStream;
import java.util.Collection;

public interface SpigotDevTools {

    SDTContainer hello(Player player, byte major, byte minor);

    void registerHandler(String name, MessageHandler handler);

    void processMessage(String name, Player player, DataInputStream input);

    void setAuthorizationHandler(AuthorizationHandler handler);

    boolean hasSaidHello(Player player);

    Plugin getPlugin();

    Watcher getWatcher();

    SDTContainer getContainer(Player player);

    Collection<SDTContainer> getContainers();

}
