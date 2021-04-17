package me.ihdeveloper.spigot.devtools.test.command;

import me.ihdeveloper.spigot.devtools.api.DevTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou don't have permissions to execute this command!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou have to be player to execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!DevTools.getInstance().hasSaidHello(player)) {
            sender.sendMessage("§cYou haven't send a hello message! Try /hello");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(command.getUsage());
            return true;
        }

        String rawType = args[0];
        int type;

        try {
            type = Integer.parseInt(rawType);
        } catch (NumberFormatException exception) {
            sender.sendMessage("§cFailed to parse type!§7 (NOT_INTEGER)");
            return true;
        }

        if (type == 0) {
            DevTools.watch("hello", "world");
        } else if (type == 1) {
            DevTools.watch(player, "test", "test");
        } else if (type == 2) {
            DevTools.unwatch("hello");
        } else if (type == 3) {
            DevTools.unwatch(player, "test");
        }
        return true;
    }
}
