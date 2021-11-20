package de.client.base.newConfig;

import java.util.function.Consumer;

public abstract class SettingBase<V> {
    public final String name, description;
    V defaultValue;
    V value;

    public SettingBase(V defaultValue, String name, String description) {
        this.name = name;
        this.description = description;
        this.defaultValue = this.value = defaultValue;
    }

    public abstract V parse(String value);

    public void accept(String value) {
        this.setValue(this.parse(value));
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    @SuppressWarnings("unchecked") public abstract static class Builder<B extends Builder<?, ?, ?>, V, S extends SettingBase<?>> {
        String name = "none", description = "";
        V           defaultValue;
        Consumer<V> changed;

        protected Builder(V defaultValue) {
            this.defaultValue = defaultValue;
        }

        public B name(String name) {
            this.name = name;
            return getThis();
        }

        public B description(String description) {
            this.description = description;
            return getThis();
        }

        public B defaultValue(V defaultValue) {
            this.defaultValue = defaultValue;
            return getThis();
        }

        public B onChanged(Consumer<V> changed) {
            this.changed = changed;
            return getThis();
        }

        public abstract S get();

        protected B getThis() {
            return (B) this;
        }
    }
}
