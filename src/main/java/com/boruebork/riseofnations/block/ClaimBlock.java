package com.boruebork.riseofnations.block;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.screen.inv.SharedInv;
import com.boruebork.riseofnations.gui.screen.inv.SharedInvMenu;
import com.boruebork.riseofnations.team.TeamData;
import com.boruebork.riseofnations.team.Teams;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ClaimBlock extends Block {
    public ClaimBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        TeamData data = Teams.getPlayerTeamAsObject(player);
        if (data == null){
            ((ServerPlayer) player).sendSystemMessage(Component.literal("You are not in a Team! Unable to acces!"));
            return InteractionResult.FAIL;
        }
        player.openMenu(new SimpleMenuProvider((id, inv, player1) ->
           new SharedInvMenu(id, inv, player, new SharedInv(data)),
                Component.literal("Shared Inv")));
        return InteractionResult.SUCCESS;
    }
}
