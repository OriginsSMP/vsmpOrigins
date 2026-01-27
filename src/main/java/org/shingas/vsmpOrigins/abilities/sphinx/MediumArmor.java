package org.shingas.vsmpOrigins.abilities.sphinx;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.util.ShortcutUtils;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MediumArmor implements Listener, VisibleAbility {
    
    private static final List<Material> ALLOWED_ARMOR = List.of(
            Material.CHAINMAIL_HELMET,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS,
            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS,
            Material.GOLDEN_HELMET,
            Material.GOLDEN_CHESTPLATE,
            Material.GOLDEN_LEGGINGS,
            Material.GOLDEN_BOOTS,
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS
    );

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "medium_armor");
    }

    @Override
    public String description() {
        return "You can not wear any heavy armor (armor with protection values higher than iron).";
    }

    @Override
    public String title() {
        return "Need for Mobility";
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        if (event.getNewOrigin() == null) return;
        runForAbility(event.getPlayer(), player -> {
            ItemStack helmet = player.getInventory().getHelmet();
            ItemStack chestplate = player.getInventory().getChestplate();
            ItemStack leggings = player.getInventory().getLeggings();
            ItemStack boots = player.getInventory().getBoots();
            
            if (helmet != null && !ALLOWED_ARMOR.contains(helmet.getType())) {
                player.getInventory().setHelmet(null);
                ShortcutUtils.giveItemWithDrops(player, helmet);
            }
            if (chestplate != null && !ALLOWED_ARMOR.contains(chestplate.getType())) {
                player.getInventory().setChestplate(null);
                ShortcutUtils.giveItemWithDrops(player, chestplate);
            }
            if (leggings != null && !ALLOWED_ARMOR.contains(leggings.getType())) {
                player.getInventory().setLeggings(null);
                ShortcutUtils.giveItemWithDrops(player, leggings);
            }
            if (boots != null && !ALLOWED_ARMOR.contains(boots.getType())) {
                player.getInventory().setBoots(null);
                ShortcutUtils.giveItemWithDrops(player, boots);
            }
        });
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventorySlots().contains(38)) {
            if (event.getWhoClicked() instanceof Player player) {
                checkArmorEvent(event, player, event.getOldCursor());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (event.getCursor() != null) {
                if (isArmor(event.getCursor().getType())) {
                    if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                        checkArmorEvent(event, player, event.getCursor());
                    }
                }
            }
            if (event.isShiftClick()) {
                if (event.getCurrentItem() == null) return;
                if (event.getInventory().getType() != InventoryType.CRAFTING) return;
                if (MaterialTags.HELMETS.isTagged(event.getCurrentItem().getType()) && isNull(player.getEquipment().getHelmet())) {
                    checkArmorEvent(event, player, event.getCurrentItem());
                }
                if (MaterialTags.CHESTPLATES.isTagged(event.getCurrentItem().getType()) && isNull(player.getEquipment().getChestplate())) {
                    checkArmorEvent(event, player, event.getCurrentItem());
                }
                if (MaterialTags.LEGGINGS.isTagged(event.getCurrentItem().getType()) && isNull(player.getEquipment().getLeggings())) {
                    checkArmorEvent(event, player, event.getCurrentItem());
                }
                if (MaterialTags.BOOTS.isTagged(event.getCurrentItem().getType()) && isNull(player.getEquipment().getBoots())) {
                    checkArmorEvent(event, player, event.getCurrentItem());
                }
            }
            if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
                if (event.getHotbarButton() == -1) {
                    if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                        checkArmorEvent(event, player, player.getInventory().getItemInOffHand());
                    }
                }
            }
            if (event.getClick() == ClickType.NUMBER_KEY) {
                if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                    ItemStack item = player.getInventory().getItem(event.getHotbarButton());
                    if (item != null) {
                        checkArmorEvent(event, player, item);
                    }
                }
            }
        }
    }

    public boolean isNull(ItemStack item) {
        if (item == null) return true;
        return item.getType().isAir();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            if (event.getItem() == null) return;
            if (MaterialTags.HELMETS.isTagged(event.getItem().getType())) {
                checkArmorEvent(event, event.getPlayer(), event.getItem());
            }
            if (MaterialTags.CHESTPLATES.isTagged(event.getItem().getType())) {
                checkArmorEvent(event, event.getPlayer(), event.getItem());
            }
            if (MaterialTags.LEGGINGS.isTagged(event.getItem().getType())) {
                checkArmorEvent(event, event.getPlayer(), event.getItem());
            }
            if (MaterialTags.BOOTS.isTagged(event.getItem().getType())) {
                checkArmorEvent(event, event.getPlayer(), event.getItem());
            }
        }
    }
    
    @EventHandler
    public void onBlockDispenseArmor(BlockDispenseArmorEvent event) {
        if (event.getTargetEntity() instanceof Player player) {
            checkArmorEvent(event, player, event.getItem());
        }
    }

    public void checkArmorEvent(Cancellable event, Player p, ItemStack armor) {
        runForAbility(p, player -> {
            if (ALLOWED_ARMOR.contains(armor.getType())) return;
            event.setCancelled(true);
        });
    }

    public boolean isArmor(Material material) {
        return MaterialTags.HELMETS.isTagged(material) || 
               MaterialTags.CHESTPLATES.isTagged(material) || 
               MaterialTags.LEGGINGS.isTagged(material) || 
               MaterialTags.BOOTS.isTagged(material);
    }
}
