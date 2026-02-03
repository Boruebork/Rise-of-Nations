package com.boruebork.riseofnations.gui.screen.team;

import com.boruebork.riseofnations.gui.buttons.TeamList;
import com.boruebork.riseofnations.gui.screen.focus.FocusScreen;
import com.boruebork.riseofnations.gui.util.Colors;
import com.boruebork.riseofnations.network.packets.*;
import com.boruebork.riseofnations.team.TeamData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

public class TeamScreen extends Screen {
    //=========================DATA=========================
    public List<TeamData> teams;
    public TeamData thisTeamData;
    public int teamID;

    public TeamScreenState state;
    public TeamScreen() {
        super(Component.translatable("gui.riseofnations.team"));
        this.state = TeamScreenState.LOADING;
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

    public void newRebuildWidgets(){
        System.err.println(state);
        clearWidgets();
        switch (state){
            case NO_TEAM -> rebuildNoTeam();
            case JOIN_TEAM -> rebuildJoinTeam();
            case CREATE_TEAM -> rebuildCreateTeam();
            case MAIN -> rebuildMain();
            case LOADING -> rebuildLoading();
            case MEMBERS -> rebuildMembers();
            case EDIT_TEAM -> rebuildEditTeam();
        }

    }


    //======================================Rebuilders==================================================================
    EditBox editTeamName;
    private void rebuildEditTeam() {
        editTeamName = new EditBox(this.font, 100, 20, Component.literal("team name"));
        editTeamName.setValue(this.thisTeamData.teamName());
        Button save = Button.builder(Component.literal("Save"), this::save).build();
        Button cancel = Button.builder(Component.literal("Cancel"), this::cancelSave).build();
        save.setPosition(0, this.height - 20);
        cancel.setPosition(this.width - 150, this.height-20);
        editTeamName.setPosition(100, 100);
        addRenderableWidget(save);
        addRenderableWidget(editTeamName);
        addRenderableWidget(cancel);

    }

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
        Button editTeam = Button.builder(Component.literal("Edit Team"), this::editTeam).build();
        focusButton.setPosition(this.width - 150, 0);
        leaveTeam.setPosition(this.width-150, 20);
        destroyTeam.setPosition(this.width -150, 40);
        editTeam.setPosition(this.width -150, 60);
        addRenderableWidget(leaveTeam);
        addRenderableWidget(destroyTeam);
        addRenderableWidget(focusButton);
        addRenderableWidget(seeMembers);
        addRenderableWidget(editTeam);
        System.out.println("Rebuilt widget!");
        System.err.println(this.thisTeamData.teamName());
    }

    private void editTeam(Button button) {
        this.setState(TeamScreenState.EDIT_TEAM);
        newRebuildWidgets();
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
            teamList.addTeam(Component.literal(data.teamName), i, data.color());
            i++;
        }
        //this.teamName = NULL;
        teamList.setPosition(0, 25);
        addRenderableWidget(button);
        addRenderableWidget(teamList);
    }
    //================================Tranistors and Packeters===========================//
    private void cancelSave(Button button) {
        this.setState(TeamScreenState.MAIN);
        newRebuildWidgets();
    }

    private void save(Button button) {
        if (editTeamName.getValue().length() > 100) return;
        ClientPacketDistributor.sendToServer(new ChangeTeamDataPacket(this.teamID,editTeamName.getValue()));
    }
    private void destroyTeam(Button button) {
        ClientPacketDistributor.sendToServer(new DisbandTeamPacket(this.teamID));
    }

    private void leaveTeam(Button button) {
        ClientPacketDistributor.sendToServer(new LeaveTeamPacket(minecraft.player.getName().getString(), this.thisTeamData.teamName));
    }
    private int selectedTeamIdx;
    public void onTeamSelected(int id){
        selectedTeamIdx = id;
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
        guiGraphics.drawString(this.font, "State: " + this.state, 0, 0, Colors.GREEN);
        if (this.state != null){
            switch (this.state){
                case JOIN_TEAM -> guiGraphics.drawString(this.font, this.teams.get(selectedTeamIdx).teamName(), this.width/2, 0, Colors.WHITE);
                case MAIN -> guiGraphics.drawString(this.font,  this.thisTeamData == null ? "" : this.thisTeamData.teamName,this.width/2, 0, Colors.WHITE);
                case EDIT_TEAM -> guiGraphics.drawString(this.font, "Edit Team Settings", this.width/2 - 40, 40, Colors.WHITE);
            }
        }
    }
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, this.width, this.height, 0xFFAAAAAA);


    }
}