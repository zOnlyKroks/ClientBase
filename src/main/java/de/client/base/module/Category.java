package de.client.base.module;

/**
 * Enum containing each category of module
 */
public enum Category {

    COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), EXPLOIT("Exploit"), PLAYER("Player"), MISC("Miscellaneous");
    String name = "";

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
