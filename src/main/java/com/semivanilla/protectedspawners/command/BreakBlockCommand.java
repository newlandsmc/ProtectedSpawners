package com.semivanilla.protectedspawners.command;

import com.semivanilla.protectedspawners.ProtectedSpawners;
import com.semivanilla.protectedspawners.config.Config;
import com.semivanilla.protectedspawners.listeners.BlockBreakListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BreakBlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            return true;
        }
        BlockBreakListener.playerMap.put(player.getUniqueId(), true);
        new BukkitRunnable(){
            @Override
            public void run() {
                BlockBreakListener.playerMap.put(player.getUniqueId(), false);
            }
        }.runTaskLater(ProtectedSpawners.getInstance(), Config.commandTimeOut);
        return true;
    }
}
