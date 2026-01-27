package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record JoinTeamPacket(String team) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<JoinTeamPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "leave_team_packet"));
    public static final StreamCodec<ByteBuf, JoinTeamPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            JoinTeamPacket::team,
            JoinTeamPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
