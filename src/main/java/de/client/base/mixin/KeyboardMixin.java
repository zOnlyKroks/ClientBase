package de.client.base.mixin;

import de.client.base.event.KeyboardEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.keybinding.KeybindingManager;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class) public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5))
    private void onKeyEvent_1(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        EventManager.call(new KeyboardEvent(key));
    }

    @Inject(method = "onKey", at = @At("RETURN")) void atomic_postKeyPressed(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle() && MinecraftClient
                .getInstance().currentScreen == null) { // make sure we are in game and the screen has been there for at least 10 ms

            KeybindingManager.updateSingle(key, action);
        }
    }

}
