package de.client.base.config;

import java.util.ArrayList;
import java.util.List;

public class Propgroup {

    final String                name;
    final List<DynamicValue<?>> children = new ArrayList<>();

    public Propgroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Propgroup withChild(DynamicValue<?> c) {
        children.add(c);
        return this;
    }

    public Propgroup addAll(DynamicValue<?>... children) {
        for (DynamicValue<?> child : children) {
            withChild(child);
        }
        return this;
    }

    public List<DynamicValue<?>> getChildren() {
        return children;
    }

}
