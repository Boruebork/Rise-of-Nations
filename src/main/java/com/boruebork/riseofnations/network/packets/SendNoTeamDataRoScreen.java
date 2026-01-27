package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import com.boruebork.riseofnations.team.TeamData;
import com.boruebork.riseofnations.team.TeamEntry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record SendNoTeamDataRoScreen(List<TeamData> teams) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendNoTeamDataRoScreen> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "no_team_screen_data"));

    public static final StreamCodec<FriendlyByteBuf, SendNoTeamDataRoScreen> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, TeamData.STREAM_CODEC),
            SendNoTeamDataRoScreen::teams,
            SendNoTeamDataRoScreen::new//the constructor call for the record
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
