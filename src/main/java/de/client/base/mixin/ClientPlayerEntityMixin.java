package de.client.base.mixin;

import de.client.base.ClientBase;
import de.client.base.command.Command;
import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "sendMovementPackets")
    private void sendMovementPackets(CallbackInfo ci) {
        EventManager.call(new MoveEvent());
    }

    /**
     * @author zOnlyKroks
     */
    @Overwrite
    public void sendChatMessage(String message) {
        if(message.charAt(0) == '.') {
            for(Command cmd : ClientBase.getCommandManager().getModules()) {
                if(message.contains(cmd.getAlias())) {
                    cmd.runCommand();
                }
            }
        }else{
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ChatMessageC2SPacket(message));
        }
    }

}
