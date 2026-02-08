package com.boruebork.riseofnations.gui.screen.inv;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.team.TeamData;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SharedInv extends SimpleContainer {
    public TeamData team;
    public SharedInv(TeamData teamData){
        super(27);
        this.team = teamData;

        for (ItemStackWithSlot entry : team.inventory()) {
            if (entry.isValidInContainer(getContainerSize())) {
                setItem(entry.slot(), entry.stack());
            }
        }
    }
    @Override
    public void setChanged() {
        super.setChanged();
        saveBackToTeam();
    }

    private void saveBackToTeam() {
        List<ItemStackWithSlot> list = new ArrayList<>();

        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                list.add(new ItemStackWithSlot(i, stack.copy()));
            }
        }

        team.setInventory(list);
        RiseofNations.teams.setDirty();
    }

}
