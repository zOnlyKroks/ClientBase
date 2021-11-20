package de.client.base.util;

import java.awt.Color;

public class Utils {

    /**
     * Returns an RGB color based on time
     *
     * @return The color
     */
    public static Color getCurrentRGB() {
        return new Color(Color.HSBtoRGB((System.currentTimeMillis() % 4750) / 4750f, 0.5f, 1));
    }

}
