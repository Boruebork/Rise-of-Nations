package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.focus.events.FocusFinishedEvent;
import com.boruebork.riseofnations.network.packets.FocusFinishedPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class FocusManager {
    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Pre event){
        //TODO: Sort this focus managing, currently not a focus
        /*for (Team team : RiseofNations.teams.TEAMS.keySet()){
            TeamData teamData = RiseofNations.teams.TEAMS.get(team);
            if (teamData.currentFocus >= 0){
                //System.out.println("Team has a focus");
                teamData.timeTillEndOfFocus--;
                System.out.println("time left " + teamData.timeTillEndOfFocus);
                System.out.println(teamData.timeTillEndOfFocus);
                if (teamData.timeTillEndOfFocus <= 0){
                    teamData.timeTillEndOfFocus = 0;
                    NeoForge.EVENT_BUS.post(new FocusFinishedEvent(team, teamData.currentFocus));
                    //TODO PLS DESTROY THIS we shouldn't use the ServerPlayer array
                    System.out.println("A team has finished a focus!");
                    for (ServerPlayer player : RiseofNations.teams.TEAMS.get(team).membersAsServerPlayers){
                        PacketDistributor.sendToPlayer(player, new FocusFinishedPacket(teamData.currentFocus));
                        player.sendSystemMessage(Component.literal("Finished focus " + teamData.currentFocus));
                    }
                    teamData.currentFocus = -1;
                }
            }

        }*/
    }
}
