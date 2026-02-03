package com.boruebork.riseofnations.focus.events;

import com.boruebork.riseofnations.team.TeamData;
import net.neoforged.bus.api.Event;

public class FocusFinishedEvent extends Event {
    private TeamData team;
    private int focusId;
    public FocusFinishedEvent(TeamData team, int focusId){
        this.team = team;
        this.focusId = focusId;
    }
    public TeamData getTeam(){
        return this.team;
    }

    public int getFocusId() {
        return focusId;
    }
}
