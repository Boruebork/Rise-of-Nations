package com.boruebork.riseofnations.team;

import java.util.Objects;

public class Team {
    public String name;
    public String leaderName;
    public int color;
    public Team(String name,String leaderName){
        this.name = name;
        this.leaderName = leaderName;

    }
    public void changeLeader(String newLeaderName){
        this.leaderName = newLeaderName;
    }
    public void changeName(String newName){
        this.name = newName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
