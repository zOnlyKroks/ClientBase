package de.client.base.newConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of settings
 */
public class SettingsGroup {
    String name, description;
    List<SettingBase<?>> settings;

    SettingsGroup(String name, String description, List<SettingBase<?>> settings) {
        this.name = name;
        this.description = description;
        this.settings = settings;
    }

    public List<SettingBase<?>> getSettings() {
        return settings;
    }

    public static class Builder {
        String name = "none", description = "";
        List<SettingBase<?>> s = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String desc) {
            this.description = desc;
            return this;
        }

        public Builder settings(SettingBase<?>... settings) {
            s.addAll(List.of(settings));
            return this;
        }

        public SettingsGroup get() {
            return new SettingsGroup(name, description, s);
        }
    }
}
