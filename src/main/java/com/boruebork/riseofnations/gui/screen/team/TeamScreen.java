package com.boruebork.riseofnations.gui.screen.team;

import com.boruebork.riseofnations.gui.buttons.TeamList;
import com.boruebork.riseofnations.gui.screen.FocusScreen;
import com.boruebork.riseofnations.network.packets.CreateNewTeamData;
import com.boruebork.riseofnations.network.packets.JoinTeamPacket;
import com.boruebork.riseofnations.network.packets.LeaveTeamPacket;
import com.boruebork.riseofnations.network.packets.RequestTeamData;
import com.boruebork.riseofnations.team.TeamData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;
import java.util.Objects;

import static com.boruebork.riseofnations.network.NetowrkConstants.NULL;

public class TeamScreen extends Screen {
    //=========================DATA=========================
    //TODO Another architecture shift, make data, thisTeamData and teamID the gods of this class
    //TODO Rebuild this mess into a clean system with TeamScreenState
    public List<TeamData> teams;
    public TeamData thisTeamData;
    public int teamID;

    public TeamScreenState state;
    //====For removal=======
    @Deprecated(forRemoval = true)
    public boolean hasTeam;
    public TeamScreen() {
        super(Component.translatable("gui.riseofnations.team"));
    }
    public void setState(TeamScreenState state){
        this.state = state;
    }

    @Override
    protected void init() {
        assert minecraft.player != null;
        ClientPacketDistributor.sendToServer(new RequestTeamData(minecraft.player.getName().getString()));
        super.init();
    }

    /*@Deprecated(forRemoval = true)
    public void rebuildWidgets() {
        if (Objects.equals(this.thisTeamData.teamName, NULL)){
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

    }*/
    public void newRebuildWidgets(){
        /*//TODO: Questionable
        if (thisTeamData == null && state == TeamScreenState.MAIN) this.state = TeamScreenState.NO_TEAM;
        else this.state = TeamScreenState.MAIN;*/
        System.err.println(state);
        clearWidgets();
        switch (state){
            case NO_TEAM -> rebuildNoTeam();
            case JOIN_TEAM -> rebuildJoinTeam();
            case CREATE_TEAM -> rebuildCreateTeam();
            case MAIN -> rebuildMain();
            case LOADING -> rebuildLoading();
            case MEMBERS -> rebuildMembers();
        }

    }
    //======================================Rebuilders==================================================================

    private void rebuildMembers() {
        Button toMainMenuButton = Button.builder(Component.literal("To main menu"), this::toMainMenu).build();
        toMainMenuButton.setPosition(this.width-100, 0);
        addRenderableWidget(toMainMenuButton);
    }

    private void rebuildLoading() {
    }

    private void rebuildMain() {
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
    EditBox teamNameEditbox;
    private void rebuildCreateTeam() {
        teamNameEditbox = new EditBox(this.font,0,0, 100, 20, Component.literal("Team Name"));
        Button create = Button.builder(Component.literal("Create"), this::sendNewTeamPacket).build();
        create.setPosition(0, 60);
        addRenderableWidget(teamNameEditbox);
        addRenderableWidget(create);
    }

    //TODO do some physical stuff
    private void rebuildJoinTeam() {
        /*if (name == null) return;
        System.err.println(name.getString());
        selectedTeam = name;*/
        Button join = Button.builder(Component.literal("Join"), this::joinTeam).build();
        join.setPosition(200, 150);
        addRenderableWidget(join);
    }

    private void rebuildNoTeam() {
        System.out.println("Rebuilt widget for no team");
        Button button = Button.builder(Component.literal("Create new Team"), this::createNewTeam).build();
        button.setPosition(0, 0);

        TeamList teamList = new TeamList(minecraft, 150, this.height - 120, 0, 20);
        teamList.setParent(this);
        int i = 0;
        for (TeamData data : this.teams){
            System.out.println(data.teamName);
            teamList.addTeam(Component.literal(data.teamName), i);
            i++;
        }
        //this.teamName = NULL;
        teamList.setPosition(0, 20);
        addRenderableWidget(button);
        addRenderableWidget(teamList);
    }
    //================================Tranistors and Packeters===========================//

    private void destroyTeam(Button button) {
    }

    private void leaveTeam(Button button) {
        ClientPacketDistributor.sendToServer(new LeaveTeamPacket(minecraft.player.getName().getString(), this.thisTeamData.teamName));
    }
    private int selectedTeamIdx;
    public void onTeamSelected(Component name){
        this.state = TeamScreenState.JOIN_TEAM;
        newRebuildWidgets();
    }

    private void joinTeam(Button button) {
        ClientPacketDistributor.sendToServer(new JoinTeamPacket(selectedTeamIdx));
    }

    private void openFocusScreen(Button button) {
        minecraft.setScreen(new FocusScreen());
    }

    private void createNewTeam(Button button) {
        this.state = TeamScreenState.CREATE_TEAM;
        newRebuildWidgets();
    }
    private void seeMembers(Button button) {
        this.state = TeamScreenState.MEMBERS;
        newRebuildWidgets();
    }

    private void toMainMenu(Button button) {
        this.state = TeamScreenState.MAIN;
        this.newRebuildWidgets();
    }

    public void sendNewTeamPacket(Button button){
        System.err.println("send create new Team packet!");
        ClientPacketDistributor.sendToServer(new CreateNewTeamData(this.teamNameEditbox.getValue(), minecraft.player.getName().getString()));

        
    }
    //========================= Overrides ===================================

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        guiGraphics.drawString(this.font,  this.thisTeamData == null ? "" : this.thisTeamData.teamName,this.width/2, 0, 0xFFFFFFFF);
        if (this.state == TeamScreenState.MEMBERS){
            for (int i = 0; i < this.thisTeamData.members.size(); ++i){
                guiGraphics.drawString(this.getFont(), this.thisTeamData.members.get(i), 0, i*10, 0xFFFFFFFF);
            }
        }
    }
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xFFAAAAAA);
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

    }
}