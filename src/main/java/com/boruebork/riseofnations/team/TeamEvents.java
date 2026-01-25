package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.RiseofNations;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Objects;

@EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = RiseofNations.MODID)
public class TeamEvents {
    @SubscribeEvent
    public static void onPlayerJoined(PlayerEvent.PlayerLoggedInEvent event){
        Player player = (Player) (event.getEntity());
        int flag = 0;
        String name = player.getName().toString();
        /*for (Team team : Teams.TEAMS.keySet()){
            for (String player_name : Teams.TEAMS.get(team)){
                if (Objects.equals(player_name, name)){
                    if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                        // Now you can use serverPlayer for networking
                        serverPlayer.sendSystemMessage((Component.literal("Welcome to the server, " + name + "! You are now on the " + team.name + " team!")));
                    }
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 0){
            if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                // Now you can use serverPlayer for networking
                serverPlayer.sendSystemMessage((Component.literal("Welcome to the server, " + name + "You have no team right now!")));

                // Or send custom packets
                // PacketDistributor.sendToPlayer(serverPlayer, yourPacket);
            }
        }*/
    }
}
