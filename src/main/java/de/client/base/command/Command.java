package de.client.base.command;

import net.minecraft.client.MinecraftClient;

public abstract class Command {

    public final  MinecraftClient mc;
    private final String          command;
    private final String[]        alias;

    public Command(String command, String... aliases) {
        this.command = command;
        this.alias = aliases;
        this.mc = MinecraftClient.getInstance();
    }

    public String getCommand() {
        return command;
    }

    public String[] getAliases() {
        return alias;
    }

    public abstract void runCommand(String[] args);
}
