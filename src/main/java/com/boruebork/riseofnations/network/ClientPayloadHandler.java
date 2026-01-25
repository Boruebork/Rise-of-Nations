package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.gui.screen.FocusScreen;
import com.boruebork.riseofnations.gui.screen.TeamScreen;
import com.boruebork.riseofnations.network.packets.FocusFinishedPacket;
import com.boruebork.riseofnations.network.packets.SendFocusDataToScreen;
import com.boruebork.riseofnations.network.packets.SendNoTeamDataRoScreen;
import com.boruebork.riseofnations.network.packets.SendTeamDataToScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class ClientPayloadHandler {
    //server -> (client)
    private Minecraft MC = Minecraft.getInstance();


    public static void sendTeamDataToScreen(final SendTeamDataToScreen data, final IPayloadContext context){
        Screen screen = Minecraft.getInstance().screen;
        if (screen != null){
            if (screen instanceof TeamScreen teamScreen){
                teamScreen.teamName = data.teamName();
                teamScreen.leaderName = data.leaderName();
                teamScreen.players = data.players();
                teamScreen.rebuildWidgets();

                System.err.println("Forwarded data to the screen");
            }
        }

    }

    public static void SendFocusDataToScreen(SendFocusDataToScreen focusDataPacket, IPayloadContext context) {
        if (Minecraft.getInstance().screen instanceof FocusScreen focusScreen){
            focusScreen.finishedFocuses = focusDataPacket.completedFocuses();
            focusScreen.publicInitFocusButtons();
            System.err.println("Sent focus data to screen");
        }
    }

    public static void sendFinishedFocusToScreen(FocusFinishedPacket focusFinishedPacket, IPayloadContext context) {
        if (Minecraft.getInstance().screen instanceof FocusScreen fs){
            fs.finishedFocuses.add(focusFinishedPacket.focusId());
        }
    }

    public static void sendNoTeamDataToScreen(SendNoTeamDataRoScreen sendNoTeamDataRoScreen, IPayloadContext context) {
        System.err.println("SendNoTeamDataRoScreen");
        if (Minecraft.getInstance().screen instanceof TeamScreen teamScreen){
            teamScreen.teams = sendNoTeamDataRoScreen.teams();
            teamScreen.acceptNoTeamDataFromServer();
        }
    }
}