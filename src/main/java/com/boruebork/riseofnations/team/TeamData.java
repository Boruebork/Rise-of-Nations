package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.gui.screen.inv.SharedInv;
import com.boruebork.riseofnations.gui.util.Colors;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
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
    public List<ItemStackWithSlot> inventory = new ArrayList<>();
    public SimpleContainer container;
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
    /**This constructor is used to create new teams**/
    public TeamData(String teamName, String creatorName, List<String> members) {
        this.teamName = teamName;
        this.leaderName = creatorName;
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>();
        this.currentFocus = -1;
        this.timeTillEndOfFocus = 0;
        this.color = Colors.BLUE;
        inventory = new ArrayList<>();
    }

    public TeamData(List<String> members, Set<@NotNull Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
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
                            .forGetter(t -> new HashSet<>(t.completedFocuses)),
                    Codec.INT.fieldOf("currentFocus").forGetter(t -> t.currentFocus),
                    Codec.INT.fieldOf("timeTillEndOfFocus").forGetter(t ->t.timeTillEndOfFocus),
                    Codec.list(ItemStackWithSlot.CODEC).
                            fieldOf("inv")
                            .forGetter(t -> t.inventory)
                    ).apply(instance, TeamData::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, TeamData> STREAM_CODEC =
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
    //For codec
    public TeamData(String teamName, String leaderName, Integer color, List<String> members, Set<Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus, List<ItemStackWithSlot> inventory) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.color = color;
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
        this.inventory = inventory;
        this.container = new SharedInv(this);
    }
    //For stream Codec
    public TeamData(String teamName, String leaderName, Integer color, List<String> members, Set<Integer> completedFocuses, Integer currentFocus, Integer timeTillEndOfFocus) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.color = color;
        this.members = new ArrayList<>(members);
        this.completedFocuses = new HashSet<>(completedFocuses);
        this.currentFocus = currentFocus;
        this.timeTillEndOfFocus = timeTillEndOfFocus;
        this.container = new SharedInv(this);
        this.inventory = new ArrayList<>();
    }

    public List<ItemStackWithSlot> inventory() {
        return inventory;
    }

    public void setInventory(List<ItemStackWithSlot> inventory) {
        this.inventory = inventory;
    }
}