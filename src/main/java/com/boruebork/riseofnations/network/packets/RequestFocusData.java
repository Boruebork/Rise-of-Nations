package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RequestFocusData(String playerName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestFocusData> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "request_focus_data"));

    public static final StreamCodec<ByteBuf, RequestFocusData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            RequestFocusData::playerName,
            RequestFocusData::new//the constructor call for the record
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
