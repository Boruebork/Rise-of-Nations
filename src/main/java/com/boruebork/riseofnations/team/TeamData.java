package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.focus.FocusData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamData {
    public List<String> members;
    public Set<Integer> completedFocuses;
    public int currentFocus;
    public int timeTillEndOfFocus;
    public TeamData(List<String> members, Set<Integer> completedFocuses){
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
    }
    public TeamData(String creatorName){
        this.members = new ArrayList<>();
        this.members.add(creatorName);
        this.completedFocuses = new HashSet<>();
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
    }

    public TeamData(List<String> members, List<ServerPlayer> membersAsServerPlayers, Set<Integer> completedFocuses, int currentFocus, int timeTillEndOfFocus) {
    }

    public TeamData(List<String> members, Set<@NotNull Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
    }

    public TeamData() {

    }
}
