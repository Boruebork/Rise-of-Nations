package com.boruebork.riseofnations.team;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CodecUtil {
    public static final Codec<Map<String, List<String>>> STRING_LIST_MAP_CODEC =
            Codec.unboundedMap(
                    Codec.STRING,
                    Codec.list(Codec.STRING)
            );
    Codec<Set<Integer>> INT_SET_CODEC =
            Codec.INT.listOf()
                    .xmap(Set::copyOf, List::copyOf);

    public static final Codec<TeamData> TEAM_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.listOf()
                            .fieldOf("members")
                            .forGetter(data -> data.members),

                    Codec.INT.listOf()
                            .xmap(Set::copyOf, List::copyOf)
                            .fieldOf("completed_focuses")
                            .forGetter(data -> data.completedFocuses),

                    Codec.INT.fieldOf("current_focus")
                            .forGetter(data -> data.currentFocus),

                    Codec.INT.fieldOf("time_left")
                            .forGetter(data -> data.timeTillEndOfFocus)
            ).apply(instance, TeamData::new)
    );


}
