package de.client.base.mixin;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.util.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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

    /**
     *
     */
    /*@Overwrite
    public void sendChatMessage(String message) {
        if(message.toLowerCase().startsWith(PREFIX)) {

        }
        *//*if(message.charAt(0) == '.') {
            for(Command cmd : ClientBase.getCommandManager().getCommands()) {
                if(message.contains(cmd.getAlias())) {
                    String trimmedMessage = message.replace(".", "");
                    String moreTrimmedMessage = trimmedMessage.replace(cmd.getAlias() + "", "");
                    cmd.runCommand(moreTrimmedMessage);
                }
            }
        }else{
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ChatMessageC2SPacket(message));
        }*//*
    }*/
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true) void sendChatMsg(String message, CallbackInfo ci) {
        if (message.toLowerCase().startsWith(PREFIX)) {
            ci.cancel();
            MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message); // add to history
            ci.cancel();
            String msgTrimmed = message.substring(PREFIX.length());
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
                //                MinecraftClient.getInstance().player.sendMessage(Text.of("§cCommand wasn't found"), false);
                return;
            }
            found.runCommand(args);
        }
    }

}
