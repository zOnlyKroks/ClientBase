package de.client.base.module;

import de.client.base.newConfig.IntSetting;
import de.client.base.newConfig.ModuleConfig;
import net.minecraft.client.MinecraftClient;

/**
 * A container describing a module
 */
public abstract class Module {

    /**
     * The minecraft instance
     */
    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    /**
     * The configuration of this module
     */
    public final           ModuleConfig    config;
    /**
     * The name of this module
     */
    private final          String          name;
    /**
     * The description of this module
     */
    private final          String          description;
    /**
     * The category this module belongs in
     */
    private final          Category        category;
    /**
     * The keybind of this module
     */
    public                 IntSetting      keybind;
    /**
     * Whether this module is currently enabled
     */
    private                boolean         enabled;

    /**
     * Constructs a new module
     *
     * @param name        The name of the module
     * @param description The description of this module
     * @param category    Which category this module belongs in
     */
    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.config = new ModuleConfig();
        keybind = config.create(new IntSetting.Builder(-1).min(-1).name("Keybind").description("The keybind to toggle the module with").get());
        // this.config.create("Keybind", -1).description("The keybind to toggle the module with");
    }

    /**
     * Gets the name of this module
     *
     * @return This module's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of this module
     *
     * @return This module's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the category of this module
     *
     * @return This module's category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Whether this module is enabled or not
     *
     * @return true if the module is enabled, false if otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets this module's enabled state
     *
     * @param enabled The new enabled state
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Turns this module on if it's off, off when otherwise
     */
    public void toggle() {
        setEnabled(!isEnabled());
    }

    /**
     * Gets called when the module switches from disabled to enabled, not implemented in base class
     */
    protected abstract void onEnable();

    /**
     * Gets called when the module switches from enabled to disabled, not implemented in base class
     */
    protected abstract void onDisable();
}
