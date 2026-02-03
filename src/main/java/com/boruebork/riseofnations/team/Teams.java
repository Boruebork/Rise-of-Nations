package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.RiseofNations;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class Teams extends SavedData {
    public List<TeamData> NEW_TEAMS = new ArrayList<>();

    public Teams() {
        this.NEW_TEAMS = new ArrayList<>();
    }



    public void unMute(){
        this.NEW_TEAMS = new ArrayList<>(this.NEW_TEAMS);
    }

    public static final Codec<Teams> NEW_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.list(
                            TeamData.CODEC
                    ).fieldOf("teams")
                    .forGetter(t -> t.NEW_TEAMS)
            ).apply(instance, Teams::new));


    public static final SavedDataType<Teams> NEW_ID =
            new SavedDataType<>("teams", Teams::new, NEW_CODEC);
    public Teams(List<TeamData> objects) {
        this.NEW_TEAMS = objects;
    }

    // WORKS BASED ON THE NEW TEAMS (NULL-PROOF)
    public static void playerJoinTeam(int teamID, String playerName){
        if (playerName == null){
            return;
        }
        RiseofNations.teams.NEW_TEAMS.get(teamID).members.add(playerName);
    }
    public static void playerJoinTeam(String teamName, String playerName){
        if (teamName == null || playerName == null){
            return;
        }
        for (TeamData t : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(t.teamName, teamName)){
                t.members.add(playerName);
                return;
            }
        }
    }
    public static void changeLeader(String teamName, String newLeaderName){
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(data.teamName, teamName)){
                System.err.println("We don't have the cards right now!");
            }
        }
    }
    public static void disbandTeam(String teamName){
        if (teamName == null){
            return;
        }
        int len = RiseofNations.teams.NEW_TEAMS.size();
        for (int i = 0; i < len; ++i){
            if (Objects.equals(RiseofNations.teams.NEW_TEAMS.get(i).teamName, teamName)){
                RiseofNations.teams.NEW_TEAMS.remove(i);
                return;
            }
        }
    }
    public static void kickPlayerFromTeam(String playerName, String teamName){
        if (!Teams.isPlayerOnTeam(playerName, teamName)){
            return;
        }
        int id = getTeamId(teamName);
        if (id == -1){
            return;
        }
        RiseofNations.teams.NEW_TEAMS.get(id).members.remove(playerName);
    }
    public static int getTeamId(String name){
        List<TeamData> data = RiseofNations.teams.NEW_TEAMS;
        for (int i = 0; i < data.size(); ++i){
            if (Objects.equals(data.get(i).teamName, name)){
                return i;
            }
        }
        return -1;
    }
    public static TeamData getTeam(String name){
        if (name == null){
            return null;
        }
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(data.teamName, name)){
                return data;
            }
        }
        return null;
    }
    public static boolean isPlayerOnTeam(String player, String team){
        if (player == null || team == null){
            return false;
        }
        return team.equals(getPlayerTeamAsString(player));
    }
    public static String leaderOfTeamStr(Player player){
        if (player == null) return null;
        String name = player.getName().getString();
        return leaderOfTeamStr(name);
    }
    public static String leaderOfTeamStr(String player){
        if (player == null) return null;
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(data.leaderName, player)) return data.teamName;
        }
        return null;
    }
    public static TeamData leaderOfTeamObj(Player player){
        if (player == null) return null;
        String name = player.getName().getString();
        return leaderOfTeamObj(name);
    }
    public static TeamData leaderOfTeamObj(String player){
        if (player == null) return null;
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(data.leaderName, player)) return data;
        }
        return null;
    }
    public static boolean isLeader(Player player){
        if (player == null) return false;
        String name = player.getName().getString();
        return isLeader(name);

    }
    public static boolean isLeader(String player){
        if (player == null) return false;
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (Objects.equals(data.leaderName, player)) return true;
        }
        return false;
    }
    public static boolean playersOnSameTeam(Player player1, Player player2) {
        if (player1 == null || player2 == null) return false;
        String first = player1.getName().getString();
        String second = player2.getName().getString();
        return playerOnSameTeam(first, second);


    }
    public static boolean playerOnSameTeam(String player1, String player2){
        if (player1 == null || player2 == null) return false;
        return Objects.equals(getPlayerTeamAsString(player1), getPlayerTeamAsString(player2));

    }
    public static TeamData getPlayerTeamAsObject(Player player){
        if (player == null) return null;
        String name = player.getName().getString();
        return getPlayerTeamAsObject(name);
    }
    public static TeamData getPlayerTeamAsObject(String player){
        if (player == null) return null;
        for (TeamData data : RiseofNations.teams.NEW_TEAMS){
            if (data.members.contains(player)){
                return data;
            }
        }
        return null;
    }
    public static String getPlayerTeamAsString(Player player){
        if (player == null) return null;
        return getPlayerTeamAsString(player.getName().getString());
    }
    public static String getPlayerTeamAsString(String player){
        if (player == null) return null;
        for (TeamData data: RiseofNations.teams.NEW_TEAMS){
            if (data.members.contains(player)){
                return data.teamName;
            }
        }
        return null;
    }
    public static int getPlayerTeamAsId(String string) {
        if (string == null) return -1;
        for (int i = 0; i < RiseofNations.teams.NEW_TEAMS.size(); ++i){
            if (RiseofNations.teams.NEW_TEAMS.get(i).members.contains(string)){
                return i;
            }
        }
        return -1;
    }
}