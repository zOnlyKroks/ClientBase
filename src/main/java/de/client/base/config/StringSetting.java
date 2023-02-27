package de.client.base.config;

public class StringSetting extends SettingBase<String> {

    public StringSetting(String defaultValue, String name, String description) {
        super(defaultValue, name, description);
    }

    @Override public String parse(String value) {
        return value;
    }

    public static class Builder extends SettingBase.Builder<Builder, String, StringSetting> {

        public Builder(String defaultValue) {
            super(defaultValue);
        }

        @Override public StringSetting get() {
            return new StringSetting(defaultValue, name, description);
        }
    }
}
