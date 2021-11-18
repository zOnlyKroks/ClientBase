package de.client.base.command;

import de.client.base.command.commands.BindCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommands();
    }

    public void addCommands() {
        addCommand(new BindCommand());
    }

    private void addCommand(Command command) {
        commands.add(command);
    }

    @NotNull public List<Command> getCommands() {
        return commands;
    }

}
