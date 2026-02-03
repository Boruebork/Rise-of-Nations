package com.boruebork.riseofnations.event.crop;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.block.ModBlocks;
import com.boruebork.riseofnations.team.DataAttachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import com.boruebork.riseofnations.team.Teams;
@EventBusSubscriber(modid = RiseofNations.MODID)
public class CropEvents {
    @SubscribeEvent
    public static void onCropGrow(CropGrowEvent.Pre event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        var chunk = serverLevel.getChunk(event.getPos());
        int owner = chunk.getData(DataAttachments.CHUNK_CONTROLLER);

        if (owner != -1) {
            if (serverLevel.random.nextFloat() < 0.4f) {
                BlockState currentState = event.getState();
                    if (currentState.getBlock() instanceof CropBlock crop) {
                    int currentAge = crop.getAge(event.getState());
                    int maxAge = crop.getMaxAge();

                    if (currentAge < maxAge) {
                        int nextAge = Math.min(currentAge + 2, maxAge);
                        event.setResult(CropGrowEvent.Pre.Result.DO_NOT_GROW);
                        serverLevel.setBlock(event.getPos(), crop.getStateForAge(nextAge), 3);
                        serverLevel.levelEvent(2005, event.getPos(), 0);

                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Player player && event.getState().is(ModBlocks.CLAIM_BLOCK) &&event.getLevel().getChunk(event.getPos()).getData(DataAttachments.CHUNK_CONTROLLER) == -1){
            int id = Teams.getPlayerTeamAsId(player.getName().getString());
            event.getLevel().getChunk(event.getPos()).setData(DataAttachments.CHUNK_CONTROLLER, id);
            //event.getLevel().getChunk(event.getPos()).setUnsaved()
            System.err.println("Chunk got Team! " + id);
        }
    }

}
