package de.client.base.config;

import de.client.base.util.Utils;

import java.awt.*;

public class ColorValue extends DynamicValue<String> {

    boolean isRGB = false;

    public ColorValue(String key, Color value) {
        super(key, value.getRGB() + "");
    }

    public Color getColor() {
        int v = Integer.parseInt(this.getValue().replaceAll(";", ""));
        return new Color(!isRGB ? v : Utils.getCurrentRGB().getRGB());
    }

    public boolean isRGB() {
        return isRGB;
    }

    public void setRGB(boolean RGB) {
        isRGB = RGB;
    }

    @Override public String getValue() {
        return super.getValue() + (isRGB ? ";" : "");
    }

    @Override public void setValue(Object value) {

        String v = null;

        if (!(value instanceof String)) {
            return;
        }
        boolean isRGB = v.endsWith(";");
        if (isRGB) {
            v = v.replaceAll(";", "");
        }
        Color parsed;
        try {
            parsed = new Color(Integer.parseInt(v));
        } catch (Exception ignored) {
            return;
        }
        this.isRGB = isRGB;
        this.value = parsed.getRGB() + "";

        onValueChanged();
    }
}
