package de.client.base;

import de.client.base.module.ModuleManager;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

public class ClientBase implements ModInitializer {

    @Getter
    private static ModuleManager moduleManager;

    @Override
    public void onInitialize() {
        moduleManager = new ModuleManager();
        System.out.println("Initialized Client");
    }
}
