package de.client.base.newConfig;

public class BooleanSetting extends SettingBase<Boolean> {

    public BooleanSetting(Boolean defaultValue, String name, String description) {
        super(defaultValue, name, description);
    }

    @Override public Boolean parse(String value) {
        return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1"));
    }

    public static class Builder extends SettingBase.Builder<Builder, Boolean, BooleanSetting> {

        public Builder(Boolean defaultValue) {
            super(defaultValue);
        }

        @Override public BooleanSetting get() {
            return new BooleanSetting(defaultValue, name, description);
        }
    }
}
