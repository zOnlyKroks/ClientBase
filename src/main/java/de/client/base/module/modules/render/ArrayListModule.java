package de.client.base.module.modules.render;

import de.client.base.ClientBase;
import de.client.base.event.RenderIngameHudEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

import java.awt.*;

public class ArrayListModule extends Module {

    public ArrayListModule() {
        super("ArrayList", "Usefull to know what is active", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @EventTarget
    public void onRenderIngameHud(RenderIngameHudEvent event) {
        TextRenderer renderer = MinecraftClient.getInstance().inGameHud.getFontRenderer();
        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int y = 2;
        final int counter[] = {1};

        for(Module module : ClientBase.getModuleManager().getModules()) {
            if(!(module.getName().equalsIgnoreCase("arraylist"))) {
                if(module.isToggled()) {
                    renderer.drawWithShadow(event.getStack(), module.getName(), width - renderer.getWidth(module.getName()) - 2,y, rainbow(counter[0] * 300));
                    y += renderer.fontHeight;
                    counter[0]++;
                }
            }
        }
    }

    public static int rainbow(int delay) {
        double rainbowstate = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowstate %= 360;
        return Color.getHSBColor((float) rainbowstate / 360, 0.5f,1f).getRGB();
    }
}
