package com.semivanilla.protectedspawners.listeners;

import com.destroystokyo.paper.loottable.LootableBlockInventory;
import com.semivanilla.protectedspawners.config.Config;
import com.semivanilla.protectedspawners.util.Util;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final JavaPlugin plugin;

    public static Object2BooleanMap<UUID> playerMap;
    private final List<Material> lootableMaterials;

    public BlockBreakListener(JavaPlugin plugin) {
        this.plugin = plugin;
        playerMap = new Object2BooleanOpenHashMap<>();
        playerMap.defaultReturnValue(false);
        lootableMaterials = new ArrayList<>();
        for (String mat : Config.lootableMaterials) {
            Material material = Material.getMaterial(mat.toUpperCase());
            if (material == null) continue;
            lootableMaterials.add(material);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!unbreakable(block)) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (playerMap.getBoolean(uuid)) {
            playerMap.replace(uuid, true, false);
            return;
        }

        if (block.getState() instanceof Lootable lootable) {
            // covers the following : CHEST, DISPENSER, DROPPER, HOPPER, SHULKERBOX
            if (!lootable.hasLootTable()) return;
        }

        event.setCancelled(true);
        if (!Config.blockBreakMessage.isEmpty())
            player.sendMessage(Util.parseMiniMessage(Config.blockBreakMessage, null));
    }

    private boolean unbreakable(Block block) {
        return lootableMaterials.contains(block.getType());
    }

}
