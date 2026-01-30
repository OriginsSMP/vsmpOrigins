package org.shingas.vsmpOrigins.abilities.dragonborn;

import org.shingas.vsmpOrigins.vsmpOrigins;
import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.cooldowns.Cooldowns;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DragonFireBall implements VisibleAbility, CooldownAbility, Listener {


    private final EntityType dragon_fireball = EntityType.FIREBALL;
    private final MiniMessage mm = MiniMessage.miniMessage();

    private final Set<Material> NEEDED_ITEMS = Set.of(
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.COPPER_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD
    );


    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "dragon_fireball");
    }

    @Override
    public String description() {
        return "Shoots a fire ball in the direction you're looking at";
    }

    @Override
    public String title() {
        return "Dragon Fire Ball";
    }


    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(
                600,  // 15 seconds = 300 ticks (20 ticks per second)
                "dragon_fireball"  // cooldown icon
        );
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if (event.getAction() != Action.RIGHT_CLICK_AIR || !NEEDED_ITEMS.contains(event.getItem().getType())) return;

            NamespacedKey dragonFireballKey = new NamespacedKey(vsmpOrigins.getInstance(), "dragonFireball");
            runForAbility(player, p -> {

                if (hasCooldown(p)) {
                    long remaining = getCooldown(p) / 20; // Convert ticks to seconds
                    String msg = "<red>Fire Dash on cooldown: " + remaining + "s";
                    p.sendActionBar(mm.deserialize(msg));
                    return;
                }

                Vector dir = p.getEyeLocation().getDirection().normalize();
                Location loc = p.getEyeLocation();
                Entity fireball = loc.getWorld().spawnEntity(loc, dragon_fireball);
                fireball.setVelocity(dir.multiply(1.5));
                fireball.getPersistentDataContainer().set(dragonFireballKey, PersistentDataType.STRING, player.getUniqueId().toString());
                setCooldown(p);
            });
        } catch (Exception e) {
            //Player is not holding an item
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Fireball fireball)) return;
        if (!(event.getEntity() instanceof Player player)) return;

        NamespacedKey dragonFireballKey = new NamespacedKey(vsmpOrigins.getInstance(), "dragonFireball");
        PersistentDataContainer data = fireball.getPersistentDataContainer();

        // Does this fireball have an owner?
        if (!data.has(dragonFireballKey, PersistentDataType.STRING)) return;
        String ownerUUID = data.get(dragonFireballKey, PersistentDataType.STRING);

        // Check if the damaged player is the owner
        if (player.getUniqueId().toString().equals(ownerUUID)) {
            // Cancel damage
            event.setCancelled(true);
            // optionally remove the fireball
            fireball.remove();
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof Fireball fireball)) return;

        NamespacedKey dragonFireballKey = new NamespacedKey(vsmpOrigins.getInstance(), "dragonFireball");
        if (!fireball.getPersistentDataContainer().has(dragonFireballKey, PersistentDataType.STRING)) return;

        event.blockList().clear(); // No block damage
    }

}
