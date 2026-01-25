package com.boruebork.riseofnations.focus;

import java.util.Set;

public class FocusData {
    public int x;
    public int y;
    public int timeInTicks;
    public String id;
    public String name;
    public String description;
    public Set<Integer> focusParents;

    public FocusData(String id, int x, int y, int timeInTicks, String name, String description, Set<Integer> focusParents) {
        this.x = x;
        this.y = y;
        this.timeInTicks = timeInTicks;
        this.name = name;
        this.description = description;
        this.focusParents = focusParents;
    }
}
