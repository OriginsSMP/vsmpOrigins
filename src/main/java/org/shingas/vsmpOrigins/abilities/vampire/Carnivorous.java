package org.shingas.vsmpOrigins.abilities.vampire;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Carnivorous implements VisibleAbility, Listener {

    private static final Set<Material> FORBIDDEN_FOODS = Set.of(
            Material.APPLE,
            Material.BAKED_POTATO,
            Material.BEETROOT,
            Material.BEETROOT_SOUP,
            Material.BREAD,
            Material.CARROT,
            Material.CHORUS_FRUIT,
            Material.DRIED_KELP,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.GOLDEN_APPLE,
            Material.GOLDEN_CARROT,
            Material.HONEY_BOTTLE,
            Material.MELON_SLICE,
            Material.MUSHROOM_STEW,
            Material.POISONOUS_POTATO,
            Material.POTATO,
            Material.PUMPKIN_PIE,
            Material.SUSPICIOUS_STEW,
            Material.SWEET_BERRIES,
            Material.GLOW_BERRIES,
            Material.COOKIE
    );

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "carnivorous");
    }

    @Override
    public String title() {
        return "Carnivorous";
    }

    @Override
    public String description() {
        return "You can only eat meat-based foods. Your body rejects plant-based nutrition.";
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        runForAbility(player, p -> {
            if (FORBIDDEN_FOODS.contains(item.getType())) {
                event.setCancelled(true);
                p.sendActionBar(Component.text(
                        "You can only consume meat!",
                        NamedTextColor.RED
                ));
            }
        });
    }
}