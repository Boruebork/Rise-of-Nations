package com.boruebork.riseofnations.keys;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyBindings {
    // In some physical client only class

    // Key mapping is lazily initialized so it doesn't exist until it is registered
    public static final Lazy<KeyMapping> T_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.riseofnations.t",
            InputConstants.KEY_U,
            KeyMapping.Category.MISC
    ));

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(T_MAPPING.get());
    }
}
