package org.shingas.vsmpOrigins.abilities.kraken;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Pescetarianism implements VisibleAbility, Listener {

    private static final Set<Material> FORBIDDEN_FOODS = Set.of(
            Material.APPLE,
            Material.BAKED_POTATO,
            Material.BEETROOT,
            Material.BEETROOT_SOUP,
            Material.BREAD,
            Material.CARROT,
            Material.CHORUS_FRUIT,
            Material.COOKED_CHICKEN,
            Material.COOKED_MUTTON,
            Material.COOKED_PORKCHOP,
            Material.COOKED_RABBIT,
            Material.COOKIE,
            Material.DRIED_KELP,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.GLOW_BERRIES,
            Material.GOLDEN_APPLE,
            Material.GOLDEN_CARROT,
            Material.HONEY_BOTTLE,
            Material.MELON_SLICE,
            Material.MUSHROOM_STEW,
            Material.POISONOUS_POTATO,
            Material.POTATO,
            Material.PUMPKIN_PIE,
            Material.RABBIT_STEW,
            Material.BEEF,
            Material.CHICKEN,
            Material.MUTTON,
            Material.PORKCHOP,
            Material.RABBIT,
            Material.ROTTEN_FLESH,
            Material.SPIDER_EYE,
            Material.COOKED_BEEF,
            Material.SUSPICIOUS_STEW,
            Material.SWEET_BERRIES
    );

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "pescetarianism");
    }

    @Override
    public String title() {
        return "pescetarianism";
    }

    @Override
    public String description() {
        return "You can only eat fish-based foods. Your body rejects all others.";
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        runForAbility(player, p -> {
            if (FORBIDDEN_FOODS.contains(item.getType())) {
                event.setCancelled(true);
                p.sendActionBar(net.kyori.adventure.text.Component.text(
                        "You can only consume fish!",
                        net.kyori.adventure.text.format.NamedTextColor.RED
                ));
            }
        });
    }
}