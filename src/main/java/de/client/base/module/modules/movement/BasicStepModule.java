package de.client.base.module.modules.movement;

import de.client.base.module.Category;
import de.client.base.module.Module;

public class BasicStepModule extends Module {

    //    final MultiValue mode = (MultiValue) this.config.create("Mode", "Static", "Vanilla", "Static", "3D", "Jetpack").description("Which type of fly to do");

    public BasicStepModule() {
        super("Step", "Space Bars are overrated", Category.MOVEMENT);
    }

    @Override protected void onEnable() {
        mc.player.stepHeight = 69f;
    }

    @Override protected void onDisable() {
        mc.player.stepHeight = 0.6F;
    }


}
