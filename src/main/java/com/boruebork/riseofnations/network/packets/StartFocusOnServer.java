package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record StartFocusOnServer(int id) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<StartFocusOnServer> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "start_focus_server"));
    public static final StreamCodec<ByteBuf, StartFocusOnServer> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    StartFocusOnServer::id,
                    StartFocusOnServer::new
            );//the constructor call for the record
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
