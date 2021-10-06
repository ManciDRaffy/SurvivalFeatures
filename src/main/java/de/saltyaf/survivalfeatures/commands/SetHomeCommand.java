package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length == 0) {
            if (SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getConfigurationSection(p.getUniqueId().toString()).getKeys(false).contains("default")) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You already set this home, delete it first to set it. Use /delhome default.");
                return true;
            }
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.world", p.getLocation().getWorld().getName());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.x", p.getLocation().getX());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.y", p.getLocation().getY());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.z", p.getLocation().getZ());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.yaw", p.getLocation().getYaw());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + ".default.pitch", p.getLocation().getPitch());
            SurvivalFeatures.getPlugin().getHomes().save();
            p.sendMessage(SurvivalFeatures.PREFIX + "You've set your default home. Use /home or /home default to get there.");
        } else if (args.length == 1) {
            if (SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getInt("max_homes") <= 1) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You can only set a default home.");
                return true;
            }
            if (SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getInt("max_homes") <= SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getConfigurationSection(p.getUniqueId().toString()).getKeys(false).size()) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You have set the maximum of homes. Delete one if you want to set a new one.");
                return true;
            }
            if (SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getConfigurationSection(p.getUniqueId().toString()).getKeys(false).contains(args[0].toLowerCase())) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You already set this home, delete it first to set it. Use /delhome " + args[0].toLowerCase() + ".");
                return true;
            }
            String name = args[0].toLowerCase();
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".world", p.getLocation().getWorld().getName());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".x", p.getLocation().getX());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".y", p.getLocation().getY());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".z", p.getLocation().getZ());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".yaw", p.getLocation().getYaw());
            SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().set(p.getUniqueId().toString() + "." + name + ".pitch", p.getLocation().getPitch());
            SurvivalFeatures.getPlugin().getHomes().save();
            p.sendMessage(SurvivalFeatures.PREFIX + "You've set a home (§a" + name + "§7). Use /home " + name + " to get there.");
        } else {
            p.sendMessage(SurvivalFeatures.PREFIX + "Use /sethome [<name>]");
        }
        return false;
    }
}
