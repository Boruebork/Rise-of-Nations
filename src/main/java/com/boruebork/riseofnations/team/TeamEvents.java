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

    }
}
