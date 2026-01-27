package org.shingas.vsmpOrigins.abilities.dwarf;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DwarfSize implements AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.SCALE;
    }

    @Override
    public double getAmount(Player player) {
        return -0.5;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_SCALAR;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "dwarf_size");
    }
}
