package com.boruebork.riseofnations.team;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.*;

public class TeamEntry {

    // ===== Team identity =====
    public String name;
    public String leaderName;
    public int color;

    // ===== Team state =====
    public List<String> members;
    public Set<Integer> completedFocuses;
    public int currentFocus;
    public int timeTillEndOfCurrentFocus;

    public TeamEntry(
            String name,
            String leaderName,
            int color,
            List<String> members,
            Set<Integer> completedFocuses,
            int currentFocus,
            int timeTillEndOfCurrentFocus
    ) {
        this.name = name;
        this.leaderName = leaderName;
        this.color = color;
        this.members = members;
        this.completedFocuses = completedFocuses;
        this.currentFocus = currentFocus;
        this.timeTillEndOfCurrentFocus = timeTillEndOfCurrentFocus;
    }

    // ===== Codec =====
    public static final Codec<TeamEntry> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("name").forGetter(t -> t.name),
                    Codec.STRING.fieldOf("leader").forGetter(t -> t.leaderName),
                    Codec.INT.fieldOf("color").forGetter(t -> t.color),

                    Codec.list(Codec.STRING)
                            .optionalFieldOf("members", List.of())
                            .forGetter(t -> t.members),

                    // ✅ Set<Integer> via List<Integer>
                    Codec.list(Codec.INT)
                            .xmap(HashSet::new, List::copyOf)
                            .optionalFieldOf("completed_focuses", new HashSet<>())
                            .forGetter(t -> (HashSet<Integer>) t.completedFocuses),

                    Codec.INT
                            .optionalFieldOf("current_focus", -1)
                            .forGetter(t -> t.currentFocus),

                    Codec.INT
                            .optionalFieldOf("focus_time", 0)
                            .forGetter(t -> t.timeTillEndOfCurrentFocus)
            ).apply(instance, TeamEntry::new));


    public String name() {
        return name;
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

    public int timeTillEndOfCurrentFocus() {
        return timeTillEndOfCurrentFocus;
    }

    //==========Stream Codec===================
    public static final StreamCodec<FriendlyByteBuf, TeamEntry> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TeamEntry::name,
            ByteBufCodecs.STRING_UTF8, TeamEntry::leaderName,
            ByteBufCodecs.VAR_INT, TeamEntry::color,
            ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8), TeamEntry::members,
            ByteBufCodecs.collection(HashSet::new, ByteBufCodecs.VAR_INT), TeamEntry::completedFocuses,
            ByteBufCodecs.VAR_INT, TeamEntry::currentFocus,
            ByteBufCodecs.VAR_INT, TeamEntry::timeTillEndOfCurrentFocus,
            TeamEntry::new
    );

    // 2. Create the Map codec (Map<String, TeamEntry>)
    public static final StreamCodec<FriendlyByteBuf, Map<String, TeamEntry>> MAP_CODEC =
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, TeamEntry.STREAM_CODEC);
}
