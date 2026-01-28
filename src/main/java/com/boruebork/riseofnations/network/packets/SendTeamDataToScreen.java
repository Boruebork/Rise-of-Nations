package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import com.boruebork.riseofnations.team.TeamData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
//TODO: Send the whole Data rather than selective one
public record SendTeamDataToScreen(List<TeamData> teams, int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendTeamDataToScreen> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "screen_data"));

    public static final StreamCodec<FriendlyByteBuf, SendTeamDataToScreen> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, TeamData.STREAM_CODEC),
            SendTeamDataToScreen::teams,
            ByteBufCodecs.INT,
            SendTeamDataToScreen::id,
            SendTeamDataToScreen::new//the constructor call for the record
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
