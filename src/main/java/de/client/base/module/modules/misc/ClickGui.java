package de.client.base.module.modules.misc;

import de.client.base.ClientBase;
import de.client.base.event.UpdateEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", "Click click", Category.MISC);
    }

    @Override protected void onEnable() {
        EventManager.register(this);
    }

    @Override protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {

    }
}
