package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Set;

public record SendFocusDataToScreen(Set<Integer> completedFocuses, int currentFocus, int timeTillEndOfFocus) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendFocusDataToScreen> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "focus_data_to_screen"));

    public static final StreamCodec<FriendlyByteBuf, SendFocusDataToScreen> STREAM_CODEC = StreamCodec.composite(
            RONStreamCodecs.INT_SET_CODEC,
            SendFocusDataToScreen::completedFocuses,
            ByteBufCodecs.INT,
            SendFocusDataToScreen::currentFocus,
            ByteBufCodecs.INT,
            SendFocusDataToScreen::timeTillEndOfFocus,
            SendFocusDataToScreen::new//the constructor call for the record
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
