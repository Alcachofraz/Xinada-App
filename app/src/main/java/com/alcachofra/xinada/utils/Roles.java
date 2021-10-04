package com.alcachofra.xinada.utils;

import android.text.style.ForegroundColorSpan;

import androidx.annotation.StringRes;

import com.alcachofra.xinada.R;
import com.alcachofra.xinada.Xinada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Roles {
    
    public enum Role {
        COP(R.string.cop, R.string.cop_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLUE))),
        MURDERER(R.string.murderer, R.string.murderer_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.RED))),
        DOCTOR(R.string.doctor, R.string.doctor_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        PHANTOM(R.string.phantom, R.string.phantom_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        GURU(R.string.guru, R.string.guru_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        THIEF(R.string.thief, R.string.thief_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        GRAVEDIGGER(R.string.gravedigger, R.string.gravedigger_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        TRAITOR(R.string.traitor, R.string.traitor_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        ACCOMPLICE(R.string.accomplice, R.string.accomplice_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        DEVIL(R.string.devil, R.string.devil_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        PROMOTER(R.string.promoter, R.string.promoter_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        NEGOTIATOR(R.string.negotiator, R.string.negotiator_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        JESTER(R.string.jester, R.string.jester_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK))),
        INNOCENT(R.string.innocent, R.string.innocent_description, new ForegroundColorSpan(Xinada.getInstance().getResources().getColor(R.color.BLACK)));

        public final @StringRes int name;
        public final @StringRes int description;
        public final ForegroundColorSpan colorSpan;
        private boolean checked = true;

        Role(@StringRes int name, @StringRes int description, ForegroundColorSpan colorSpan) {
            this.name = name;
            this.description = description;
            this.colorSpan = colorSpan;
        }

        public String getName() {
            return Xinada.string(name);
        }

        public String getDescription() {
            return Xinada.string(description);
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String toString() {
            return getName();
        }
    }

    /**
     * Draw Roles. Almost every Role has the same chance of being drawn. But some don't. There are some restrictions.
     * <p>
     * There's an algorithm to it:
     * <p>
     * <b>Innocent:</b> The Innocent is most probable Role (1/4)
     *
     * @param players Collection of Players to participate.
     * @return Map of Players and their Roles for the current round.
     **/
    public static Map<String, Role> draw(Collection<String> players) {

        List<Role> pool = new ArrayList<>(getCheckedRoles());

        List<Role> roleDraws = new ArrayList<>(); // List of Drawn Roles for this round.
        Map<String, Role> roles = new HashMap<>(); // Map of Players and their Roles for this round.

        final int ROLES_NUM = pool.size();

        // Add Cop and Murderer (required):
        roleDraws.add(Role.COP);
        roleDraws.add(Role.MURDERER);

        // Add Innocents to pool (chance of Innocent is 1/4):
        for (int i = 0; i < (ROLES_NUM / 4) - 1; i++) {
            pool.add(Role.INNOCENT);
        }

        // Shuffle pool:
        Collections.shuffle(pool);

        /*
        List "pool" now holds ROLES_NUM/4 Innocents and 1 of each other Role (except for Murderer and Cop).
        List "roleDraws" now holds Murderer and Cop, because they are required Roles for the game.
        */

        // Draw Roles from pool to roleDraws:
        for (int i = 0; roleDraws.size() < players.size(); i++) { // While size of roleDraws is less than number of Players
            Role role = pool.get(i); // Role drawn

            switch (role) {
                case COP:
                case MURDERER:
                    break;
                default:
                    roleDraws.add(role);
                    break;
            }
        }

        // Shuffle list of Roles
        Collections.shuffle(roleDraws);

        // Create Roles for players:
        Iterator<Role> iterator = roleDraws.iterator();
        for (String name : players) {
            roles.put(name, iterator.next());
        }

        return roles;
    }

    public static Collection<Role> getCheckedRoles() {
        List<Role> ret = new ArrayList<>();
        for (Role role : EnumSet.allOf(Role.class)) {
            if (role.isChecked()) ret.add(role);
        }
        return ret;
    }
}