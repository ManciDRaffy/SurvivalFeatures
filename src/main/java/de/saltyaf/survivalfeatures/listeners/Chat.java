package de.saltyaf.survivalfeatures.listeners;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String format = ChatColor.translateAlternateColorCodes('&', SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getString("chatformat"));
        format = format.replaceAll("%player%", e.getPlayer().getName());
        format = format.replaceAll("%message%", ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        if (format.contains("%world%")) {
            World.Environment env = e.getPlayer().getWorld().getEnvironment();
            String type = "";
            if (env == World.Environment.NETHER)
                type = "§cNether";
            if (env == World.Environment.NORMAL)
                type = "§aOverworld";
            if (env == World.Environment.THE_END)
                type = "§5The End";
            format = format.replaceAll("%world%", type);
        }

        e.setFormat(format);
    }
}
