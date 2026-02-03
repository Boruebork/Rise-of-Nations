package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.gui.screen.focus.FocusScreen;
import com.boruebork.riseofnations.gui.screen.team.TeamScreen;
import com.boruebork.riseofnations.gui.screen.team.TeamScreenState;
import com.boruebork.riseofnations.network.packets.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class ClientPayloadHandler {
    //server -> (client)

    public static void sendTeamDataToScreen(final TeamDataPacket data, final IPayloadContext context){
        Screen screen = Minecraft.getInstance().screen;
        if (screen != null){
            if (screen instanceof TeamScreen teamScreen){
                teamScreen.teams = data.teams();
                teamScreen.thisTeamData = data.teams().get(data.id());
                teamScreen.teamID = data.id();
                teamScreen.setState(TeamScreenState.MAIN);
                teamScreen.newRebuildWidgets();

                System.err.println("Forwarded data to the screen");
            }
        }

    }

    public static void SendFocusDataToScreen(SendFocusDataToScreen focusDataPacket, IPayloadContext context) {
        if (Minecraft.getInstance().screen instanceof FocusScreen focusScreen){
            focusScreen.finishedFocuses = focusDataPacket.completedFocuses();
            focusScreen.currentFocus = focusDataPacket.currentFocus();
            focusScreen.timeUntilEndOfFocus = focusDataPacket.timeTillEndOfFocus();
            focusScreen.initFocusButtons();
            System.err.println("Sent focus data to screen");
        }
    }

    public static void sendFinishedFocusToScreen(FocusFinishedPacket focusFinishedPacket, IPayloadContext context) {
        if (Minecraft.getInstance().screen instanceof FocusScreen fs){
            fs.finishedFocuses.add(focusFinishedPacket.focusId());
        }
    }

    public static void sendNoTeamDataToScreen(NoTeamDataPacket sendNoTeamDataRoScreen, IPayloadContext context) {
        System.err.println("NoTeamDataPacket");
        if (Minecraft.getInstance().screen instanceof TeamScreen teamScreen){
            teamScreen.teams = sendNoTeamDataRoScreen.teams();
            teamScreen.setState(TeamScreenState.NO_TEAM);
            teamScreen.newRebuildWidgets();
        }
    }

    public static void startFocusOnClient(SendFocusStartedToScreen data, IPayloadContext context) {
        if (Minecraft.getInstance().screen != null){
            if (Minecraft.getInstance().screen instanceof FocusScreen focusScreen){
                focusScreen.beginFocusOnClient(data.focusId());
                System.err.println("Started focus " + data.focusId() + " on the client!7");
            }
        }
    }
}