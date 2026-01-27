package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.focus.FocusData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamData {
    public String teamName;
    public String leaderName;
    public int color;
    public List<String> members;
    public Set<Integer> completedFocuses;
    public int currentFocus;
    public int timeTillEndOfFocus;
    public TeamData(List<String> members, Set<Integer> completedFocuses){
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
    }
    public TeamData(String creatorName){
        this.members = new ArrayList<>();
        this.members.add(creatorName);
        this.completedFocuses = new HashSet<>();
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
    }

    public TeamData(List<String> members, List<ServerPlayer> membersAsServerPlayers, Set<Integer> completedFocuses, int currentFocus, int timeTillEndOfFocus) {
    }

    public TeamData(List<String> members, Set<@NotNull Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
    }

    public TeamData() {

    }
    public static final Codec<TeamData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("teamName").forGetter(t -> t.teamName),
                    Codec.STRING.fieldOf("leaderName").forGetter(t -> t.leaderName),
                    Codec.INT.fieldOf("color").forGetter(t ->t.color),
                    Codec.list(
                            Codec.STRING
                    ).fieldOf("members").forGetter(t ->t.members),
                    Codec.list(Codec.INT)
                            .xmap(HashSet::new, List::copyOf)
                            .optionalFieldOf("completed_focuses", new HashSet<>())
                            .forGetter(t -> (HashSet<Integer>) t.completedFocuses),
                    Codec.INT.fieldOf("currentFocus").forGetter(t -> t.currentFocus),
                    Codec.INT.fieldOf("timeTillEndOfFocus").forGetter(t ->t.timeTillEndOfFocus)
                    ).apply(instance, TeamData::new)
            );

    public TeamData(String teamName, String leaderName, Integer color, List<String> members, HashSet<Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.color = color;
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
    }
}
