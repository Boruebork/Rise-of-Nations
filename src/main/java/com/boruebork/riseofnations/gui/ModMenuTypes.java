package com.boruebork.riseofnations.gui;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.screen.inv.SharedInvMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, RiseofNations.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SharedInvMenu>> SHARED_INV_MENU = MENU_TYPES.register("shared_ive_menu",
            () -> new MenuType<>(SharedInvMenu::new, FeatureFlagSet.of()));
    public static void register(IEventBus e){
        MENU_TYPES.register(e);
    }
}
