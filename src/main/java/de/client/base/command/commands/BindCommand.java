package de.client.base.command.commands;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.module.Module;
import de.client.base.module.ModuleManager;
import org.lwjgl.glfw.GLFW;

public class BindCommand extends Command {

    public BindCommand() {
        super("BindCommand", "bind");
    }

    @Override
    public void runCommand() {
        ClientBase.getModuleManager().getModuleByName("Step").setKeybind(GLFW.GLFW_KEY_O);
    }
}
