package com.boruebork.riseofnations.focus.events;

import com.boruebork.riseofnations.team.Team;
import net.neoforged.bus.api.Event;

public class FocusFinishedEvent extends Event {
    private Team team;
    private int focusId;
    public FocusFinishedEvent(Team team, int focusId){
        this.team = team;
        this.focusId = focusId;
    }
    public Team getTeam(){
        return this.team;
    }

    public int getFocusId() {
        return focusId;
    }
}
