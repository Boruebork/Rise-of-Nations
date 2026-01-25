package com.boruebork.riseofnations.gui.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TextureButton extends Button {
    private final Identifier texture;
    private final int textureWidth;
    private final int textureHeight;

    public TextureButton(int x, int y, int width, int height,
                          Identifier texture,
                          OnPress onPress) {
        // Provide an empty label and default narration
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.textureWidth = 200;
        this.textureHeight = 100;
    }
    public TextureButton(int x, int y, int width, int height,
                          Identifier texture, int textureWidth, int textureHeight,
                          OnPress onPress) {
        // Provide an empty label and default narration
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void renderContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Draw the texture (u=0,v=0, region=width,height, sheet size = textureWidth,textureHeight)
        graphics.blit(texture, getX(), getY(), 0, 0, this.width, this.height, textureWidth/2, textureHeight/2);

        // Optional: draw a simple hover overlay (semi-transparent)
        if (isHoveredOrFocused()) {
            fill(graphics, getX(), getY(), getX() + this.width, getY() + this.height, 0x80FFFFFF);
        }
    }

    // helper: draws a coloured rectangle (uses Button.fill static convenience)
    private static void fill(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
        graphics.fill(x1, y1, x2, y2, color);
    }
}
