package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CreateNewTeamData(String newTeamName, String creatorsName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CreateNewTeamData> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "create_new_team_data"));

    public static final StreamCodec<ByteBuf, CreateNewTeamData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,//Data type of the first object
            CreateNewTeamData::newTeamName,//the first object
            ByteBufCodecs.STRING_UTF8,
            CreateNewTeamData::creatorsName,
            CreateNewTeamData::new//the constructor call for the record
    );
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
