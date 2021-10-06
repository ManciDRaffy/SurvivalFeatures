package de.saltyaf.survivalfeatures;

import de.saltyaf.survivalfeatures.commands.*;
import de.saltyaf.survivalfeatures.listeners.Chat;
import de.saltyaf.survivalfeatures.listeners.DeathMessageListener;
import de.saltyaf.survivalfeatures.listeners.JoinQuit;
import de.saltyaf.survivalfeatures.listeners.SignChange;
import de.saltyaf.survivalfeatures.tablist.TablistManager;
import de.saltyaf.survivalfeatures.tablist.VanishManager;
import de.saltyaf.survivalfeatures.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public final class SurvivalFeatures extends JavaPlugin {

    public static String PREFIX;
    private static SurvivalFeatures plugin;
    private static BukkitTask tablistupdater;
    public Config settings;
    public Config homes;
    public TablistManager tablistManager;
    public VanishManager vanishManager;

    public static SurvivalFeatures getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        // MANAGER
        this.tablistManager = new TablistManager();
        this.vanishManager = new VanishManager(this);


        // COMMANDS
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("vanish").setExecutor(new VanishCommand());

        // LISTENERS
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Chat(), this);
        pluginManager.registerEvents(new SignChange(), this);
        pluginManager.registerEvents(new JoinQuit(), this);
        pluginManager.registerEvents(new DeathMessageListener(), this);


        // CONFIGS
        homes = new Config("homes.yml", getDataFolder());
        settings = new Config("settings.yml", getDataFolder());
        // SET DEFAULT SETTINGS
        if (!settings.getFileConfiguration().isSet("prefix")) {
            settings.getFileConfiguration().set("prefix", "&2&lS&f&lF &8➤ &7");
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("max_homes")) {
            settings.getFileConfiguration().set("max_homes", 3);
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("chatformat")) {
            settings.getFileConfiguration().set("chatformat", "&8[%world%&8] &7%player%&8: &7%message%");
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("colored_signs")) {
            settings.getFileConfiguration().set("colored_signs", true);
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("join_message")) {
            settings.getFileConfiguration().set("join_message", "&a→ &7%player%");
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("quit_message")) {
            settings.getFileConfiguration().set("quit_message", "&c← &7%player%");
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("playerlist_header")) {
            ArrayList<String> header = new ArrayList<>();
            header.add("");
            header.add("&7Welcome &a%player%&7!");
            header.add("");
            settings.getFileConfiguration().set("playerlist_header", header);
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("playerlist_footer")) {
            ArrayList<String> footer = new ArrayList<>();
            footer.add("");
            footer.add("&7TPS&8: &a%tps%");
            footer.add("&7Memory&8: &a%memory_usage%&7/&2%max_memory%");
            footer.add("&7Ping&8: &a%ping%");
            footer.add("&7Mobcap&8: &a%mobs%&7/&2%mob-cap%");
            footer.add("");
            settings.getFileConfiguration().set("playerlist_footer", footer);
            settings.save();
        }
        if (!settings.getFileConfiguration().isSet("death_message")) {
            settings.getFileConfiguration().set("death_message", "&7%death-message%");
            settings.save();
        }
        PREFIX = ChatColor.translateAlternateColorCodes('&', settings.getFileConfiguration().getString("prefix"));

        tablistupdater = new BukkitRunnable() {
            @Override
            public void run() {
                tablistManager.setAllPlayerTeams();
                tablistManager.setAllPlayerPlayerList();
                vanishManager.vanishedMessage();
                vanishManager.hideAll();
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    @Override
    public void onDisable() {
        homes.save();
        tablistupdater.cancel();
    }

    public Config getHomes() {
        return homes;
    }

    public Config getSettings() {
        return settings;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }
}
