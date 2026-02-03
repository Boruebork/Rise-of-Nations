package com.boruebork.riseofnations.gui.screen.focus;

import com.boruebork.riseofnations.api.ModRegistries;
import com.boruebork.riseofnations.focus.FocusData;
import com.boruebork.riseofnations.gui.buttons.FocusButton;
import com.boruebork.riseofnations.gui.buttons.FocusDataMenu;
import com.boruebork.riseofnations.gui.util.Colors;
import com.boruebork.riseofnations.network.packets.RequestFocusData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FocusScreen extends Screen {
    public Set<Integer> finishedFocuses;
    private int x;
    public FocusDataMenu startFocusMenu = null;
    private int y;
    public int currentFocus = -1;
    public int timeUntilEndOfFocus;
    private List<FocusData> focusData;
    private List<FocusButton> focusButtons;

    public FocusScreen() {
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
    public void beginFocusOnClient(int focusId){
        this.timeUntilEndOfFocus = focusData.get(focusId).timeInTicks;
        this.currentFocus = focusId;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        if (this.startFocusMenu != null){
            if (this.startFocusMenu.touchingMouse(event.x() - dragX, event.y() - dragY)){
                return super.mouseDragged(event, dragX, dragY);
            }
        }
        x = Math.max(0, x + (int) dragX);
        y = Math.max(0, y + (int) dragY);
        return super.mouseDragged(event, dragX, dragY);

    }
    public void initFocusButtons(){
        int i = 0;
        for (FocusData e: focusData){
            this.focusButtons.add(new FocusButton(i, e.texture_id(),e.x, e.y, this::selectFocus));
            this.focusButtons.getLast().setPosition(e.x, e.y);
            this.addRenderableWidget(this.focusButtons.getLast());
            i++;
        }
    }

    private void selectFocus(Button button) {
        if (button instanceof FocusButton focusButton){
            if (this.startFocusMenu != null){
                removeWidget(this.startFocusMenu);
                this.startFocusMenu = null;
            }
            FocusDataMenu dataMenu = new FocusDataMenu(this, 0, 0, focusData.get(focusButton.id()).name, focusData.get(focusButton.id()).description, focusData.get(focusButton.id()).texture_id(), focusButton.id());
            addRenderableWidget(dataMenu);
            this.startFocusMenu = dataMenu;
        }
    }

    @Override
    public void tick() {
        super.tick();
        //Client Side Interpolation
        if (timeUntilEndOfFocus > 0){
            timeUntilEndOfFocus--;
        }
    }

    private void moveButtons() {
        for (FocusButton button : this.focusButtons){
            button.setPosition(button.getDefaultX() - x,  button.getDefaultY() - y);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawString(this.getFont(), "X: " + x + " Y: " + y, 0, 0, Colors.GREEN);
        moveButtons();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, this.width, this.height, Colors.DARK_GRAY);
    }
    public void pRemoveWidget(GuiEventListener widget){
        this.removeWidget(widget);
    }
}
