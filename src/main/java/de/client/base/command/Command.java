package de.client.base.command;

import net.minecraft.client.MinecraftClient;

public class Command {

    public MinecraftClient mc;
    private String command;
    private String alias;

    public Command(String command, String alias) {
        this.command = command;
        this.alias = alias;
        this.mc = MinecraftClient.getInstance();
    }

    public String getCommand() {
        return command;
    }

    public String getAlias() {
        return alias;
    }

    public void runCommand(String trimmedMessage) {}
}
