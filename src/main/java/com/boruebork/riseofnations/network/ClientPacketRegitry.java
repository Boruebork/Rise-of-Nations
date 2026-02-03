package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.packets.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

@EventBusSubscriber(modid = RiseofNations.MODID, value = Dist.CLIENT)
public class ClientPacketRegitry {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void register(RegisterClientPayloadHandlersEvent event) {
        event.register(
                TeamDataPacket.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::sendTeamDataToScreen
        );
        event.register(
                SendFocusDataToScreen.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::SendFocusDataToScreen
        );
        event.register(
                FocusFinishedPacket.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::sendFinishedFocusToScreen
        );
        event.register(
                NoTeamDataPacket.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::sendNoTeamDataToScreen
        );
        event.register(
                SendFocusStartedToScreen.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::startFocusOnClient
        );
    }
}
