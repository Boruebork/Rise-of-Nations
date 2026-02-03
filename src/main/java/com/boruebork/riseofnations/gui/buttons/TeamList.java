package com.boruebork.riseofnations.gui.buttons;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.screen.team.TeamScreen;
import com.boruebork.riseofnations.gui.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TeamList extends ObjectSelectionList<TeamList.TeamEntry> {
    public TeamScreen parent;
    public TeamList(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    @Override
    public int getRowWidth() {
        return 150;
    }
    public void addTeam(Component text, int id, int color){
        this.addEntry(new TeamEntry(text, id, color));
    }

    public void setParent(TeamScreen parent) {
        this.parent = parent;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        boolean result = super.mouseClicked(event, doubleClick);
        if (!doubleClick) return result;
        TeamEntry entry = this.getSelected();
        if (entry != null) {
            parent.onTeamSelected(entry.id);
        }
        return result;
    }

    public static class TeamEntry extends ObjectSelectionList.Entry<TeamEntry>{
        private final Component text;
        private final int id;
        private int color;
        private static final Identifier ENTRY = Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/team/team_entry.png");


        public TeamEntry(Component text, int id, int color){
            this.text = text;
            this.id = id;
            this.color =color;
        }


        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float pT) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ENTRY, getContentX() -1 , getContentY() - 1, 0, 0, 148, 18, 148,18);
            guiGraphics.fill(this.getContentX() + 2, this.getContentY() + 2, this.getContentX() + 16, this.getContentY() + 16, color);
            guiGraphics.drawString(Minecraft.getInstance().font,this.text,this.getContentX() + 18, this.getContentY() + 4, Colors.WHITE);
        }


        @Override
        public Component getNarration() {
            return this.text;
        }
    }
}
