package org.shingas.vsmpOrigins.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PoisonImmunity implements VisibleAbility, Listener {

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasfantasyorigins:poison_immunity");
    }

    @Override
    public String description() {
        return "Poisonous potions do not affect you.";
    }

    @Override
    public String title() {
        return "Poison Immunity";
    }

    @EventHandler
    public void PlayerEffectEvent(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player player)) { return; }
        if (event.getAction() != EntityPotionEffectEvent.Action.ADDED ||
                event.getAction() != EntityPotionEffectEvent.Action.CHANGED) { return; }

        runForAbility(player, p -> {
            if (event.getModifiedType() == PotionEffectType.POISON) {
                event.setCancelled(true);
            }
        });
    }
}
