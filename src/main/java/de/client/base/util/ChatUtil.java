package de.client.base.util;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {

    @NotNull
    public static void send(final String s) {
        MinecraftClient.getInstance().player.sendChatMessage(s);
    }
}
