package de.client.base.config;

public class IntSetting extends SettingBase<Integer> {
    int min, max;

    public IntSetting(Integer defaultValue, String name, String description, int min, int max) {
        super(defaultValue, name, description);
        this.min = min;
        this.max = max;
    }

    @Override public Integer parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static class Builder extends SettingBase.Builder<Builder, Integer, IntSetting> {
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;

        public Builder(Integer defaultValue) {
            super(defaultValue);
        }

        public Builder min(int min) {
            this.min = min;
            return this;
        }

        public Builder max(int max) {
            this.max = max;
            return this;
        }

        @Override public IntSetting get() {
            return new IntSetting(defaultValue, name, description, min, max);
        }
    }
}
