package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RequestTeamData(String playerName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestTeamData> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "request_team_data"));

    public static final StreamCodec<ByteBuf, RequestTeamData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            RequestTeamData::playerName,
            RequestTeamData::new//the constructor call for the record
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
