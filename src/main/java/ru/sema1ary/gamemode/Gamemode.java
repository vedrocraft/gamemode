package ru.sema1ary.gamemode;

import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.gamemode.command.GamemodeCommand;
import ru.vidoskim.bukkit.service.ConfigService;
import ru.vidoskim.bukkit.service.impl.ConfigServiceImpl;
import ru.vidoskim.bukkit.util.LiteCommandUtil;
import service.ServiceManager;

public final class Gamemode extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        new LiteCommandUtil().create("gamemode",
                new GamemodeCommand(ServiceManager.getService(ConfigService.class)));
    }

    @Override
    public void onDisable() {
        ServiceManager.disableServices();
    }
}
