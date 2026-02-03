package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.team.TeamData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record NoTeamDataPacket(List<TeamData> teams) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NoTeamDataPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "no_team_screen_data"));

    public static final StreamCodec<FriendlyByteBuf, NoTeamDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, TeamData.STREAM_CODEC),
            NoTeamDataPacket::teams,
            NoTeamDataPacket::new//the constructor call for the record
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
