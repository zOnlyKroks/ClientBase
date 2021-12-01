package de.client.base;

import de.client.base.command.CommandManager;
import de.client.base.keybinding.KeybindingManager;
import de.client.base.module.Module;
import de.client.base.module.ModuleManager;
import de.client.base.util.ConfigManager;
import lombok.Getter;
import lombok.SneakyThrows;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.io.File;

@Environment(EnvType.CLIENT)
public class ClientBase implements ModInitializer {

    public static String CLIENT_ID = "clientbase";

    public static File BASE = new File(MinecraftClient.getInstance().runDirectory, CLIENT_ID);

    @Getter private static ModuleManager  moduleManager;
    @Getter private static CommandManager commandManager;

    @SuppressWarnings("ResultOfMethodCallIgnored") @SneakyThrows @Override public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveState));
        if (BASE.exists() && !BASE.isDirectory()) {
            BASE.delete();
        }
        if (!BASE.exists()) {
            BASE.mkdir();
        }
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        KeybindingManager.init();
        ConfigManager.loadState();
        for (Module m : moduleManager.getModules()) {
            if (m.getName().equalsIgnoreCase("arraylist")) {
                m.toggle();
            }
        }
        System.out.println("Initialized Client");
    }
}
