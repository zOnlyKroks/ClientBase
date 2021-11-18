package de.client.base;

import de.client.base.command.CommandManager;
import de.client.base.keybinding.KeybindingManager;
import de.client.base.module.ModuleManager;
import de.client.base.util.ConfigManager;
import lombok.Getter;
import lombok.SneakyThrows;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class ClientBase implements ModInitializer {

    @Getter
    private static ModuleManager moduleManager;
    @Getter
    private static CommandManager commandManager;
    public static       File              CONFIG_STORAGE;

    @SneakyThrows
    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveState));
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        CONFIG_STORAGE = new File(FabricLoader.getInstance().getConfigDir() + "/atomicConfigs.json");
        CONFIG_STORAGE.createNewFile();
        KeybindingManager.init();
        ConfigManager.loadState();
        System.out.println("Initialized Client");
    }
}
