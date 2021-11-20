package de.client.base.keybinding;

import de.client.base.ClientBase;
import de.client.base.module.Module;

import java.util.HashMap;
import java.util.Map;

public class KeybindingManager {

    public static final Map<Module, Keybind> keybindMap = new HashMap<>();

    public static void init() {
        for (Module module : ClientBase.getModuleManager().getModules()) {
            keybindMap.put(module, new Keybind(module.keybind.getValue()));
        }
    }

    public static void updateSingle(int kc, int action) {
        if (kc == -1) {
            return; // JESSE WE FUCKED UP
        }
        if (action == 1) { // key pressed
            for (Module o : keybindMap.keySet().toArray(new Module[0])) {
                Keybind kb = keybindMap.get(o);
                if (kb.keycode == kc) {
                    o.toggle();
                }
            }
        }
    }

    public static void reload() {
        keybindMap.clear();
        init();
    }

}
