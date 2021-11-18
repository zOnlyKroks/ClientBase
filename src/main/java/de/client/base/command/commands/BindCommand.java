package de.client.base.command.commands;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.util.ChatUtil;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import java.util.Locale;

public class BindCommand extends Command {

    public BindCommand() {
        super("BindCommand", "bind");
    }

    @Override
    public void runCommand(String trimmedMessage) {
        String lastCharacter = String.valueOf(trimmedMessage.charAt(trimmedMessage.length() -1)).toLowerCase(Locale.ROOT).replace(" ", "");
        char keyChar = lastCharacter.charAt(0);
        String module = StringUtils.removeEnd(trimmedMessage, lastCharacter);
        int keyCode = keyChar;

        System.out.println(lastCharacter);
        System.out.println(module);
        System.out.println(lastCharacter);
        System.out.println(keyCode);

    }
}
