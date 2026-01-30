package org.shingas.vsmpOrigins.abilities.dwarf;

import com.starshootercity.abilities.types.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DwarfOreFortune implements Ability, Listener {

    private final Map<Material, Material> oreMap = new HashMap<>() {{
        put(Material.COAL_ORE, Material.COAL);
        put(Material.DIAMOND_ORE, Material.DIAMOND);
        put(Material.EMERALD_ORE, Material.EMERALD);
        put(Material.COPPER_ORE, Material.RAW_COPPER);
        put(Material.IRON_ORE, Material.RAW_IRON);
        put(Material.GOLD_ORE, Material.RAW_GOLD);
        put(Material.REDSTONE_ORE, Material.REDSTONE);
        put(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET);
        put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
    }};

    private final List<Material> pickaxeTypes = List.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
    );

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "dwarf_ore_fortune");
    }

    private boolean isPickaxe(Material type) {
        return pickaxeTypes.contains(type);
    }

    private boolean hasSilkTouch(ItemStack item) {
        return item != null &&
                isPickaxe(item.getType()) &&
                item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH);
    }

    private boolean isOre(Material type) {
        return oreMap.containsKey(type);
    }

    private ItemStack getExtraOre(Material type) {
        double chance = Math.random(); // 0.0 to 1.0
        int amount;

        if (chance < 0.40) {       // 40% chance
            amount = 1;
        } else if (chance < 0.475) { // 7.5% chance
            amount = 2;
        } else if (chance < 0.50) {  // 2.5% chance
            amount = 3;
        } else {
            // 50% chance - no extra ore drops
            return new ItemStack(Material.AIR, 0);
        }

        return new ItemStack(oreMap.get(type), amount);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();
        Material type = block.getType();
        if (!isOre(type)) return;
        if (hasSilkTouch(pickaxe)) return;

        runForAbility(player, p -> {
            Location loc = block.getLocation();
            ItemStack extraOre = getExtraOre(type);
            if (extraOre.getType() != Material.AIR) {
                loc.getWorld().dropItemNaturally(loc, extraOre);
            }
        });
    }

}
