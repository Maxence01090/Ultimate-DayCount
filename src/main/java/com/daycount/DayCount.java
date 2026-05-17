package com.daycount;

import com.daycount.commands.DayCountCommand;
import com.daycount.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

public class DayCount extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        this.getLogger().info("[Ultimate Daycount] Starting plugin...");
        
        this.config = new Config(this);
        
        this.getCommand("daycount").setExecutor(new DayCountCommand(this));

        startOnlineCheckTask();

        this.getLogger().info("[Ultimate Daycount] Ultimate Daycount loaded!");
        this.getLogger().info("[Ultimate Daycount] Current days: " + getDayCount());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("[Ultimate Daycount] Plugin disabled!");
    }

    private void startOnlineCheckTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (!config.isPauseTimeWhenEmpty()) return;
            
            boolean playersOnline = !Bukkit.getOnlinePlayers().isEmpty();
            Bukkit.getWorlds().forEach(world -> {
                boolean currentCycle = Boolean.TRUE.equals(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
                if (currentCycle != playersOnline) {
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, playersOnline);
                    if (config.isDebugEnabled()) {
                        getLogger().info("[Ultimate Daycount] Daylight cycle set to: " + playersOnline);
                    }
                }
            });
        }, 0L, 20L);
    }

    public int getDayCount() {
        if (Bukkit.getWorlds().isEmpty()) return 0;
        return (int) (Bukkit.getWorlds().get(0).getFullTime() / 24000);
    }

    public Config getConfiguration() {
        return config;
    }
}
