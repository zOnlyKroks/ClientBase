package de.client.base.module;

import de.client.base.config.ModuleConfig;
import net.minecraft.client.MinecraftClient;

public abstract class Module {

    public final ModuleConfig config;
    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    private String name;
    private String description;
    private Category category;
    private boolean toggled;

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.config = new ModuleConfig();
        this.config.create("Keybind", -1).description("The keybind to toggle the module with");
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

    public boolean isToggled() {
        return toggled;
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }
}
