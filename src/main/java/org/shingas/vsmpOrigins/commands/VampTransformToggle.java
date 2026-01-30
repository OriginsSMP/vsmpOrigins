package org.shingas.vsmpOrigins.commands;

import org.shingas.vsmpOrigins.vsmpOrigins;
import org.shingas.vsmpOrigins.data.DataConfig;
import org.shingas.vsmpOrigins.data.PlayerData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class VampTransformToggle implements CommandExecutor, TabExecutor {

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
                dataConfig.setVampToggle(p.getUniqueId(), true);
                p.sendMessage(mm.deserialize("<dark_gray>[</dark_gray><aqua>VampToggle</aqua><dark_gray>]</dark_gray> Toggled <aqua>on</aqua> vampire transformation when killing a player."));
                break;
            case "off":
                dataConfig.setVampToggle(p.getUniqueId(), false);
                p.sendMessage(mm.deserialize("<dark_gray>[</dark_gray><aqua>VampToggle</aqua><dark_gray>]</dark_gray> Toggled <aqua>off</aqua> vampire transformation when killing a player."));
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
