package ru.sema1ary.gamemode;

import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.gamemode.command.GamemodeCommand;
import ru.sema1ary.gamemode.command.argument.GamemodeArgument;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

public final class Gamemode extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        LiteCommandBuilder.builder()
                .commands(new GamemodeCommand(ServiceManager.getService(ConfigService.class)))
                .argument(GameMode.class, new GamemodeArgument())
                .build();
    }

    @Override
    public void onDisable() {
        ServiceManager.disableServices();
    }
}
