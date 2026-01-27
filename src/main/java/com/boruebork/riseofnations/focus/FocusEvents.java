package com.boruebork.riseofnations.focus;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.api.ModRegistries;
import com.boruebork.riseofnations.focus.events.FocusStartedEvent;
import com.boruebork.riseofnations.network.packets.FocusFinishedPacket;
import com.boruebork.riseofnations.focus.events.FocusFinishedEvent;
import com.boruebork.riseofnations.team.Team;
import com.boruebork.riseofnations.team.Teams;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = RiseofNations.MODID)
public class FocusEvents {
    //@SubscribeEvent
    /*public static void onFocusFinished(FocusFinishedEvent event){
        Team team = event.getTeam();
        RiseofNations.teams.TEAMS.get(team).completedFocuses.add(event.getFocusId());
        for (ServerPlayer player : RiseofNations.teams.TEAMS.get(team).membersAsServerPlayers){
            PacketDistributor.sendToPlayer(player, new FocusFinishedPacket(event.getFocusId()));
            player.sendSystemMessage(Component.literal("Finished focus " + event.getFocusId()));
        }
    }*/
    @SubscribeEvent
    @Deprecated(forRemoval = true)
    public static void onFocusStarted(FocusStartedEvent event){/*
        RiseofNations.teams.TEAMS.get(event.getTeam()).currentFocus = event.getFocusId();
        RiseofNations.teams.TEAMS.get(event.getTeam()).timeTillEndOfFocus = event.getPlayer().level().registryAccess().lookupOrThrow(ModRegistries.FOCUS_KEY).stream().toList().get(event.getFocusId()).timeInTicks;
        System.out.println("Started focus!");*/

    }
}
