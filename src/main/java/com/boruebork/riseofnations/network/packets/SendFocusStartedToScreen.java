package com.boruebork.riseofnations.network.packets;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.List;

public record SendFocusStartedToScreen(int focusId) implements CustomPacketPayload {
    public static final Type<SendFocusStartedToScreen> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "new_focus_to_screen"));

    public static final StreamCodec<FriendlyByteBuf, SendFocusStartedToScreen> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SendFocusStartedToScreen::focusId,
            SendFocusStartedToScreen::new//the constructor call for the record
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
