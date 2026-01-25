package com.boruebork.riseofnations.gui.screen;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.api.ModRegistries;
import com.boruebork.riseofnations.focus.FocusData;
import com.boruebork.riseofnations.gui.buttons.FocusButton;
import com.boruebork.riseofnations.network.packets.RequestFocusData;
import com.boruebork.riseofnations.network.packets.StartFocusOnServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FocusScreen extends Screen {
    public Set<Integer> finishedFocuses;
    private int x;
    private int y;
    public int currentFocus = -1;
    private String ONGOING_FOCUS;
    private List<FocusData> focusData;
    private List<Button> focusButtons;

    protected FocusScreen() {
        super(Component.literal("Focus Screen"));
    }

    @Override
    protected void init() {
        Registry<FocusData> registry =
                this.getMinecraft().level.registryAccess().lookupOrThrow(ModRegistries.FOCUS_KEY);
        this.focusData = registry.stream().toList();
        this.focusButtons = new ArrayList<>();
        ClientPacketDistributor.sendToServer(new RequestFocusData(Minecraft.getInstance().player.getName().getString()));

    }

    @Override
    public boolean mouseDragged(MouseButtonEvent p_446602_, double dragX, double dragY) {
        if (x + dragX >= 0){
            x += (int) dragX;
        }
        if (y + dragY >= 0){
            y += (int) dragY;
        }
        moveButtons(this.x, this.y);
        return super.mouseDragged(p_446602_, dragX, dragY);

    }
    public void publicInitFocusButtons(){
        this.initFocusButtons(this.focusData, this.x, this.y);
    }
    private void initFocusButtons(List<FocusData> list, double relX, double relY){
        for (FocusData e: list){
            //this.focusButtons.add(Button.builder(Component.literal(e.name), this::startFocus).build());
            this.focusButtons.add(new FocusButton(e.x, e.y, Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/focus/button/" + e.id +".png"), this::startFocus));
            this.focusButtons.getLast().setPosition(e.x, e.y);
            this.addRenderableWidget(this.focusButtons.getLast());

        }
    }

    private void startFocus(Button button) {
        for (int i = 0; i < focusData.size(); ++i){
            if (focusButtons.get(i).equals(button)){
                for (int el : this.focusData.get(i).focusParents){
                    if (!this.finishedFocuses.contains(el)) {
                        return;
                    }
                }
                if (i != currentFocus){
                    this.beginFocus(i);
                }
                return;
            }
        }
    }

    private void beginFocus(int i) {
        ClientPacketDistributor.sendToServer(new StartFocusOnServer(i));
    }

    @Override
    public void tick() {
        super.tick();
        this.moveButtons(this.x, this.y);
    }

    private void moveButtons(int relX, int relY) {
        for (Button button : this.focusButtons){
            button.setPosition(button.getX() - relX,  button.getY() - relY);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, this.width, this.height, 0xFFAAAAAA);
    }

    public void rebuildFocuses() {

    }
}
