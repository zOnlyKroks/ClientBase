package de.client.base.command;

import de.client.base.command.commands.BindCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager {

    /**
     * A list of all registered commands
     */
    private final List<Command> commands = new ArrayList<>();

    /**
     * Constructs a new command manager
     */
    public CommandManager() {
        addCommands();
    }

    /**
     * Registers the commands
     */
    public void addCommands() {
        addCommand(new BindCommand());
    }

    /**
     * Adds a command to the list of registered commands, registering it
     *
     * @param command The command instance
     */
    private void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Gets all registered commands
     *
     * @return The list of registered commands
     */
    @NotNull public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

}
