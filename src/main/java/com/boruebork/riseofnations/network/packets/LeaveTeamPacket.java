package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record LeaveTeamPacket(String playerName, String teamName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LeaveTeamPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "leave_team_request"));

    public static final StreamCodec<ByteBuf, LeaveTeamPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            LeaveTeamPacket::playerName,
            ByteBufCodecs.STRING_UTF8,
            LeaveTeamPacket::teamName,
            LeaveTeamPacket::new//the constructor call for the record
    );
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
