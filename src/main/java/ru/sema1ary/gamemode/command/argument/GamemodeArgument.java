package ru.sema1ary.gamemode.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class GamemodeArgument extends ArgumentResolver<CommandSender, GameMode> {
    private final Map<String, GameMode> gamemodeMap = new HashMap<>();

    public GamemodeArgument() {
        gamemodeMap.put("0", GameMode.SURVIVAL);
        gamemodeMap.put("1", GameMode.CREATIVE);
        gamemodeMap.put("2", GameMode.ADVENTURE);
        gamemodeMap.put("3", GameMode.SPECTATOR);
    }

    @Override
    protected ParseResult<GameMode> parse(Invocation<CommandSender> invocation, Argument<GameMode> argument, String s) {
        return ParseResult.success(gamemodeMap.get(s));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument,
                                    SuggestionContext context) {

        return SuggestionResult.of("0", "1", "2", "3");
    }
}
