package com.boruebork.riseofnations.focus.events;

import com.boruebork.riseofnations.team.Team;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;

public class FocusStartedEvent extends Event {
    private int focusId;
    private Team team;
    private ServerPlayer player;
    public FocusStartedEvent(int focusId, Team team, ServerPlayer player){
        this.focusId = focusId;
        this.team = team;
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public int getFocusId() {
        return focusId;
    }
}
