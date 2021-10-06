package de.saltyaf.survivalfeatures.listeners;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import de.saltyaf.survivalfeatures.tablist.VanishManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuit implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String message = ChatColor.translateAlternateColorCodes('&', SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getString("join_message"));
        message = message.replaceAll("%player%", e.getPlayer().getName());
        e.setJoinMessage(message);

        // TABLIST
        SurvivalFeatures.getPlugin().getTablistManager().setPlayerList(e.getPlayer());
        SurvivalFeatures.getPlugin().getTablistManager().setAllPlayerTeams();

        // VANISH
        VanishManager vanishManager = SurvivalFeatures.getPlugin().getVanishManager();
        vanishManager.hideAll();


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        String message = ChatColor.translateAlternateColorCodes('&', SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getString("quit_message"));
        message = message.replaceAll("%player%", e.getPlayer().getName());
        e.setQuitMessage(message);

    }
}
