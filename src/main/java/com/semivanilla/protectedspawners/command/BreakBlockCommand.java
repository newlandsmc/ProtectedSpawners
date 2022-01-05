package com.semivanilla.protectedspawners.command;

import com.semivanilla.protectedspawners.ProtectedSpawners;
import com.semivanilla.protectedspawners.config.Config;
import com.semivanilla.protectedspawners.listeners.BlockBreakListener;
import com.semivanilla.protectedspawners.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class BreakBlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (BlockBreakListener.playerMap.putIfAbsent(uuid, false) || BlockBreakListener.playerMap.replace(uuid, false,  true)) {
            if (!Config.commandBreakMessage.isEmpty())
                player.sendMessage(Util.parseMiniMessage(Config.commandBreakMessage, null));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (BlockBreakListener.playerMap.replace(player.getUniqueId(), true, false)) {
                        if (!Config.commandTimeOutMessage.isEmpty())
                            player.sendMessage(Util.parseMiniMessage(Config.commandTimeOutMessage, null));
                    }
                }
            }.runTaskLater(ProtectedSpawners.getInstance(), Config.commandTimeOut);
        }
        return true;
    }
}
