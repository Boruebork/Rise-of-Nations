package com.boruebork.riseofnations.network;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.api.ModRegistries;
import com.boruebork.riseofnations.focus.events.FocusStartedEvent;
import com.boruebork.riseofnations.network.packets.*;
import com.boruebork.riseofnations.team.Team;
import com.boruebork.riseofnations.team.TeamData;
import com.boruebork.riseofnations.team.Teams;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = RiseofNations.MODID)
public class ServerPayloadHadler {
    //On the (server) <- client

    public static void handleTeamDataRequest(RequestTeamData packet, IPayloadContext context){
        boolean flag = true;
        String name = packet.playerName();
        ServerPlayer sender = (ServerPlayer) context.player();
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            var value = RiseofNations.teams.TEAMS.get(team);
            if (value.members.contains(packet.playerName())){
                PacketDistributor.sendToPlayer(sender, new SendTeamDataToScreen(team.name, team.leaderName, RiseofNations.teams.TEAMS.get(team).members));
                flag = false;
            }
        }
        if (flag) {
            System.out.println("No team found?");
            PacketDistributor.sendToPlayer(sender, new SendNoTeamDataRoScreen(RiseofNations.teams.teamsForDat));
        }
        System.err.println("Accepted screen's reequest for data");
    }

    public static void createNewTeam(CreateNewTeamData data, IPayloadContext context) {
        String teamName = data.newTeamName();
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.leaderName, teamName)){
                return;
            }
        }
        List<String> list = new ArrayList<>();
        List<ServerPlayer> serverPlayerList = new ArrayList<>();
        serverPlayerList.add((ServerPlayer) context.player());
        list.add(data.creatorsName());
        list.add("Is it really you?");
        RiseofNations.teams.TEAMS.put(new Team(data.newTeamName(), data.creatorsName()), new TeamData(list, new HashSet<>()));
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendTeamDataToScreen(data.newTeamName(), data.creatorsName(),list));
        System.err.println("Created new Team" + data.newTeamName() + "!");
    }

    public static void requestFocusData(RequestFocusData data, IPayloadContext context) {
        ServerPlayer sender = (ServerPlayer) context.player();
        Team playerteam = Teams.getPlayerTeamAsObject(data.playerName());
        if (playerteam != null){
            PacketDistributor.sendToPlayer(sender, new SendFocusDataToScreen(RiseofNations.teams.TEAMS.get(playerteam).completedFocuses));
            System.err.println("Accepted screen's request for focus data");
        }else{
            System.err.println("The player's team is Null somehow🙄");
        }
    }

    public static void startFocusOnServer(StartFocusOnServer startFocusOnServer, IPayloadContext context) {
        Team team = Teams.getPlayerTeamAsObject(context.player().getName().getString());
        if (team != null){
            System.err.println("Started Foucs on the server!");
            System.out.println("Team is not null");
            RiseofNations.teams.TEAMS.get(team).currentFocus = startFocusOnServer.id();
            RiseofNations.teams.TEAMS.get(team).timeTillEndOfFocus = context.player().level().registryAccess().lookupOrThrow(ModRegistries.FOCUS_KEY).stream().toList().get(startFocusOnServer.id()).timeInTicks;
            NeoForge.EVENT_BUS.post(new FocusStartedEvent(startFocusOnServer.id(), team, (ServerPlayer) context.player()));
            System.out.println("Started Focus");

        }else System.out.println("Team is null💀");
    }

    public static void playerLeaveTeam(LeaveTeamPacket leaveTeamPacket, IPayloadContext context) {
        System.err.println("playerLeaveTeam");
        Teams.kickPlayerFromTeam(leaveTeamPacket.playerName(), leaveTeamPacket.teamName());
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendNoTeamDataRoScreen(RiseofNations.teams.teamsForDat));
    }

    public static void playerJoinTeam(JoinTeamPacket joinTeamPacket, IPayloadContext context) {
        String name = context.player().getName().getString();
        Team team = Teams.getTeam(joinTeamPacket.team());
        TeamData data = Teams.getData(team);
        Teams.playerJoinTeam(joinTeamPacket.team(), name);
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendTeamDataToScreen(team.name, team.leaderName, data.members));
    }
}