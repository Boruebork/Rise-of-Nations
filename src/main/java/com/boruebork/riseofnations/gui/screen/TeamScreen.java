package com.boruebork.riseofnations.gui.screen;

import com.boruebork.riseofnations.RiseofNations;
import com.boruebork.riseofnations.gui.buttons.TeamList;
import com.boruebork.riseofnations.gui.buttons.TextureButton;
import com.boruebork.riseofnations.network.packets.CreateNewTeamData;
import com.boruebork.riseofnations.network.packets.JoinTeamPacket;
import com.boruebork.riseofnations.network.packets.LeaveTeamPacket;
import com.boruebork.riseofnations.network.packets.RequestTeamData;
import com.boruebork.riseofnations.team.TeamData;
import com.boruebork.riseofnations.team.TeamEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.boruebork.riseofnations.network.NetowrkConstants.NULL;

public class TeamScreen extends Screen {
    private TextureButton clb;
    private Minecraft MC;
    public String teamName;
    public List<TeamData> teams;
    public String leaderName;
    public boolean hasTeam;
    public List<String> players;
    private boolean seePlayers = false;
    private List<Button> teamButtons;
    private Button LeaveTeam;
    private static final Identifier CHANGE_LEADER_BUTTON = Identifier.fromNamespaceAndPath(RiseofNations.MODID, "textures/gui/team/clb.png");
    public TeamScreen() {
        super(Component.translatable("gui.riseofnations.team"));
        this.MC = Minecraft.getInstance();
        this.teamName = NULL;
    }

    @Override
    protected void init() {
        assert MC.player != null;
        ClientPacketDistributor.sendToServer(new RequestTeamData(MC.player.getName().getString()));
        super.init();
    }
    public void rebuildWidgets() {
        if (Objects.equals(this.teamName, NULL)){
            this.hasTeam = false;
            System.out.println("Rebuilt widget for no team");
            Button button = Button.builder(Component.literal("Create new Team"), this::createNewTeam).build();
            button.setPosition(0, 0);
            addRenderableWidget(button);
        }else{
            clearWidgets();
            this.hasTeam = true;
            Button seeMembers = Button.builder(Component.literal("See Members"), this::seeMembers).build();
            seeMembers.setPosition(0, 20);
            Button focusButton = Button.builder(Component.literal("Focuses"), this::openFocusScreen).build();
            Button leaveTeam = Button.builder(Component.literal("Leave Team"), this::leaveTeam).build();
            Button destroyTeam = Button.builder(Component.literal("Disband Team"), this::destroyTeam).build();
            focusButton.setPosition(this.width - 150, 0);
            leaveTeam.setPosition(this.width-150, 20);
            destroyTeam.setPosition(this.width -150, 40);
            addRenderableWidget(leaveTeam);
            addRenderableWidget(destroyTeam);
            addRenderableWidget(focusButton);
            addRenderableWidget(seeMembers);
            System.out.println("Rebuilt widget!");
        }

    }

    private void destroyTeam(Button button) {
    }

    private void leaveTeam(Button button) {
        ClientPacketDistributor.sendToServer(new LeaveTeamPacket(MC.player.getName().getString(), this.teamName));
    }

    public void acceptNoTeamDataFromServer(){
        System.err.println("No team data accepted from server!");
        clearWidgets();
        TeamList teamList = new TeamList(minecraft, 150, this.teams.size()*20, 0, 20);
        teamList.setParent(this);
        int i = 0;
        for (TeamData data : this.teams){
            System.out.println(data.teamName);
            teamList.addTeam(Component.literal(data.teamName), i);
            i++;
        }
        this.teamName = NULL;
        teamList.setPosition(0, 20);
        rebuildWidgets();
        addRenderableWidget(teamList);
    }
    private Component selectedTeam;
    public void onTeamSelected(Component name){
        if (name == null) return;
        System.err.println(name.getString());
        selectedTeam = name;
        Button join = Button.builder(Component.literal("Join"), this::joinTeam).build();
        join.setPosition(200, 150);
        addRenderableWidget(join);
    }

    private void joinTeam(Button button) {
        ClientPacketDistributor.sendToServer(new JoinTeamPacket(selectedTeam.getString()));

    }

    private void openFocusScreen(Button button) {
        MC.setScreen(new FocusScreen());
    }

    EditBox teamNameEditbox;
    private void createNewTeam(Button button) {
        clearWidgets();
        teamNameEditbox = new EditBox(this.font,0,0, 100, 20, Component.literal("Team Name"));
        Button create = Button.builder(Component.literal("Create"), this::sendNewTeamPacket).build();
        create.setPosition(0, 60);
        addRenderableWidget(teamNameEditbox);
        addRenderableWidget(create);
    }
    public void setToMainTeamScreen(){
        Button button = Button.builder(Component.literal("See Members"), this::seeMembers).build();
        button.setPosition(0, 20);
    }

    private void seeMembers(Button button) {
        this.seePlayers = true;
        clearWidgets();
        Button toMainMenuButton = Button.builder(Component.literal("To main menu"), this::toMainMenu).build();
        toMainMenuButton.setPosition(this.width-100, 0);
        addRenderableWidget(toMainMenuButton);
    }

    private void toMainMenu(Button button) {
        this.rebuildWidgets();
        this.seePlayers = false;
    }

    public void sendNewTeamPacket(Button button){
        ClientPacketDistributor.sendToServer(new CreateNewTeamData(this.teamNameEditbox.getValue(), MC.player.getName().getString()));

        
    }

    private void changeLeader(Button button) {
        assert MC.player != null;
        //ClientPacketDistributor.sendToServer(new ChangeteamLeaderData(this.teamName, MC.player.getName().getString()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        guiGraphics.drawString(this.font, this.teamName,this.width/2, 0, 0xFFFFFFFF);
        if (this.seePlayers){
            for (int i = 0; i < this.players.size(); ++i){
                guiGraphics.drawString(this.getFont(), this.players.get(i), 0, i*10, 0xFFFFFFFF);
            }
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xFFAAAAAA);

        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

    }
}