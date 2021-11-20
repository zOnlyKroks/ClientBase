package de.client.base.command.commands;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.keybinding.KeybindingManager;
import de.client.base.module.Module;
import de.client.base.util.ChatUtil;

public class BindCommand extends Command {

    public BindCommand() {
        super("BindCommand", "bind");
    }

    @Override public void runCommand(String[] args) {
        if (args.length < 2) {
            ChatUtil.send("I need the module and key to bind");
            return;
        }
        String mod = args[0];
        String key = args[1];
        if (key.length() != 1) {
            ChatUtil.send("One character as key allowed only");
            return;
        }

        int kc = key.toUpperCase().charAt(0);
        Module m = ClientBase.getModuleManager().getModuleByName(mod);
        if (m == null) {
            ChatUtil.send("Cant find that module");
            return;
        }
        ChatUtil.send("BIND " + m.getName() + " -> " + kc + " (" + ((char) kc) + ")");
        //m.config.get("Keybind").setValue(kc);
        m.keybind.setValue(kc);
        KeybindingManager.reload();
    }
}
