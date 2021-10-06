package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import de.saltyaf.survivalfeatures.util.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if(SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getConfigurationSection(p.getUniqueId().toString()).getKeys(false).size() <= 0) {
            p.sendMessage(SurvivalFeatures.PREFIX + "You haven't set any homes yet.");
            return true;
        }
        if (SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getInt("max_homes") <= 1) {
            p.sendMessage(SurvivalFeatures.PREFIX + "You only have the default home. Use /home.");
            return true;
        }
        StringBuilder homenames = new StringBuilder();

        for (String name : SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
            homenames.append(name).append(" ");


        p.sendMessage(SurvivalFeatures.PREFIX + "Homes: " + homenames);
        return false;
    }
}
