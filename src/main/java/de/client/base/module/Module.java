package de.client.base.module;

import net.minecraft.client.MinecraftClient;

public abstract class Module {

    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    private String name;
    private String description;
    private Category category;
    private int keybind;
    private boolean toggled;

    protected Module(String name, String description, Category category, int keybind) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keybind = keybind;
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

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        if(toggled) {
            onEnable();
        }else {
            onDisable();
        }
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }
}
