package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import com.boruebork.riseofnations.team.TeamEntry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Map;

public record SendNoTeamDataRoScreen(Map<String, TeamEntry> teams) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendNoTeamDataRoScreen> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "no_team_screen_data"));

    public static final StreamCodec<FriendlyByteBuf, SendNoTeamDataRoScreen> STREAM_CODEC = StreamCodec.composite(
            TeamEntry.MAP_CODEC,
            SendNoTeamDataRoScreen::teams,
            SendNoTeamDataRoScreen::new//the constructor call for the record
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
