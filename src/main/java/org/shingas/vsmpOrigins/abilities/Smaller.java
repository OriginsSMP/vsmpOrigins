package org.shingas.vsmpOrigins.abilities;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Smaller implements AttributeModifierAbility, VisibleAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.SCALE;
    }

    @Override
    public double getAmount(Player player) {
        return -0.35;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_SCALAR;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "smaller");
    }

    @Override
    public String title() {
        return "Smaller";
    }

    @Override
    public String description() {
        return "You are 35% smaller than normal players.";
    }
}
