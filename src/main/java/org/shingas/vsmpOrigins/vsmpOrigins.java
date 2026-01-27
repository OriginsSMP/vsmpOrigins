package org.shingas.vsmpOrigins;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.types.Ability;
import org.jetbrains.annotations.NotNull;
import org.shingas.vsmpOrigins.abilities.PoisonImmunity;
import org.shingas.vsmpOrigins.abilities.dragonborn.DragonFireBall;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfHaste;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfNightVision;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfOreFortune;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfSize;
import org.shingas.vsmpOrigins.abilities.vampire.Carnivorous;
import org.shingas.vsmpOrigins.abilities.vampire.DaylightWeakness;
import org.shingas.vsmpOrigins.abilities.vampire.SunProtection;
import org.shingas.vsmpOrigins.abilities.vampire.VampireTransformation;
import org.shingas.vsmpOrigins.commands.DwarfVisionToggle;
import org.shingas.vsmpOrigins.commands.VampTransformToggle;
import org.shingas.vsmpOrigins.data.DataConfig;

import java.util.List;

public final class vsmpOrigins extends OriginsAddon {

    private final DwarfNightVision dwarfNightVision = new DwarfNightVision();
    private final DwarfHaste dwarfHaste = new DwarfHaste();
    private final DaylightWeakness dailyLightWeakness = new DaylightWeakness();
    private static DataConfig dataConfig = null;
    private static vsmpOrigins instance = null;

    @Override
    public @NotNull String getNamespace() {
        return "shingasorigins";
    }

    @Override
    public @NotNull List<Ability> getRegisteredAbilities() {
        return List.of(
                new DwarfOreFortune(),
                new DwarfSize(),
                dwarfHaste,
                dwarfNightVision,
                new Carnivorous(),
                new SunProtection(),
                new VampireTransformation(),
                dailyLightWeakness,
                new DragonFireBall(),
                new PoisonImmunity()
        );
    }

    @Override
    public void onRegister() {
        getLogger().info("Shingas Fantasy Origins addon has been loaded!");
        instance = this;

        getLogger().info("Loading config...");
        dataConfig = new DataConfig(this);
        getLogger().info("Loaded config!");

        getLogger().info("Registering commands:");
        getCommand("vamptoggle").setExecutor(new VampTransformToggle());
        getCommand("dwarfvision").setExecutor(new DwarfVisionToggle());
        getLogger().info(" - /vamptoggle <on/off>");
        getLogger().info(" - /dwarfvision <on/off>");

        getLogger().info("Dwarf abilities registered:");
        getLogger().info("  - shingasorigins:dwarf_size");
        getLogger().info("  - shingasorigins:dwarf_haste");
        getLogger().info("  - shingasorigins:dwarf_nightvision");
        getLogger().info("  - shingasorigins:dwarf_ore_fortune");

        getLogger().info("Vampire abilities registered:");
        getLogger().info("  - shingasorigins:carnivorous");
        getLogger().info("  - shingasorigins:daylight_weakness");
        getLogger().info("  - shingasorigins:sun_protection");
        getLogger().info("  - shingasorigins:vampire_transformation");

        getLogger().info("Dragonborn abilities registered:");
        getLogger().info("  - shingasorigins:dragon_fireball");

        getLogger().info("Abilities registered:");
        getLogger().info("  - shingasorigins:poisonimmunity");

        // Start tasks for
        dwarfHaste.startTask(this);
        dwarfNightVision.startTask(this);
        dailyLightWeakness.startTask(this);
    }

    @Override
    public void onDisable() {
        dataConfig.save();
    }

    public static DataConfig getDataConfig() { return dataConfig; }

    public static vsmpOrigins getInstance() { return instance; }
}
