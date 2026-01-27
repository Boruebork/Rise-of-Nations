package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.RiseofNations;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class Teams extends SavedData {
    @Deprecated(since = "now", forRemoval = true)
    public Map<Team, TeamData> TEAMS = new HashMap<>();
    public Map<String, TeamEntry> teamsForDat = new HashMap<>();

    public Teams() {
        this.TEAMS = new HashMap<>();
    }

    public Teams(Map<String, TeamEntry> teams) {
        this.teamsForDat = new HashMap<>(teams);
    }

    public static final Codec<Teams> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.unboundedMap(
                                    Codec.STRING,
                                    TeamEntry.CODEC
                            ).fieldOf("teams")
                            .forGetter(t -> t.teamsForDat)
            ).apply(instance, Teams::new));

    public static final SavedDataType<Teams> ID =
            new SavedDataType<>("teams", Teams::new, CODEC);


    /**A method to convert the extracted teams map to a cached object friendly version*/
    public static Map<Team, TeamData> fromSerializable(Map<String, TeamEntry> data){
        Map<Team, TeamData> out = new HashMap<>();

        for (TeamEntry e : data.values()) {
            Team team = new Team(e.name, e.leaderName);
            TeamData td = new TeamData();

            td.members = new ArrayList<>(e.members);
            td.completedFocuses = new HashSet<>(e.completedFocuses);
            td.currentFocus = e.currentFocus;
            td.timeTillEndOfFocus = e.timeTillEndOfCurrentFocus;

            out.put(team, td);
        }
        return out;
    }
    /**Used to serialize the cached Teams map into a serializable version**/
    public static Map<String, TeamEntry> toSerialized(Map<Team, TeamData> teams){
        Map<String, TeamEntry> out = new HashMap<>();

        for (var entry : teams.entrySet()) {
            Team t = entry.getKey();
            TeamData d = entry.getValue();

            out.put(t.name, new TeamEntry(
                    t.name,
                    t.leaderName,
                    t.color,
                    List.copyOf(d.members),
                    new HashSet<>(d.completedFocuses),
                    d.currentFocus,
                    d.timeTillEndOfFocus
            ));
        }
        return out;
    }
    // WORKS BASED ON THE CACHE (NULL-PROOF)
    public static TeamData getData(Team team){
        if (team == null) return null;
        for (Team team1 : RiseofNations.teams.TEAMS.keySet()){
            if (team == team1){
                return RiseofNations.teams.TEAMS.get(team);
            }
        }
        return null;
    }
    public static void playerJoinTeam(String team, String player){
        if (team == null || player == null){
            return;
        }
        for (Team t : RiseofNations.teams.TEAMS.keySet()){
            if (t.name == team){
                RiseofNations.teams.TEAMS.get(t).members.add(player);
                return;
            }
        }
    }
    public static void changeLeader(String teamName, String newLeaderName){
        //TODO Do smth we need to simplify the key to an id and merge Team and TeamData
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.name, teamName)){

            }
        }
    }
    public static void disbandTeam(String teamName){
        if (teamName == null){
            return;
        }
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.name, teamName)){
                RiseofNations.teams.TEAMS.remove(team);
                return;
            }
        }
    }
    public static void kickPlayerFromTeam(String playerName, String teamName){
        if (!Teams.isPlayerOnTeam(playerName, teamName)){
            return;
        }
        Team team = getTeam(teamName);
        if (team != null){
            if (RiseofNations.teams.TEAMS.get(team) != null){
                RiseofNations.teams.TEAMS.get(team).members.remove(playerName);
            }
        }
    }
    public static Team getTeam(String name){
        if (name == null){
            return null;
        }
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.name, name)){
                return team;
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
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.leaderName, player)) return team.name;
        }
        return null;
    }
    public static Team leaderOfTeamObj(Player player){
        if (player == null) return null;
        String name = player.getName().getString();
        return leaderOfTeamObj(name);
    }
    public static Team leaderOfTeamObj(String player){
        if (player == null) return null;
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.leaderName, player)) return team;
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
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            if (Objects.equals(team.leaderName, player)) return true;
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
    public static Team getPlayerTeamAsObject(Player player){
        if (player == null) return null;
        String name = player.getName().getString();
        return getPlayerTeamAsObject(name);
    }
    public static Team getPlayerTeamAsObject(String player){
        if (player == null) return null;
        for (Team team : RiseofNations.teams.TEAMS.keySet()){
            TeamData data = RiseofNations.teams.TEAMS.get(team);
            if (data.members.contains(player)){
                return team;
            }
        }
        return null;
    }
    public static String getPlayerTeamAsString(Player player){
        if (player == null) return null;
        Team team = getPlayerTeamAsObject(player);
        if (team != null) {
            return team.name;
        }else{
            return null;
        }
    }
    public static String getPlayerTeamAsString(String player){
        if (player == null) return null;
        Team team = getPlayerTeamAsObject(player);
        if (team != null) {
            return team.name;
        }else{
            return null;
        }
    }
}