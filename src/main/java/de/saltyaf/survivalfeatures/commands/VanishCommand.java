package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import de.saltyaf.survivalfeatures.tablist.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("sf.vanish")) {
            sender.sendMessage(SurvivalFeatures.PREFIX + "§cYou don't have permission to do that");
            return true;
        }
        VanishManager vanishManager = SurvivalFeatures.getPlugin().getVanishManager();
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(SurvivalFeatures.PREFIX + "Player is not online");
                return true;
            }
            if (vanishManager.isVanished(target)) {
                vanishManager.setVanished(target, false);
                target.sendMessage(SurvivalFeatures.PREFIX + "You are no longer vanished.");
                sender.sendMessage(SurvivalFeatures.PREFIX + "You disabled vanished for " + target.getName());
            } else {
                vanishManager.setVanished(target, true);
                target.sendMessage(SurvivalFeatures.PREFIX + "You vanished away from everyone");
                sender.sendMessage(SurvivalFeatures.PREFIX + "You enabled vanished for " + target.getName());
            }
        } else if (sender instanceof Player p) {
            if (vanishManager.isVanished(p)) {
                vanishManager.setVanished(p, false);
                p.sendMessage(SurvivalFeatures.PREFIX + "You are no longer vanished.");
            } else {
                vanishManager.setVanished(p, true);
                p.sendMessage(SurvivalFeatures.PREFIX + "You vanished away from everyone");
            }
        }
        return false;
    }
}

