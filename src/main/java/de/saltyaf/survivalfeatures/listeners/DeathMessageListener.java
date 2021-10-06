package de.saltyaf.survivalfeatures.listeners;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessageListener implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        String message = SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getString("death_message");
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replaceAll("%death-message%", e.getDeathMessage());
        e.setDeathMessage(message);
    }


}

