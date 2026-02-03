package com.boruebork.riseofnations.focus;

import com.boruebork.riseofnations.network.util.RONStreamCodecs;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Set;

public class FocusData {
    public int x;
    public int y;
    public int timeInTicks;
    public int id;
    private String texture_id;
    public String name;
    public String description;
    public Set<Integer> focusParents;

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int timeInTicks() {
        return timeInTicks;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Set<Integer> focusParents() {
        return focusParents;
    }

    public FocusData(String texture_id, int id, int x, int y, int timeInTicks, String name, String description, Set<Integer> focusParents) {
        this.texture_id = texture_id;
        this.x = x;
        this.y = y;
        this.timeInTicks = timeInTicks;
        this.name = name;
        this.description = description;
        this.focusParents = focusParents;
    }

    public String texture_id() {
        return texture_id;
    }

    public static final StreamCodec<ByteBuf, FocusData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            FocusData::texture_id,
            ByteBufCodecs.INT,
            FocusData::id,
            ByteBufCodecs.INT,
            FocusData::x,
            ByteBufCodecs.INT,
            FocusData::y,
            ByteBufCodecs.INT,
            FocusData::timeInTicks,
            ByteBufCodecs.STRING_UTF8,
            FocusData::name,
            ByteBufCodecs.STRING_UTF8,
            FocusData::description,
            RONStreamCodecs.INT_SET_CODEC,
            FocusData::focusParents,
            FocusData::new
    );
}
