package de.client.base.command;

import net.minecraft.client.MinecraftClient;

/**
 * A class representing a command
 */
public abstract class Command {

    /**
     * The minecraft instance
     */
    public final  MinecraftClient mc;
    /**
     * The main command name
     */
    private final String          command;
    /**
     * The command's aliases
     */
    private final String[]        alias;

    /**
     * Constructs a new command instance
     *
     * @param command The name of the command
     * @param aliases Triggers for the command
     */
    public Command(String command, String... aliases) {
        this.command = command;
        this.alias = aliases;
        this.mc = MinecraftClient.getInstance();
    }

    /**
     * Gets the command's name
     *
     * @return The command name
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the command's aliases
     *
     * @return The command's aliases
     */
    public String[] getAliases() {
        return alias;
    }

    /**
     * Called when the command is executed
     *
     * @param args The arguments (excluding prefix and command name) specified
     */
    public abstract void runCommand(String[] args);
}
