package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.packets.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
@EventBusSubscriber(modid = RiseofNations.MODID)
public class PacketRegistry {
    private static final String PROTOCOL_VERSION = "1";
    @SubscribeEvent // on the mod event bus
    public static void register(RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.executesOn(HandlerThread.NETWORK);
        registrar.playToServer(
                RequestTeamData.TYPE,
                RequestTeamData.STREAM_CODEC,
                ServerPayloadHandler::handleTeamDataRequest
        );
        registrar.playToClient(
                SendTeamDataToScreen.TYPE,
                SendTeamDataToScreen.STREAM_CODEC
        );
        registrar.playToServer(
                CreateNewTeamData.TYPE,
                CreateNewTeamData.STREAM_CODEC,
                ServerPayloadHandler::createNewTeam
        );
        registrar.playToServer(
                RequestFocusData.TYPE,
                RequestFocusData.STREAM_CODEC,
                ServerPayloadHandler::requestFocusData
        );
        registrar.playToClient(
                SendFocusDataToScreen.TYPE,
                SendFocusDataToScreen.STREAM_CODEC
        );
        registrar.playToServer(
                StartFocusOnServer.TYPE,
                StartFocusOnServer.STREAM_CODEC,
                ServerPayloadHandler::startFocusOnServer
        );
        registrar.playToClient(
                FocusFinishedPacket.TYPE,
                FocusFinishedPacket.STREAM_CODEC
        );
        registrar.playToClient(
                SendNoTeamDataRoScreen.TYPE,
                SendNoTeamDataRoScreen.STREAM_CODEC
        );
        registrar.playToServer(
                LeaveTeamPacket.TYPE,
                LeaveTeamPacket.STREAM_CODEC,
                ServerPayloadHandler::playerLeaveTeam
        );
        registrar.playToServer(
                JoinTeamPacket.TYPE,
                JoinTeamPacket.STREAM_CODEC,
                ServerPayloadHandler::playerJoinTeam
        );
    }
}
