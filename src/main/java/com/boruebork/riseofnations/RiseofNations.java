package com.boruebork.riseofnations;

import com.boruebork.riseofnations.focus.ModFocuses;
import com.boruebork.riseofnations.team.FocusManager;
import com.boruebork.riseofnations.team.Teams;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
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
    // Create a Deferred Register to hold Blocks which will all be registered under the "riseofnations" namespace
    // Creates a creative tab with the id "riseofnations:example_tab" for the example item, that is placed after the combat tab
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RiseofNations(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        ModFocuses.register(modEventBus);
        // Note that this is necessary if and only if we want *this* class (RiseofNations) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(FocusManager.class);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        teams = event.getServer().overworld().getDataStorage().computeIfAbsent(Teams.NEW_ID);
        if (teams != null){
            teams.TEAMS = Teams.fromSerializable(teams.teamsForDat);
        }
        LOGGER.info("HELLO from server starting");
    }
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event){
        if (teams != null){
            teams.teamsForDat = Teams.toSerialized(teams.TEAMS);
            teams.setDirty();
        }
    }
   /* @SubscribeEvent
    public void onEntityHurt(AttackEntityEvent event){
        Entity entity = event.getTarget();
        if (entity instanceof Player){
            Player target = (Player) entity;
            Player attacker = event.getEntity();
            if (Teams.playersOnSameTeam(target, attacker));
        }
    }*/
}
