package de.client.base.mixin;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.util.ChatUtil;
import de.client.base.util.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ClientPlayerEntity.class) public class ClientPlayerEntityMixin {

    private static final String PREFIX = ".";

    @Inject(at = @At("HEAD"), method = "sendMovementPackets") private void sendMovementPackets(CallbackInfo ci) {
        EventManager.call(new MoveEvent());
    }

    @Inject(method = "sendMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true) void sendChatMsg(Text message, CallbackInfo ci) {
        if (message.getString().startsWith(PREFIX)) {
            ci.cancel();
            MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message.getString()); // add to history
            ci.cancel();
            String msgTrimmed = message.toString().substring(PREFIX.length());
            String[] msgSplit = msgTrimmed.split(" +");
            String command = msgSplit[0].toLowerCase();
            String[] args = new String[msgSplit.length - 1];
            System.arraycopy(msgSplit, 1, args, 0, args.length);
            Command found = null;
            for (Command command1 : ClientBase.getCommandManager().getCommands()) {
                if (Arrays.stream(command1.getAliases()).anyMatch(s -> s.equalsIgnoreCase(command))) {
                    found = command1;
                    break;
                }
            }
            if (found == null) {
                ChatUtil.send("§cCommand wasn't found!");
                return;
            }
            found.runCommand(args);
        }
    }

    @Inject(method = "tick", at = @At("HEAD")) void tick(CallbackInfo ci) {
        if (!ConfigManager.enabled) {
            ConfigManager.enableModules();
        }
    }

}
