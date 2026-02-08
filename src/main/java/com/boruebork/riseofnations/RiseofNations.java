package com.boruebork.riseofnations;

import com.boruebork.riseofnations.block.ModBlocks;
import com.boruebork.riseofnations.focus.ModFocuses;
import com.boruebork.riseofnations.gui.ModMenuTypes;
import com.boruebork.riseofnations.item.ModItems;
import com.boruebork.riseofnations.team.DataAttachments;
import com.boruebork.riseofnations.team.Teams;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RiseofNations.MODID)
public class RiseofNations {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "riseofnations";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Teams teams = null;
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RiseofNations(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        ModFocuses.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        DataAttachments.register(modEventBus);
        // Note that this is necessary if and only if we want *this* class (RiseofNations) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModBlocks.CLAIM_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        teams = event.getServer().overworld().getDataStorage().computeIfAbsent(Teams.NEW_ID);
        teams.unMute();
        LOGGER.info("HELLO from server starting");
    }
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event){
        if (teams != null){
            teams.setDirty();
        }
    }
}
