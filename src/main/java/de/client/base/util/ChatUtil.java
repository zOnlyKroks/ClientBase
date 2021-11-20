package de.client.base.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

public class ChatUtil {
    /**
     * Sends a message to the user, client side
     *
     * @param s The message to send. Formatting using § is allowed
     */
    public static void send(final String s) {
        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of(s), MinecraftClient.getInstance().player.getUuid());
    }
}
