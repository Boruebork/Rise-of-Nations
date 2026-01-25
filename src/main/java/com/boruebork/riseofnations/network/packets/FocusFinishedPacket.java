package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FocusFinishedPacket(int focusId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FocusFinishedPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "focus_finished"));
    public static final StreamCodec<ByteBuf, FocusFinishedPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    FocusFinishedPacket::focusId,
                    FocusFinishedPacket::new
            );//the constructor call for the record
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
