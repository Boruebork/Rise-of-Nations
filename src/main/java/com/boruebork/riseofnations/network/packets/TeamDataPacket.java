package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.team.TeamData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
public record TeamDataPacket(List<TeamData> teams, int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TeamDataPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "screen_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TeamDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, TeamData.STREAM_CODEC),
            TeamDataPacket::teams,
            ByteBufCodecs.INT,
            TeamDataPacket::id,
            TeamDataPacket::new//the constructor call for the record
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
