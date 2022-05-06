package com.semivanilla.protectedspawners;

import com.semivanilla.protectedspawners.command.BreakBlockCommand;
import com.semivanilla.protectedspawners.config.Config;
import com.semivanilla.protectedspawners.listeners.BlockBreakListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectedSpawners extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Config.init();

        registerListener(new BlockBreakListener(instance));
        getCommand("breakconfirm").setExecutor(new BreakBlockCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
