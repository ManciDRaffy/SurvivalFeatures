package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length == 1) {
            if (SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().isSet(p.getUniqueId().toString() + "." + args[0].toLowerCase())) {
                SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + args[0].toLowerCase(), null);
                SurvivalFeatures.getPlugin().getHomes().save();
                p.sendMessage(SurvivalFeatures.PREFIX + "You have removed §a" + args[0]);
            } else {
                p.sendMessage(SurvivalFeatures.PREFIX + "You don't have a home with this name");
            }
        } else {
            p.sendMessage(SurvivalFeatures.PREFIX + "Use: /delhome [<name>]");
        }
        return false;
    }
}