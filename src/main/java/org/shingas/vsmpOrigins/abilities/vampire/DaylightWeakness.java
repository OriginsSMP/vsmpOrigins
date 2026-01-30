package org.shingas.vsmpOrigins.abilities.vampire;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DaylightWeakness implements VisibleAbility, Listener {

    private final Map<UUID, Boolean> sunlightCache = new ConcurrentHashMap<>();

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "daylight_weakness");
    }

    @Override
    public String title() {
        return "Daylight Weakness";
    }

    @Override
    public String description() {
        return "You suffer from weakness during the day when exposed to sunlight.";
    }

    private boolean isInSunlight(Player player) {
        return player.getWorld().isDayTime() &&
                player.getLocation().getBlock().getLightFromSky() >= 12;
    }

    public void startTask(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                runForAbility(player, p -> {
                    if (p.isOnline()) {
                        try {
                            boolean inSun = isInSunlight(p);
                            sunlightCache.put(p.getUniqueId(), inSun);

                            if (inSun) {
                                p.addPotionEffect(new PotionEffect(
                                        PotionEffectType.WEAKNESS,
                                        40,
                                        1,
                                        false,
                                        true,
                                        true
                                ));
                            }
                        } catch (Exception e) {
                            sunlightCache.put(p.getUniqueId(), false);
                        }
                    }
                });
            }
        }, 0L, 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sunlightCache.remove(event.getPlayer().getUniqueId());
    }
}