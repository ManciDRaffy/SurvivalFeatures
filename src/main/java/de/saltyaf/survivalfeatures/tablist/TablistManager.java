package de.saltyaf.survivalfeatures.tablist;

import de.saltyaf.survivalfeatures.SurvivalFeatures;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setPlayerList(Player player) {

        StringBuilder headerend = new StringBuilder();
        for (String line : SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getStringList("playerlist_header")) {
            if (line.isEmpty() || line.isBlank())
                headerend.append("\n");
            else
                headerend.append(line).append("\n");
        }
        headerend.delete(headerend.length() - 1, headerend.length());

        int spawnedMobs = 0;
        for (Entity entity : player.getWorld().getEntities())
            if (entity instanceof Monster || entity instanceof Flying)
                spawnedMobs++;
        String finalheader = headerend.toString();
        finalheader = ChatColor.translateAlternateColorCodes('&', finalheader);
        finalheader = finalheader.replaceAll("%player%", player.getName());
        finalheader = finalheader.replaceAll("%memory_usage%", String.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
        finalheader = finalheader.replaceAll("%max_memory%", String.valueOf((Runtime.getRuntime().maxMemory() / 1048576L)));
        finalheader = finalheader.replaceAll("%total_memory%", String.valueOf((Runtime.getRuntime().totalMemory() / 1048576L)));
        finalheader = finalheader.replaceAll("%tps%", String.valueOf((MinecraftServer.TPS)));
        finalheader = finalheader.replaceAll("%ping%", String.valueOf((player.getPing())));
        finalheader = finalheader.replaceAll("%mobs%", String.valueOf(spawnedMobs));
        finalheader = finalheader.replaceAll("%mob-cap%", String.valueOf(player.getWorld().getMonsterSpawnLimit()));

        player.setPlayerListHeader(finalheader);

        StringBuilder footerend = new StringBuilder();
        for (String line : SurvivalFeatures.getPlugin().getSettings().getFileConfiguration().getStringList("playerlist_footer")) {
            if (line.isEmpty() || line.isBlank())
                footerend.append("\n");
            else
                footerend.append(line).append("\n");
        }
        footerend.delete(footerend.length() - 1, footerend.length());

        String finalfooter = footerend.toString();
        finalfooter = ChatColor.translateAlternateColorCodes('&', finalfooter);
        finalfooter = finalfooter.replaceAll("%player%", player.getName());
        finalfooter = finalfooter.replaceAll("%memory_usage%", String.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
        finalfooter = finalfooter.replaceAll("%max_memory%", String.valueOf((Runtime.getRuntime().maxMemory() / 1048576L)));
        finalfooter = finalfooter.replaceAll("%total_memory%", String.valueOf((Runtime.getRuntime().totalMemory() / 1048576L)));
        finalfooter = finalfooter.replaceAll("%tps%", String.valueOf((MinecraftServer.TPS)));
        finalfooter = finalfooter.replaceAll("%ping%", String.valueOf((player.getPing())));
        finalfooter = finalfooter.replaceAll("%mobs%", String.valueOf(spawnedMobs));
        finalfooter = finalfooter.replaceAll("%mob-cap%", String.valueOf(player.getWorld().getMonsterSpawnLimit()));

        player.setPlayerListFooter(finalfooter);
    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setAllPlayerPlayerList() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerList);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team players = scoreboard.getTeam("aplayers");
        if (players == null)
            players = scoreboard.registerNewTeam("aplayers");
        players.setPrefix("");
        players.setColor(ChatColor.GRAY);

        Team creative = scoreboard.getTeam("bcreative");
        if (creative == null)
            creative = scoreboard.registerNewTeam("bcreative");
        creative.setPrefix("§6C §7| ");
        creative.setColor(ChatColor.GOLD);

        Team vanished = scoreboard.getTeam("cvanished");
        if (vanished == null)
            vanished = scoreboard.registerNewTeam("cvanished");
        vanished.setPrefix("§8§oV §7| ");
        vanished.setColor(ChatColor.DARK_GRAY);


        for (Player target : Bukkit.getOnlinePlayers()) {
            if (SurvivalFeatures.getPlugin().getVanishManager().isVanished(target)) {
                vanished.addEntry(target.getName());
                target.setGlowing(false);
                target.setInvisible(true);
            } else if (target.getGameMode().equals(GameMode.CREATIVE)) {
                creative.addEntry(target.getName());
                target.setGlowing(true);
                target.setInvisible(false);
            } else {
                players.addEntry(target.getName());
                target.setGlowing(false);
                target.setInvisible(false);
            }

        }
    }

}
