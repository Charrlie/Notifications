package com.vanishedd.notifications;

import com.vanishedd.notifications.listeners.PlayerActivity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Notifications extends JavaPlugin {
    private static Notifications instance;

    @Override
    public void onEnable() {
        instance = this;

        registerConfig();
        registerListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Notifications getInstance() {
        return instance;
    }

    private void registerConfig() {
        saveDefaultConfig();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerActivity(), this);
    }
}