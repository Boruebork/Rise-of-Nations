package com.boruebork.riseofnations.focus.events;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;

public class FocusStartedEvent extends Event {
    private int focusId;
    private int teamId;
    private ServerPlayer player;
    public FocusStartedEvent(int focusId, int teamId, ServerPlayer player){
        this.focusId = focusId;
        this.teamId = teamId;
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getFocusId() {
        return focusId;
    }
}
