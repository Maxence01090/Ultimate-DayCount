package com.daycount.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Config {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        checkAndUpdateConfig();
    }

    private void checkAndUpdateConfig() {
        InputStream defaultStream = plugin.getResource("config.yml");
        if (defaultStream == null) return;

        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(defaultStream, StandardCharsets.UTF_8)
        );

        Set<String> defaultKeys = defaultConfig.getKeys(true);

        boolean missing = false;
        for (String key : defaultKeys) {
            if (!config.contains(key)) {
                missing = true;
                break;
            }
        }

        if (!missing) return;

        plugin.getLogger().info("[Ultimate Daycount] Missing configuration keys detected. Updating config.yml...");

        Map<String, Object> preservedValues = new HashMap<>();
        for (String key : defaultKeys) {
            if (config.contains(key) && !(config.get(key) instanceof org.bukkit.configuration.ConfigurationSection)) {
                preservedValues.put(key, config.get(key));
            }
        }

        java.io.File configFile = new java.io.File(plugin.getDataFolder(), "config.yml");
        if (configFile.exists()) configFile.delete();

        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();

        for (Map.Entry<String, Object> entry : preservedValues.entrySet()) {
            config.set(entry.getKey(), entry.getValue());
        }

        plugin.saveConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        checkAndUpdateConfig();
    }

    public boolean isDebugEnabled() {
        return config.getBoolean("debug", false);
    }

    public String getLanguage() {
        return config.getString("language", "en");
    }

    public String getMessageFormat() {
        String lang = getLanguage();
        if (lang.equals("fr")) {
            return config.getString("messages.fr.format", "§6[Ultimate Daycount] §bJours passés: §a{days} jour(s)");
        }
        return config.getString("messages.en.format", "§6[Ultimate Daycount] §bDays passed: §a{days} day(s)");
    }

    public boolean isPauseTimeWhenEmpty() {
        return config.getBoolean("pause-time-when-empty", true);
    }

    public String translateColorCodes(String text) {
        return text.replace('&', '§');
    }

    public String formatMessage(int dayCount) {
        String message = getMessageFormat();
        message = message.replace("{days}", String.valueOf(dayCount));
        return translateColorCodes(message);
    }

    public String getNoPermissionMessage() {
        if (getLanguage().equals("fr")) {
            return "§c[Ultimate Daycount] Vous n'avez pas la permission d'utiliser cette commande.";
        }
        return "§c[Ultimate Daycount] You don't have permission to use this command.";
    }

    public String getReloadMessage() {
        if (getLanguage().equals("fr")) {
            return "§a[Ultimate Daycount] Configuration rechargée.";
        }
        return "§a[Ultimate Daycount] Config reloaded.";
    }
}
