package de.client.base.module;

import de.client.base.module.modules.misc.AutoclickerModule;
import de.client.base.module.modules.misc.ClickGui;
import de.client.base.module.modules.movement.BasicStepModule;
import de.client.base.module.modules.movement.NoFallModule;
import de.client.base.module.modules.movement.Velocity;
import de.client.base.module.modules.render.ArrayListModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The module manager
 */
public class ModuleManager {

    /**
     * A list of all registered modules
     */
    private final List<Module> modules = new ArrayList<>();

    /**
     * Constructs a new module manager
     */
    public ModuleManager() {
        addModules();
    }

    /**
     * Adds all modules we want to register
     */
    public void addModules() {
        addModule(new BasicStepModule());
        addModule(new NoFallModule());
        addModule(new ArrayListModule());
        addModule(new AutoclickerModule());
        addModule(new ClickGui());
        addModule(new Velocity());
    }

    /**
     * Registers a module
     *
     * @param module The module to register
     */
    private void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Returns all modules
     *
     * @return The modules
     */
    @NotNull public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }

    /**
     * Returns the first module which has the name we specified, null if no module matches
     *
     * @param name The name we want to find
     * @return The module found, null otherwise
     */
    @Nullable public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }

        return null;
    }
}
