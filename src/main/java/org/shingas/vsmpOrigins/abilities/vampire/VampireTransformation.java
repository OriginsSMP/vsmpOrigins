package org.shingas.vsmpOrigins.abilities.vampire;

import org.shingas.vsmpOrigins.vsmpOrigins;
import org.shingas.vsmpOrigins.data.DataConfig;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class VampireTransformation implements VisibleAbility, Listener {


    @Override
    public String description() {
        return "Transform other players into vampires by killing them.";
    }

    @Override
    public String title() {
        return "Vampire Transformation";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "vampire_transformation");
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player killed)) return;
        Player killer = killed.getKiller();
        runForAbility(killer, player -> {
            DataConfig dataConfig = vsmpOrigins.getDataConfig();
            if (dataConfig.getVampToggle(player.getUniqueId())) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "origin set " + killed.getName() + " origin vampire");
            }
        });
    }
}
