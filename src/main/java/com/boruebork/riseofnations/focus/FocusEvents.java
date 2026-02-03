package com.boruebork.riseofnations.focus;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.focus.events.FocusStartedEvent;
import com.boruebork.riseofnations.focus.events.FocusFinishedEvent;
import com.boruebork.riseofnations.team.TeamData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = RiseofNations.MODID)
public class FocusEvents {
    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event){
        for (TeamData team : RiseofNations.teams.NEW_TEAMS){
            if (team.currentFocus == -1) return;
            team.timeTillEndOfFocus--;
            if (team.timeTillEndOfFocus <= 0 && team.currentFocus != 1){
                NeoForge.EVENT_BUS.post(new FocusFinishedEvent(team, team.currentFocus));
                team.timeTillEndOfFocus = 0;
                team.completedFocuses.add(team.currentFocus);
                team.currentFocus = -1;
            }
        }
    }
    @SubscribeEvent
    public static void onFocusFinished(FocusFinishedEvent event){
        System.out.println("FocusFinishedEvent");
    }
    @SubscribeEvent
    public static void onFocusStarted(FocusStartedEvent event){
        System.out.println("FocusStartedEvent");
    }
}
