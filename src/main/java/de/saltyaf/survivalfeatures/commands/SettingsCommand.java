package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("sf.settings")) {
            sender.sendMessage("§cYou don't have permission to do that.");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                SurvivalFeatures.getPlugin().getSettings().reload();
                SurvivalFeatures.PREFIX = ChatColor.translateAlternateColorCodes('&', SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getString("prefix"));

                sender.sendMessage(SurvivalFeatures.PREFIX + "Settings have been reloaded.");
            }

        } else if (args.length == 2) {

        } else {

        }
        return false;
    }
}
