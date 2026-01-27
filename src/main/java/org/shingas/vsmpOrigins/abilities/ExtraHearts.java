package org.shingas.vsmpOrigins.abilities;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExtraHearts implements AttributeModifierAbility, VisibleAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.MAX_HEALTH;
    }

    @Override
    public double getAmount(Player player) {
        return 10.0; // 5 hearts = 10 health points (each heart is 2 health)
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("shingasorigins", "extra_hearts");
    }

    @Override
    public String title() {
        return "Extra Hearts";
    }

    @Override
    public String description() {
        return "You have 5 extra hearts of health.";
    }
}
