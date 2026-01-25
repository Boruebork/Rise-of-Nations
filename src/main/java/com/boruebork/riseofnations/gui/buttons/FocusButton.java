package com.boruebork.riseofnations.gui.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FocusButton extends Button {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int ONGOING_COLOR = 0xFF0000FF;
    private static final int LOCKED_COLOR = 0xFF444444;
    private static final int UNLOCKED_COLOR = 0xFFFFD700;
    private static final int FINISHED_COLOR = 0xFF00FF00;
    private Identifier OVERLAY_TEXTURE;
    private FocusState state;
    public FocusButton(int x, int y, Identifier overlayTexture, OnPress onPress) {
        super(x, y, WIDTH, HEIGHT, Component.empty(), onPress, DEFAULT_NARRATION);
        this.OVERLAY_TEXTURE = overlayTexture;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.fill(this.getX(), this.getY(), WIDTH, HEIGHT, this.getColor());
        guiGraphics.blit(this.OVERLAY_TEXTURE, this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT,  WIDTH, HEIGHT);
    }

    public int getColor() {
        if (this.state == FocusState.LOCKED){
            return LOCKED_COLOR;
        } else if (this.state == FocusState.UNLOCKED) {
            return UNLOCKED_COLOR;
        } else if (this.state == FocusState.RUNNING) {
            return  ONGOING_COLOR;
        }else{
            return FINISHED_COLOR;
        }
    }
    public void setState(FocusState state){
        this.state = state;
    }
}
