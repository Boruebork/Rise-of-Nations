package com.boruebork.riseofnations.gui.buttons;

import com.boruebork.riseofnations.gui.screen.TeamScreen;
import com.boruebork.riseofnations.gui.utik.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class TeamList extends ObjectSelectionList<TeamList.TeamEntry> {
    public TeamScreen parent;
    public TeamList(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    @Override
    public int getRowWidth() {
        return 150;
    }
    public void addTeam(Component text, int id){
        this.addEntry(new TeamEntry(text, id));
    }

    public void setParent(TeamScreen parent) {
        this.parent = parent;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bool) {
        if (bool){
            TeamList.TeamEntry entry = this.getSelected();
            assert entry != null;
            Component name = entry.text;
            parent.onTeamSelected(name);
        }
        return super.mouseClicked(event, bool);
    }

    public static class TeamEntry extends ObjectSelectionList.Entry<TeamEntry>{
        //private final Button button;
        private final Component text;
        private final int id;

        /*public TeamEntry(Component text, Button.OnPress onPress) {
            this.button = Button.builder(text, onPress)
                    .size(200, 20)
                    .build();
            this.button.setWidth(150);
            this.button.setHeight(20);
        }
        public TeamEntry(Button button){
            this.button = button;
            this.button.setWidth(150);
            this.button.setHeight(20);
        }*/
        public TeamEntry(Component text, int id){
            this.text = text;
            this.id = id;
        }


        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float pT) {
            //button.setPosition(x, y);
            int x = 0;
            int y = 20*(this.id + 1);
            guiGraphics.fill(x+1, y+1, x+150-1, y+20-1, Colors.LIGHT_GRAY);
            guiGraphics.drawString(Minecraft.getInstance().font,this.text,x+20,y+5, Colors.WHITE);
        }

        @Override
        public Component getNarration() {
            return this.text;
        }
    }
}
