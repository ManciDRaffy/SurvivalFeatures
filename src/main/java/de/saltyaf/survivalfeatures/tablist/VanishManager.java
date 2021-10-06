package de.saltyaf.survivalfeatures.tablist;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    private Plugin plugin;
    private List<Player> vanished;

    public VanishManager(Plugin plugin) {
        this.plugin = plugin;
        this.vanished = new ArrayList<>();
    }

    public List<Player> isVanished() {
        return vanished;
    }

    public boolean isVanished(Player player) {
        return vanished.contains(player);
    }

    public void setVanished(Player player, boolean bool) {
        if (bool)
            vanished.add(player);
        else
            vanished.remove(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (bool)
                onlinePlayer.hidePlayer(plugin, player);
            else
                onlinePlayer.showPlayer(plugin, player);
        }
    }

    public void hideAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("sf.seevanished") || player.isOp())
                vanished.forEach(player1 -> player.showPlayer(plugin, player1));
            else
                vanished.forEach(player1 -> player.hidePlayer(plugin, player1));
        }
    }

    public void vanishedMessage() {
        vanished.forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aYou are vanished!")));
    }
}
