package com.boruebork.riseofnations.gui.buttons;

import com.boruebork.riseofnations.RiseofNations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FocusButton extends Button {
    private int id;
    private int name;
    private final int defaultX;
    private final int defaultY;
    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    private static final int ONGOING_COLOR = 0xFF0000FF;
    private static final int LOCKED_COLOR = 0xFF444444;
    private static final int UNLOCKED_COLOR = 0xFFFFD700;
    private static final int FINISHED_COLOR = 0xFF00FF00;
    private Identifier OVERLAY_TEXTURE;
    private FocusState state;
    public FocusButton(int id, String name, int x, int y,OnPress onPress) {
        super(x, y, WIDTH, HEIGHT, Component.empty(), onPress, DEFAULT_NARRATION);
        this.OVERLAY_TEXTURE = Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/focus/button/" + name + ".png");
        this.id = id;
        this.defaultX = x;
        this.defaultY = y;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //guiGraphics.fill(this.getX(), this.getY(), this.getX() + WIDTH, this.getY() + HEIGHT, Colors.YELLOW);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, OVERLAY_TEXTURE, this.getX(), this.getY(), 0, 0, WIDTH,HEIGHT,64,64);
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

    public int id() {
        return id;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }
}
