package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record DisbandTeamPacket(int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DisbandTeamPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "disband_team"));

    public static final StreamCodec<FriendlyByteBuf, DisbandTeamPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            DisbandTeamPacket::id,
            DisbandTeamPacket::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
