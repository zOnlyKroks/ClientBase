package de.client.base.module.modules.render;

import de.client.base.ClientBase;
import de.client.base.event.RenderIngameHudEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import de.client.base.newConfig.DoubleSetting;

import java.awt.Color;
import java.util.Comparator;

public class ArrayListModule extends Module {
    DoubleSetting speed = this.config.create(new DoubleSetting.Builder(2000).min(10).max(5000).name("Speed").description("How fast to do RGB").get());

    public ArrayListModule() {
        super("ArrayList", "Usefull to know what is active", Category.RENDER);
    }

    @Override protected void onEnable() {
        EventManager.register(this);
    }

    @Override protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget public void onRenderIngameHud(RenderIngameHudEvent event) {
        int yOffset = 1;
        float rgb = (System.currentTimeMillis() % 2000) / 2000f;
        for (Module module : ClientBase.getModuleManager().getModules().stream().sorted(Comparator.comparingInt(value -> -mc.textRenderer.getWidth(value.getName()))).toList()) {
            if (module.isEnabled()) {
                rgb += 0.1f;
                rgb %= 1;
                Color current = Color.getHSBColor(rgb, 0.6f, 1f);
                mc.textRenderer.draw(event.stack(), module.getName(), mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(module.getName()) - 1, yOffset, current.getRGB());
                yOffset += mc.textRenderer.fontHeight;
            }
        }
    }
}
