package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.focus.FocusData;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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
    public List<String> members = new ArrayList<>();
    public Set<Integer> completedFocuses = new HashSet<>();
    public int currentFocus;
    public int timeTillEndOfFocus;

    public String teamName() {
        return teamName;
    }

    public String leaderName() {
        return leaderName;
    }

    public int color() {
        return color;
    }

    public List<String> members() {
        return members;
    }

    public Set<Integer> completedFocuses() {
        return completedFocuses;
    }

    public int currentFocus() {
        return currentFocus;
    }

    public int timeTillEndOfFocus() {
        return timeTillEndOfFocus;
    }

    public TeamData(List<String> members, Set<Integer> completedFocuses){
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
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
    /**This constructor is use to create new teams**/
    public TeamData(String teamName, String creatorName, List<String> members) {
        this.teamName = teamName;
        this.leaderName = creatorName;
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>();
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
    }

    public TeamData(List<String> members, Set<@NotNull Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
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
    public static final StreamCodec<FriendlyByteBuf, TeamData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, TeamData::teamName,
                    ByteBufCodecs.STRING_UTF8, TeamData::leaderName,
                    ByteBufCodecs.INT, TeamData::color,
                    RONStreamCodecs.STRING_LIST_CODEC, TeamData::members,
                    RONStreamCodecs.INT_SET_CODEC, TeamData::completedFocuses,
                    ByteBufCodecs.INT, TeamData::currentFocus,
                    ByteBufCodecs.INT, TeamData::timeTillEndOfFocus,
                    TeamData::new
            );

    public TeamData(String teamName, String leaderName, Integer color, List<String> members, Set<Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.color = color;
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
    }
}
