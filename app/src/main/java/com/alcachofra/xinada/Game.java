package com.alcachofra.xinada;

import com.alcachofra.xinada.utils.Roles;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Game {
    public static int ROUNDS;
    public static int PLAYERS;
    public static int round;
    public static Collection<String> names;
    public static Map<String, Integer> points = new HashMap<>();
    public static Map<String, Roles.Role> roles;

    /**
     * Set player names.
     * @param playerNames Collection of player names.
     *                    This collection's size should
     *                    be the same as the number of players.
     */
    public static void setNames(Collection<String> playerNames) {
        names = playerNames;
        for (String name : names) {
            points.put(name, 0);
        }
    }

    /**
     * Get role of specified player.
     * @param name Specified player.
     */
    public static Roles.Role getRole(String name) {
        return roles.get(name);
    }

    /**
     * Add 1 point to specified player.
     * @param name Specified player.
     */
    public static void addPoint(String name) {
        points.put(name, points.get(name) + 1);
    }

    /**
     * Draws roles.
     */
    public static void draw() {
        roles = Roles.draw(points.keySet());
    }

    /**
     * Zero all players points.
     */
    public static void zero() {
        for (String name : points.keySet()) {
            points.put(name, 0);
        }
    }
}
