package me.ihdeveloper.spigot.devtools.test;

import me.ihdeveloper.spigot.devtools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HelloCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou don't have permissions to execute this command!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou have to be player to execute this command!");
            return true;
        }

        Player player = (Player) sender;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("hello");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "Spigot|DevTools", stream.toByteArray());
        player.sendMessage("§aHello there!");
        return true;
    }
}
