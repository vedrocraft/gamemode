package ru.sema1ary.gamemode.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

import java.util.HashMap;
import java.util.Map;

@Command(name = "gamemode", aliases = {"gm"})
public class GamemodeCommand {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final ConfigService configService;
    private final Map<Integer, GameMode> gamemodeMap = new HashMap<>();

    public GamemodeCommand(ConfigService configService) {
        this.configService = configService;
        gamemodeMap.put(0, GameMode.SURVIVAL);
        gamemodeMap.put(1, GameMode.CREATIVE);
        gamemodeMap.put(2, GameMode.SPECTATOR);
        gamemodeMap.put(3, GameMode.ADVENTURE);
    }

    @Async
    @Execute(name = "reload")
    @Permission("gamemode.reload")
    void reload(@Context CommandSender sender) {
        configService.reload();
        sender.sendMessage(miniMessage.deserialize(configService.get("reload-message")));
    }

    @Execute
    @Permission("gamemode.use")
    void execute(@Context Player sender, @Arg("режим") GameMode gameMode) {
        if(!sender.hasPermission("gamemode." + gameMode.toString())) {
            sender.sendMessage(miniMessage.deserialize(configService.get("no-perms-message")));
            return;
        }

        sender.setGameMode(gameMode);
        sender.sendMessage(miniMessage.deserialize(
               ((String) configService.get("change-message")).replace("{gamemode}", gameMode.toString())
        ));
    }

    @Execute
    @Permission("gamemode.use")
    void execute(@Context Player sender, @Arg("режим") int id) {
        GameMode gameMode = gamemodeMap.get(id);
        if(!sender.hasPermission("gamemode." + gameMode.toString())) {
            sender.sendMessage(miniMessage.deserialize(configService.get("no-perms-message")));
            return;
        }

        sender.setGameMode(gameMode);
        sender.sendMessage(miniMessage.deserialize(
                ((String) configService.get("change-message")).replace("{gamemode}", gameMode.toString())
        ));
    }

    @Execute
    @Permission("gamemode.other")
    void execute(@Context CommandSender sender, @Arg("игрок") Player target
            , @Arg("режим") GameMode gameMode) {
        target.setGameMode(gameMode);
        sender.sendMessage(miniMessage.deserialize(
                ((String) configService.get("target-change-message")).replace("{gamemode}", gameMode.toString())
                        .replace("{player}", target.getName())
        ));
    }

    @Async
    @Execute
    @Permission("gamemode.other")
    void execute(@Context Player sender, @Arg("игрок") Player target
            , @Arg("режим") int id) {
        GameMode gameMode = gamemodeMap.get(id);
        target.setGameMode(gameMode);
        sender.sendMessage(miniMessage.deserialize(
                ((String) configService.get("target-change-message")).replace("{gamemode}", gameMode.toString())
                        .replace("{player}", target.getName())
        ));
    }
}
