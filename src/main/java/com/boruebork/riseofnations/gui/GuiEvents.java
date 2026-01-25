package com.boruebork.riseofnations.gui;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.screen.TeamScreen;
import com.boruebork.riseofnations.keys.KeyBindings;
import com.boruebork.riseofnations.network.packets.RequestTeamData;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(value = Dist.CLIENT, modid = RiseofNations.MODID)
public class GuiEvents {
    @SubscribeEvent
    public static void onTPress(ClientTickEvent.Post event){
        while (KeyBindings.T_MAPPING.get().consumeClick()){
            TeamScreen screen = new TeamScreen();
            Minecraft.getInstance().setScreen(screen);
            ClientPacketDistributor.sendToServer(new RequestTeamData(Minecraft.getInstance().player.getName().getString()));
        }

    }
}
