package com.boruebork.riseofnations.item;

import com.boruebork.riseofnations.RiseofNations;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RiseofNations.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
