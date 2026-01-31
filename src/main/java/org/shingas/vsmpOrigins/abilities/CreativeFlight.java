package org.shingas.vsmpOrigins.abilities;

import com.starshootercity.abilities.types.FlightAllowingAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class CreativeFlight implements VisibleAbility, FlightAllowingAbility, Listener {

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "creative_flight");
    }

    @Override
    public String title() {
        return "Creative Flight";
    }

    @Override
    public String description() {
        return "You have the ability to fly like in creative mode.";
    }

    @Override
    public boolean canFly(Player player) {
        // Only allow flight if not in creative/spectator (those already have flight)
        return player.getGameMode() != GameMode.CREATIVE && 
               player.getGameMode() != GameMode.SPECTATOR;
    }

    @Override
    public float getFlightSpeed(Player player) {
        return 0.05f; // Default creative flight speed
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        runForAbility(player, p -> {
            if (canFly(p)) {
                p.setAllowFlight(true);
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        runForAbility(player, p -> {
            // Remove flight when player leaves
            if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
                p.setAllowFlight(false);
                p.setFlying(false);
            }
        });
    }
}
