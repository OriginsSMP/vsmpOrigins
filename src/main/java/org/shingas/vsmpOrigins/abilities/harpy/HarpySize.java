package org.shingas.vsmpOrigins.abilities.harpy;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HarpySize implements AttributeModifierAbility, VisibleAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.SCALE;
    }

    @Override
    public double getAmount(Player player) {
        return -0.1;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_SCALAR;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "harpysize");
    }

    @Override
    public String title() {
        return "Harpy Size";
    }

    @Override
    public String description() {
        return "You are 10% smaller than normal players.";
    }
}
