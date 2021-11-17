package de.client.base;

import de.client.base.command.CommandManager;
import de.client.base.module.ModuleManager;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

public class ClientBase implements ModInitializer {

    @Getter
    private static ModuleManager moduleManager;
    @Getter
    private static CommandManager commandManager;

    @Override
    public void onInitialize() {
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        System.out.println("Initialized Client");
    }
}
