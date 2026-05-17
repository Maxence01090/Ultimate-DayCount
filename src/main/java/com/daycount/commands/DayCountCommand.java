package com.daycount.commands;

import com.daycount.DayCount;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DayCountCommand implements CommandExecutor {

    private final DayCount plugin;

    public DayCountCommand(DayCount plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("daycount")) return false;

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.isOp()) {
                sender.sendMessage(plugin.getConfiguration().getNoPermissionMessage());
                return true;
            }
            plugin.getConfiguration().reload();
            sender.sendMessage(plugin.getConfiguration().getReloadMessage());
            return true;
        }

        int days = plugin.getDayCount();
        String message = plugin.getConfiguration().formatMessage(days);
        sender.sendMessage(message);
        return true;
    }
}
