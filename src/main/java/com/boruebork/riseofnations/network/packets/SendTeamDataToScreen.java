package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.List;

public record SendTeamDataToScreen(String teamName, String leaderName, List<String> players) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendTeamDataToScreen> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "screen_data"));

    public static final StreamCodec<FriendlyByteBuf, SendTeamDataToScreen> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SendTeamDataToScreen::teamName,
            ByteBufCodecs.STRING_UTF8,
            SendTeamDataToScreen::leaderName,
            RONStreamCodecs.STRING_LIST_CODEC,
            SendTeamDataToScreen::players,
            SendTeamDataToScreen::new//the constructor call for the record
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
