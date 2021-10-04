package com.alcachofra.xinada.utils;

public class Score {
    private boolean checked = false;
    private final String name;
    private final Roles.Role role;

    public Score(String name, Roles.Role role) {
        this.name = name;
        this.role = role;
    }

    public boolean isChecked() {
        return checked;
    }

    public void uncheck() {
        this.checked = false;
    }

    public void check() {
        this.checked = true;
    }

    public String getName() {
        return name;
    }

    public Roles.Role getRole() {
        return role;
    }
}
