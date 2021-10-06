package de.saltyaf.survivalfeatures.commands;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().contains(p.getUniqueId().toString())) {
            p.sendMessage(SurvivalFeatures.PREFIX + "You haven't set any homes yet.");
            return true;
        }
        if (args.length == 0) {
            if (SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().isSet(p.getUniqueId().toString() + ".default")) {
                p.teleport(getHome(p, "default"));
                p.sendMessage(SurvivalFeatures.PREFIX + "You've been sent to your default home.");
                return true;
            }
        } else if (args.length == 1) {
            if (SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getInt("max_homes") <= 1) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You only have the default home. Use /home.");
                return true;
            }
            if (!SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().isSet(p.getUniqueId().toString() + "." + args[0].toLowerCase())) {
                p.sendMessage(SurvivalFeatures.PREFIX + "You haven't set the home yet. Use /homes to show all your homes.");
                return true;
            }
            if (SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().isSet(p.getUniqueId().toString() + "." + args[0].toLowerCase())) {
                p.teleport(getHome(p, args[0].toLowerCase()));
                p.sendMessage(SurvivalFeatures.PREFIX + "You've been sent to " + args[0] + ".");
                return true;
            }
        } else {
            p.sendMessage(SurvivalFeatures.PREFIX + "Use /home [<name>]");
        }

        return false;
    }

    public Location getHome(Player p, String name) {
        String worldName = SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getString(p.getUniqueId().toString() + "." + name.toLowerCase() + ".world");
        assert worldName != null;
        World world = Bukkit.getServer().getWorld(worldName);
        double x = SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getDouble(p.getUniqueId().toString() + "." + name.toLowerCase() + ".x");
        double y = SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getDouble(p.getUniqueId().toString() + "." + name.toLowerCase() + ".y");
        double z = SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getDouble(p.getUniqueId().toString() + "." + name.toLowerCase() + ".z");
        float yaw = (float) SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getDouble(p.getUniqueId().toString() + "." + name.toLowerCase() + ".yaw");
        float pitch = (float) SurvivalFeatures.getPlugin().getHomes().getFileConfiguration().getDouble(p.getUniqueId().toString() + "." + name.toLowerCase() + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
