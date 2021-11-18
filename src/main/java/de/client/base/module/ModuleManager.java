package de.client.base.module;

import de.client.base.module.modules.movement.BasicStepModule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public void addModules() {
        addModule(new BasicStepModule());
    }

    private void addModule(Module module) {
        modules.add(module);
    }

    @NotNull
    public List<Module> getModules() {
        return modules;
    }

    @NotNull
    public Module getModuleByName(String name) {
        for(Module m : modules) {
            if(m.getName().equalsIgnoreCase(name)) return m;
        }

        return null;
    }
}
