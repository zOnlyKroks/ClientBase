package de.client.base.gui;

import ca.weblite.objc.Client;
import de.client.base.ClientBase;
import de.client.base.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.util.TextRenderingUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.Color;

public class ClickGui extends Screen {

    public ClickGui(Text title) {
        super(title);
    }

    @Override protected void init() {
    }


    @Override public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int textColor = Color.WHITE.getRGB();

        for (Module mod : ClientBase.getModuleManager().getModules()) {
            MinecraftClient.getInstance().inGameHud.getFontRenderer().drawWithShadow(matrices, mod.getName(), width - textRenderer.getWidth(mod.getName()), height, textColor);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }
}
