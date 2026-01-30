package org.shingas.vsmpOrigins;

import org.shingas.vsmpOrigins.abilities.PoisonImmunity;
import org.shingas.vsmpOrigins.abilities.Smaller;
import org.shingas.vsmpOrigins.abilities.ExtraHearts;
import org.shingas.vsmpOrigins.abilities.dragonborn.DragonFireBall;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfHaste;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfNightVision;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfOreFortune;
import org.shingas.vsmpOrigins.abilities.dwarf.DwarfSize;
import org.shingas.vsmpOrigins.abilities.harpy.HarpySize;
import org.shingas.vsmpOrigins.abilities.BeeFlight;
import org.shingas.vsmpOrigins.abilities.kraken.Pescetarianism;
import org.shingas.vsmpOrigins.abilities.kraken.Slightly;
import org.shingas.vsmpOrigins.abilities.vampire.*;
import org.shingas.vsmpOrigins.commands.DwarfVisionToggle;
import org.shingas.vsmpOrigins.commands.VampTransformToggle;
import org.shingas.vsmpOrigins.data.DataConfig;
import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.types.Ability;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class vsmpOrigins extends OriginsAddon {

    private final DwarfNightVision dwarfNightVision = new DwarfNightVision();
    private final DwarfHaste dwarfHaste = new DwarfHaste();
    private final DaylightWeakness dailyLightWeakness = new DaylightWeakness();
    private static DataConfig dataConfig = null;
    private static vsmpOrigins instance = null;

    @Override
    public @NotNull String getNamespace() {
        return "vsmporigins";
    }

    @Override
    public @NotNull List<Ability> getRegisteredAbilities() {
        return List.of(
                //Dwarf Abilities
                new DwarfOreFortune(),
                new DwarfSize(),
                dwarfHaste,
                dwarfNightVision,
                new Carnivorous(),
                new SunProtection(),
                //new RainProtection(),
                new VampireTransformation(),
                dailyLightWeakness,
                new DragonFireBall(),
                new PoisonImmunity(),
                //Kraken Abilities
                new Slightly(),
                new Pescetarianism(),
                //Harpy Abilities
                new HarpySize(),
                //Size
                new Smaller(),
                //Extra Hearts
                new ExtraHearts(),
                //Bee Abilities
                new BeeFlight()
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
        //getLogger().info("  - shingasorigins:rain_protection");

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
