package org.shingas.vsmpOrigins.abilities;

import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.abilities.types.TriggerableAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.cooldowns.Cooldowns;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BeeFlight implements Listener, CooldownAbility, VisibleAbility, TriggerableAbility {

    private final Map<UUID, Long> lastSneakTime = new HashMap<>();

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "bee_flight");
    }

    @Override
    public String title() {
        return "Bee Flight";
    }

    @Override
    public String description() {
        return "Double-tap sneak while in the air to activate levitation.";
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(400, "bee_flight");
    }

    @Override
    public @NotNull Trigger getTrigger() {
        return Trigger.builder(TriggerType.SNEAK_ON, this)
                .build(event -> {
                    Player player = event.player();
                    
                    // Check if player is in the air (not on ground)
                    if (player.isOnGround()) return;
                    
                    // Check cooldown
                    if (hasCooldown(player)) return;
                    
                    UUID uuid = player.getUniqueId();
                    long currentTime = System.currentTimeMillis();
                    long lastSneak = lastSneakTime.getOrDefault(uuid, 0L);
                    
                    // If last sneak was within 500ms (0.5 seconds), it's a double tap
                    if (currentTime - lastSneak <= 500) {
                        // Apply levitation for 10 seconds (200 ticks)
                        player.addPotionEffect(new PotionEffect(
                            PotionEffectType.LEVITATION,
                            200,
                            0,
                            false,
                            false
                        ));
                        
                        setCooldown(player);
                        lastSneakTime.remove(uuid);
                    } else {
                        // First tap, record the time
                        lastSneakTime.put(uuid, currentTime);
                    }
                });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Clean up to prevent memory leaks
        lastSneakTime.remove(event.getPlayer().getUniqueId());
    }
}
