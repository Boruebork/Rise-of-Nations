package com.boruebork.riseofnations.api;

import com.boruebork.riseofnations.focus.FocusData;
import com.boruebork.riseofnations.RiseofNations;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber
public class ModRegistries {
    public static final ResourceKey<Registry<FocusData>> FOCUS_KEY =
            ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(RiseofNations.MODID, "focuses"));
    public static final Registry<FocusData> REGISTRY_FOCUS = new RegistryBuilder<>(FOCUS_KEY).sync(true).create();

    @SubscribeEvent
    public static void newRegistry(NewRegistryEvent event){
        event.register(REGISTRY_FOCUS);
    }
}
