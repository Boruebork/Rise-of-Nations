package com.boruebork.riseofnations.team;

import com.boruebork.riseofnations.RiseofNations;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RiseofNations.MODID);

    public static final Supplier<AttachmentType<Integer>> CHUNK_CONTROLLER = ATTACHMENT_TYPES.register(
            "chunk_controller", ()-> AttachmentType.builder(()-> -1).serialize(Codec.INT.fieldOf("chunk_controller")).build()
    );
    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }

}
