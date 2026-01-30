package org.shingas.vsmpOrigins.abilities.kraken;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Slightly implements AttributeModifierAbility, VisibleAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.SCALE;
    }

    @Override
    public double getAmount(Player player) {
        return 0.25;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_SCALAR;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("vsmporigins", "slightly");
    }

    @Override
    public String title() {
        return "Slightly Larger";
    }

    @Override
    public String description() {
        return "You are 3.5x larger than normal players.";
    }
}
