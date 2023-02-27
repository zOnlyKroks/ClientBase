package de.client.base.mixin;

import de.client.base.event.PacketReadEvent;
import de.client.base.event.PacketSendEvent;
import de.client.base.eventapi.EventManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketCallbacks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
        PacketReadEvent event = new PacketReadEvent(packet);
        EventManager.call(event);
    }

    /**
     * @author zOnlyKroks
     */
    @Inject(at = @At("RETURN"), method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V")
    public void send(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        EventManager.call(event);
        if(event.isCancelled()) ci.cancel();
    }
}
