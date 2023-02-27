package de.client.base.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {
    /**
     * Sends a message to the user, client side
     *
     * @param s The message to send. Formatting using § is allowed
     */
    public static void send(final String s) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(s));
    }
}
