package org.shingas.vsmpOrigins.commands;

import org.shingas.vsmpOrigins.vsmpOrigins;
import org.shingas.vsmpOrigins.data.DataConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DwarfVisionToggle implements CommandExecutor, TabExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player p)) return false;
        if (args.length == 0) {
            return false;
        }

        DataConfig dataConfig = vsmpOrigins.getDataConfig();
        switch(args[0]) {
            case "on":
                dataConfig.setDwarfVision(p.getUniqueId(), true);
                p.sendMessage(mm.deserialize("<dark_gray>[</dark_gray><aqua>DwarfVision</aqua><dark_gray>]</dark_gray> Toggled <aqua>on</aqua> your dwarf vision."));
                break;
            case "off":
                dataConfig.setDwarfVision(p.getUniqueId(), false);
                p.sendMessage(mm.deserialize("<dark_gray>[</dark_gray><aqua>DwarfVision</aqua><dark_gray>]</dark_gray> Toggled <aqua>off</aqua> your dwarf vision."));
                break;
            default:
                return false;
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return List.of("on", "off");
        }
        return List.of();
    }
}
