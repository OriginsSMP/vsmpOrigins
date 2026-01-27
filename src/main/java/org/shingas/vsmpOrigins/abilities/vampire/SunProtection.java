package org.shingas.vsmpOrigins.abilities.vampire;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SunProtection implements VisibleAbility, Listener {

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "sun_protection");
    }

    @Override
    public String title() {
        return "Sun Protection";
    }

    @Override
    public String description() {
        return "Wearing a copper helmet protects you from the sun's burning rays.";
    }

    private boolean isWearingProtectiveHelmet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        return helmet != null && helmet.getType() == Material.COPPER_HELMET;
    }

    private boolean isInSunlight(Player player) {
        return player.getWorld().isDayTime() &&
                player.getLocation().getBlock().getLightFromSky() >= 14;
    }

    private boolean isNotInFire(Player player) {
        return player.getLocation().getBlock().getType() != Material.FIRE;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isOnline()) return;

        if (event instanceof EntityCombustByEntityEvent || event instanceof EntityCombustByBlockEvent) {
            return;
        }

        runForAbility(player, p -> {
            if (isWearingProtectiveHelmet(p) && isInSunlight(p) && isNotInFire(p)) {
                event.setCancelled(true);
                p.setFireTicks(0);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isOnline()) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.FIRE &&
                event.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }

        runForAbility(player, p -> {
            if (isWearingProtectiveHelmet(p) && isInSunlight(p) && isNotInFire(p)) {
                event.setCancelled(true);
                p.setFireTicks(0);
            }
        });
    }
}