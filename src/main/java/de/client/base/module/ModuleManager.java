package de.client.base.module;

import de.client.base.module.modules.movement.BasicStepModule;
import de.client.base.module.modules.movement.NoFallModule;
import de.client.base.module.modules.render.ArrayListModule;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public void addModules() {
        addModule(new BasicStepModule());
        addModule(new NoFallModule());
        addModule(new ArrayListModule());
    }

    private void addModule(Module module) {
        modules.add(module);
    }

    @NotNull public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }

        return null;
    }
}
