package com.boruebork.riseofnations.focus;

import com.boruebork.riseofnations.RiseofNations;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

import static com.boruebork.riseofnations.api.ModRegistries.REGISTRY_FOCUS;

public class ModFocuses {
    public static final DeferredRegister<FocusData> FOCUSES = DeferredRegister.create(REGISTRY_FOCUS, RiseofNations.MODID);

    public static final DeferredHolder<FocusData, FocusData> STARTER_FOCUS = FOCUSES.register("start",
            () -> new FocusData("cooperation", 0, 0, 0, 100, "Cooperation is the key", "This focus will unlock all other focuses", Set.of()));
    public static final DeferredHolder<FocusData, FocusData> FARMING_I = FOCUSES.register("farming_i",
            () -> new FocusData("farming1",1, 200, 200, 100, "Farming I", "Will give you farming buffs", Set.of(0)));



    public static void register(IEventBus eventBus){
        FOCUSES.register(eventBus);
    }
}
