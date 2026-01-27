package org.shingas.vsmpOrigins.abilities.dwarf;

import org.shingas.vsmpOrigins.vsmpOrigins;
import org.shingas.vsmpOrigins.data.DataConfig;
import com.starshootercity.abilities.types.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class DwarfNightVision implements Ability {

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "dwarf_nightvision");
    }

    public void startTask(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(
                plugin,
                scheduledTask -> {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        runForAbility(player, p -> {
                            DataConfig dataConfig = vsmpOrigins.getDataConfig();
                            if (dataConfig.getDwarfVision(player.getUniqueId())) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 1));
                            }
                        });
                    }
                },
                60L,
                60L
        );
    }
}
