package com.boruebork.riseofnations.network.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.Utf8String;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.*;

public class RONStreamCodecs {
    public static final StreamCodec<FriendlyByteBuf, List<String>> STRING_LIST_CODEC =
            new StreamCodec<>() {

                @Override
                public List<String> decode(FriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<String> list = new ArrayList<>(size);

                    for (int i = 0; i < size; i++) {
                        list.add(buf.readUtf());
                    }

                    return list;
                }

                @Override
                public void encode(FriendlyByteBuf buf, List<String> list) {
                    buf.writeVarInt(list.size());

                    for (String s : list) {
                        buf.writeUtf(s);
                    }
                }
            };
    public static final StreamCodec<FriendlyByteBuf, Set<String>> STRING_SET_CODEC =
            new StreamCodec<FriendlyByteBuf, Set<String>>() {
                @Override
                public Set<String> decode(FriendlyByteBuf buf) {
                    int size = buf.readInt();
                    Set<String> set = new HashSet<>();

                    for (int i = 0; i < size; i++) {
                        set.add(buf.readUtf());
                    }

                    return set;
                }



                @Override
                public void encode(FriendlyByteBuf buf, Set<String> set) {
                    buf.writeInt(set.size());
                    for (String s : set) {
                        ByteBufUtil.writeUtf8(buf, s);
                    }
                }

            };
    public static final StreamCodec<ByteBuf, Set<Integer>> INT_SET_CODEC =
            ByteBufCodecs.collection(
                    HashSet::new,      // how to create the Set
                    ByteBufCodecs.INT  // how to encode each element
            );
    public static final StreamCodec<ByteBuf, Map<String, String>> STRING_MAP =
            ByteBufCodecs.map(
                    HashMap::new,             // Factory to create the map
                    ByteBufCodecs.STRING_UTF8, // Key codec
                    ByteBufCodecs.STRING_UTF8  // Value codec
            );
}

