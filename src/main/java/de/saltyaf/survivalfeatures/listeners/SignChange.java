package de.saltyaf.survivalfeatures.listeners;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getBoolean("colored_signs")) {
            e.setLine(0, ChatColor.translateAlternateColorCodes('&', e.getLine(0)));
            e.setLine(1, ChatColor.translateAlternateColorCodes('&', e.getLine(1)));
            e.setLine(2, ChatColor.translateAlternateColorCodes('&', e.getLine(2)));
            e.setLine(3, ChatColor.translateAlternateColorCodes('&', e.getLine(3)));
        }
    }
}
