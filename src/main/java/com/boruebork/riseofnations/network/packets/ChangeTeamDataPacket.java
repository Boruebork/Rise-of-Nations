package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ChangeTeamDataPacket(int id, String name) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ChangeTeamDataPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "change_team_data"));

    public static final StreamCodec<ByteBuf, ChangeTeamDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ChangeTeamDataPacket::id,
            ByteBufCodecs.STRING_UTF8,
            ChangeTeamDataPacket::name,
            ChangeTeamDataPacket::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
