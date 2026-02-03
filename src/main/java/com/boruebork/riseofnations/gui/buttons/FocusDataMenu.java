package com.boruebork.riseofnations.gui.buttons;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.screen.focus.FocusScreen;
import com.boruebork.riseofnations.gui.util.Colors;
import com.boruebork.riseofnations.network.packets.StartFocusOnServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class FocusDataMenu extends Button {
    private String name;
    private int id;
    private String description;
    private Identifier icon;
    public FocusScreen parent;
    private static final Identifier MAIN = Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/focus/select.png");
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int buttonHeight = 13;
    private static final int buttonWidth = 51;
    private static final int startX = 145;
    private static final int startY = 181;
    private static final int cancelX = 7;
    private static final int cancelY = 181;
    public FocusDataMenu(FocusScreen parent, int x, int y, String name, String description, String texture_id, int id) {
        super(x, y, WIDTH, HEIGHT, Component.literal(""), FocusDataMenu::onpr, DEFAULT_NARRATION);
        this.name = name;
        this.parent = parent;
        this.description = description;
        this.id = id;
        this.icon = Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/focus/button/" + texture_id +".png");

    }
    private static void onpr(Button button){
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleclick) {
        double mouseX = event.x();
        double mouseY = event.y();
        if (mouseTouchingStart(mouseX, mouseY)){
            //Start
            start();
            System.err.println("Start");
        }else if (mouseTouchingCancel(mouseX, mouseY)){
            cancel();
            System.err.println("Cancel");
        }
        return super.mouseClicked(event, doubleclick);
    }
    private void cancel(){
        this.parent.startFocusMenu = null;
        this.parent.pRemoveWidget(this);
        System.err.println("cancel called");

    }
    public boolean touchingMouse(double mouseX, double mouseY){
        return mouseX >= this.getX() && mouseX <= this.getX() + WIDTH && mouseY >= this.getY() && mouseY <= this.getY() + HEIGHT;
    }
    public boolean mouseTouchingStart(double mouseX, double mouseY){
        return mouseX >= this.getX() + startX && mouseX <= this.getX() + startX + buttonWidth && mouseY >= this.getY() + startY && mouseY <= this.getY() + startY + buttonHeight;
    }
    public boolean mouseTouchingCancel(double mouseX, double mouseY){
        return mouseX >= this.getX() + cancelX && mouseX <= this.getX() + cancelX + buttonWidth && mouseY >= this.getY() + cancelY && mouseY <= this.getY() + cancelY + buttonHeight;
    }
    private void start(){
        ClientPacketDistributor.sendToServer(new StartFocusOnServer(id));
        System.err.println("start called");
        cancel();
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        double startingX = event.x() - dragX;
        double startingY = event.y() - dragY;
        if (touchingMouse(startingX, startingY)){
            this.setPosition((int) (this.getX() + dragX), (int) (this.getY() + dragY));
        }

        return super.mouseDragged(event, dragX, dragY);
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, MAIN, this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        guiGraphics.drawString(Minecraft.getInstance().font, this.name, getX() + 69, getY() + 9, Colors.WHITE);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.icon, getX() + 6, getY() + 6, 0, 0, 53, 53, 53, 53);
        guiGraphics.drawString(Minecraft.getInstance().font, this.description, getX() + 69, getY() + 33, Colors.WHITE);
        if (mouseTouchingStart(mouseX, mouseY)){
            guiGraphics.fill(this.getX() + startX, this.getY() + startY, this.getX() + startX + buttonWidth, this.getY() + startY + buttonHeight, Colors.TRANSPARENT_BLACK_25);
        }
        if (mouseTouchingCancel(mouseX, mouseY)){
            guiGraphics.fill(this.getX() + cancelX, this.getY() + cancelY, this.getX() + cancelX + buttonWidth, this.getY() + cancelY + buttonHeight, Colors.TRANSPARENT_BLACK_25);
        }
        guiGraphics.drawString(Minecraft.getInstance().font, "start", getX() + startX + 15, getY() + startY + 3, Colors.WHITE);
        guiGraphics.drawString(Minecraft.getInstance().font, "cancel", getX() + cancelX + 12, getY() + cancelY + 3, Colors.WHITE);
    }
}
