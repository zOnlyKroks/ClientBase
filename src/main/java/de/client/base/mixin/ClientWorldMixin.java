package de.client.base.mixin;

import de.client.base.event.UpdateEvent;
import de.client.base.eventapi.EventManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class) public class ClientWorldMixin {

    @Inject(at = @At("HEAD"), method = "tick") private void tickTime(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        EventManager.call(new UpdateEvent());
    }

}
