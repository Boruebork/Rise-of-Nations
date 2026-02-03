package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.api.ModRegistries;
import com.boruebork.riseofnations.focus.FocusData;
import com.boruebork.riseofnations.focus.events.FocusStartedEvent;
import com.boruebork.riseofnations.network.packets.*;
import com.boruebork.riseofnations.team.TeamData;
import com.boruebork.riseofnations.team.Teams;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerPayloadHandler {
    //On the (server) <- client

    public static void handleTeamDataRequest(RequestTeamData packet, IPayloadContext context){
        boolean flag = true;
        String name = packet.playerName();
        ServerPlayer sender = (ServerPlayer) context.player();
        for (int i = 0; i < RiseofNations.teams.NEW_TEAMS.size(); ++i){
            TeamData value = RiseofNations.teams.NEW_TEAMS.get(i);
            if (value.members.contains(packet.playerName())){
                PacketDistributor.sendToPlayer(sender, new TeamDataPacket(RiseofNations.teams.NEW_TEAMS, i));
                flag = false;
            }
        }
        if (flag) {
            System.out.println("No team found?");
            PacketDistributor.sendToPlayer(sender, new NoTeamDataPacket(RiseofNations.teams.NEW_TEAMS));
        }
        System.err.println("Accepted screen's reequest for data");
    }

    public static void createNewTeam(CreateNewTeamData data, IPayloadContext context) {
        //if (Teams.getPlayerTeamAsString(data.creatorsName()) == null) return;
        String teamName = data.newTeamName();
        for (TeamData team : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(team.teamName, teamName)){
                return;
            }
        }
        List<String> list = new ArrayList<>();
        list.add(data.creatorsName());
        RiseofNations.teams.NEW_TEAMS.add(new TeamData(data.newTeamName(), data.creatorsName(), list));
        int id = RiseofNations.teams.NEW_TEAMS.size() - 1;
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new TeamDataPacket(RiseofNations.teams.NEW_TEAMS, id));
        System.err.println("Created new Team" + data.newTeamName() + "!");
    }

    public static void requestFocusData(RequestFocusData data, IPayloadContext context) {
        ServerPlayer sender = (ServerPlayer) context.player();
        TeamData playerTeam = Teams.getPlayerTeamAsObject(data.playerName());
        if (playerTeam != null){
            PacketDistributor.sendToPlayer(sender, new SendFocusDataToScreen(playerTeam.completedFocuses, playerTeam.currentFocus, playerTeam.timeTillEndOfFocus));
            System.err.println("Accepted screen's request for focus data");
        }else{
            System.err.println("The player's team is Null somehow🙄");
        }
    }

    public static void startFocusOnServer(StartFocusOnServer data, IPayloadContext context) {
        TeamData team = Teams.getPlayerTeamAsObject(context.player().getName().getString());
        if (team == null) return;
        if (team.currentFocus != -1) return;
        if (!team.completedFocuses.contains(data.id())){
            List<FocusData> focuses = context.player().level().registryAccess().lookupOrThrow(ModRegistries.FOCUS_KEY).stream().toList();
            for (int focus : focuses.get(data.id()).focusParents){
                if (!team.completedFocuses.contains(focus)){
                    return;
                }
            }
            //Can start focus
            team.currentFocus = data.id();
            team.timeTillEndOfFocus = focuses.get(data.id()).timeInTicks;
            System.err.println("Started focus: " + data.id());
            NeoForge.EVENT_BUS.post(new FocusStartedEvent(data.id(), Teams.getTeamId(context.player().getName().getString()), (ServerPlayer) context.player()));
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendFocusStartedToScreen(data.id()));
        }
    }

    public static void playerLeaveTeam(LeaveTeamPacket leaveTeamPacket, IPayloadContext context) {
        System.err.println("playerLeaveTeam");
        Teams.kickPlayerFromTeam(leaveTeamPacket.playerName(), leaveTeamPacket.teamName());
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new NoTeamDataPacket(RiseofNations.teams.NEW_TEAMS));
    }

    public static void playerJoinTeam(JoinTeamPacket joinTeamPacket, IPayloadContext context) {
        String name = context.player().getName().getString();
        Teams.playerJoinTeam(joinTeamPacket.team(), name);

        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new TeamDataPacket(RiseofNations.teams.NEW_TEAMS, joinTeamPacket.team()));
    }

    public static void disbandTeam(DisbandTeamPacket data, IPayloadContext context) {
        if (Objects.equals(RiseofNations.teams.NEW_TEAMS.get(data.id()).leaderName, context.player().getName().getString())){
            RiseofNations.teams.NEW_TEAMS.remove(data.id());
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new NoTeamDataPacket(RiseofNations.teams.NEW_TEAMS));
        }
    }

    public static void changeTeamData(ChangeTeamDataPacket data, IPayloadContext context) {
        TeamData teamData = RiseofNations.teams.NEW_TEAMS.get(data.id());
        teamData.teamName = data.name();
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new TeamDataPacket(RiseofNations.teams.NEW_TEAMS, data.id()));
    }
}