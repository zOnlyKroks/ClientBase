package de.client.base.config;

import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleConfig {

    final  List<DynamicValue<?>> config               = new ArrayList<>();
    final  List<Propgroup>       groups               = new ArrayList<>();
    //public boolean               organizeClickGUIList = true;

    public void addProxy(DynamicValue<?> v) {
        config.add(v);
        //if (Atomic.client.textRenderer != null) config.sort(Comparator.comparingInt(value -> Atomic.client.textRenderer.getWidth(value.getKey())));
    }

    public <T> DynamicValue<T> create(String key, T value) {
        DynamicValue<T> nv = new DynamicValue<>(key, value);
        addProxy(nv);
        return nv;
    }

    public SliderValue create(String key, double value, double min, double max, int prc) {
        SliderValue sv = new SliderValue(key, MathHelper.clamp(value, min, max), min, max, prc);
        addProxy(sv);
        return sv;
    }

    public BooleanValue create(String key, boolean initial) {
        BooleanValue bv = new BooleanValue(key, initial);
        addProxy(bv);
        return bv;
    }

    public ColorValue create(String key, Color initial, boolean rgb) {
        ColorValue cv = new ColorValue(key, initial);
        cv.setRGB(rgb);
        addProxy(cv);
        return cv;
    }

    public void addGroup(Propgroup group) {
        this.groups.add(group);
    }

    public List<Propgroup> getGroups() {
        return groups;
    }

    public Propgroup createPropGroup(String name, DynamicValue<?>... children) {
        Propgroup e = new Propgroup(name);
        e.addAll(children);
        addGroup(e);
        return e;
    }

    public MultiValue create(String key, String value, String... possible) {
        MultiValue ev = new MultiValue(key, value, possible);
        addProxy(ev);
        return ev;
    }

    public DynamicValue<?> get(String key) {
        for (DynamicValue<?> dynamicValue : config) {
            if (dynamicValue.getKey().equalsIgnoreCase(key)) {
                return dynamicValue;
            }
        }
        return null;
    }

    public List<DynamicValue<?>> getAll() {
        return config;
    }

}
