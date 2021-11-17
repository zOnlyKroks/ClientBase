package de.client.base.command;

public class Command {

    private String command;
    private String alias;

    public Command(String command, String alias) {
        this.command = command;
        this.alias = alias;
    }

    public String getCommand() {
        return command;
    }

    public String getAlias() {
        return alias;
    }

    public void runCommand() {}
}
