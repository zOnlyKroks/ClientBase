package de.client.base.mixin;

import de.client.base.ClientBase;
import de.client.base.event.KeyboardEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.module.ModuleManager;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKeyEvent_1(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        ClientBase.getModuleManager().handleKeypress(key);

        EventManager.call(new KeyboardEvent(key));
    }

}
