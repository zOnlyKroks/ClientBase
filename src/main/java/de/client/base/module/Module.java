package de.client.base.module;

import de.client.base.newConfig.IntSetting;
import de.client.base.newConfig.ModuleConfig;
import net.minecraft.client.MinecraftClient;

public abstract class Module {

    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    public final           ModuleConfig    config;
    private final          String          name;
    private final          String          description;
    private final          Category        category;
    public                 IntSetting      keybind;
    private                boolean         enabled;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.config = new ModuleConfig();
        keybind = config.create(new IntSetting.Builder(-1).min(-1).name("Keybind").description("The keybind to toggle the module with").get());
        // this.config.create("Keybind", -1).description("The keybind to toggle the module with");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    protected abstract void onEnable();

    protected abstract void onDisable();
}
