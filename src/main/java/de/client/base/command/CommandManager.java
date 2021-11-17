package de.client.base.command;

import de.client.base.command.commands.BindCommand;
import de.client.base.module.Module;
import de.client.base.module.modules.movement.BasicStepModule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> commands = new ArrayList<>();

    public CommandManager() {
        addModules();
    }

    public void addModules() {
        addModule(new BindCommand());
    }

    private void addModule(Command command) {
        commands.add(command);
    }

    @NotNull
    public List<Command> getModules() {
        return commands;
    }

    @NotNull
    public Command getModuleByName(String name) {
        for(Command command : commands) {
            if (command.getCommand() == name) return command;
        }
        return null;
    }

}
