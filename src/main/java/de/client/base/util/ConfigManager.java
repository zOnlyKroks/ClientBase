package de.client.base.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.client.base.ClientBase;
import de.client.base.keybinding.KeybindingManager;
import de.client.base.module.Module;
import de.client.base.newConfig.SettingBase;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    static final  List<Module> toBeEnabled = new ArrayList<>();
    static final  File         CONFIG_FILE;
    public static boolean      loaded      = false;
    public static boolean      enabled     = false;

    static {
        CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir() + "/config.clientbase");
    }

    public static void saveState() {
        if (!loaded || !enabled) {
            System.out.println("Not saving config because we didnt load it yet");
            return;
        }
        System.out.println("Saving state");
        JsonObject base = new JsonObject();
        JsonArray enabled = new JsonArray();
        JsonArray config = new JsonArray();
        for (Module module : ClientBase.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                enabled.add(module.getName());
            }
            JsonObject currentConfig = new JsonObject();
            currentConfig.addProperty("name", module.getName());
            JsonArray pairs = new JsonArray();
            for (SettingBase<?> dynamicValue : module.config.getSettings()) {
                JsonObject jesus = new JsonObject();
                jesus.addProperty("key", dynamicValue.getName());
                jesus.addProperty("value", dynamicValue.getValue() + "");
                pairs.add(jesus);
            }
            currentConfig.add("pairs", pairs);
            config.add(currentConfig);
        }
        base.add("enabled", enabled);
        base.add("config", config);
        try {
            FileUtils.write(CONFIG_FILE, base.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save config!");
        }
    }

    public static void loadState() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            if (!CONFIG_FILE.isFile()) {
                //noinspection ResultOfMethodCallIgnored
                CONFIG_FILE.delete();
            }
            if (!CONFIG_FILE.exists()) {
                return;
            }
            String retrv = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
            JsonObject config = new JsonParser().parse(retrv).getAsJsonObject();
            if (config.has("config") && config.get("config").isJsonArray()) {
                JsonArray configArray = config.get("config").getAsJsonArray();
                for (JsonElement jsonElement : configArray) {
                    if (jsonElement.isJsonObject()) {
                        JsonObject jobj = jsonElement.getAsJsonObject();
                        String name = jobj.get("name").getAsString();
                        Module j = ClientBase.getModuleManager().getModuleByName(name);
                        if (j == null) {
                            continue;
                        }
                        if (jobj.has("pairs") && jobj.get("pairs").isJsonArray()) {
                            JsonArray pairs = jobj.get("pairs").getAsJsonArray();
                            for (JsonElement pair : pairs) {
                                JsonObject jo = pair.getAsJsonObject();
                                String key = jo.get("key").getAsString();
                                String value = jo.get("value").getAsString();
                                SettingBase<?> val = j.config.get(key);
                                if (val != null) {
                                    val.accept(value);
                                }
                            }
                        }
                    }
                }
            }

            if (config.has("enabled") && config.get("enabled").isJsonArray()) {
                for (JsonElement enabled : config.get("enabled").getAsJsonArray()) {
                    String name = enabled.getAsString();
                    Module m = ClientBase.getModuleManager().getModuleByName(name);
                    if (m != null) {
                        toBeEnabled.add(m);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            KeybindingManager.reload();
        }
    }

    public static void enableModules() {
        if (enabled) {
            return;
        }
        enabled = true;
        for (Module module : toBeEnabled) {

            module.toggle();
        }
    }

}
