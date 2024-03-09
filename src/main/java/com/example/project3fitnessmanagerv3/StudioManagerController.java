package com.example.project3fitnessmanagerv3;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.File;

public class StudioManagerController {

    // UI Components
    @FXML
    private TableView<Member> memberDisplay;
    @FXML
    private TableColumn<Member, String> firstNameColumn;
    @FXML
    private TableColumn<Member, String> lastNameColumn;
    @FXML
    private TableColumn<Member, String> dobColumn;
    @FXML
    private TableColumn<Member, String> expiresColumn;
    @FXML
    private TableColumn<Member, String> homeStudioColumn;
    @FXML
    private TableColumn<Member, String> zipColumn;
    @FXML
    private TableColumn<Member, String> countyColumn;
    @FXML
    private TableColumn<Member, String> typeColumn;
    @FXML
    private TableColumn<Member, String> infoColumn;
    @FXML
    private TableColumn<Member, String> dueColumn;
    @FXML
    private CheckBox hideFeesCheckBox;
    @FXML
    private RadioButton sortByProfileRadio;
    @FXML
    private RadioButton sortByCountyRadio;


    // Backend Components
    private MemberList memberList;
    private ObservableList<Member> observableMemberList;

    @FXML
    public void initialize() {

       initializeMemberLists();
       initializeMemberDisplay();

    }

    @FXML
    public void sortDisplayByProfile(ActionEvent event) {

        memberList.sortByMember();
        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    @FXML
    public void sortDisplayByCounty(ActionEvent event) {

        memberList.sortByCounty();
        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    @FXML
    public void toggleFees(ActionEvent event) {
        dueColumn.setVisible(!hideFeesCheckBox.isSelected());
    }

    /**
     * Initializes memberList structure and populates with data from memberList.txt.
     * Initializes observableMemberList and populates with data from memberList.
     */
    private void initializeMemberLists() {

        memberList = new MemberList();
        memberList.load(new File("src/memberList.txt"));

        observableMemberList = FXCollections.observableArrayList();
        updateObservableMemberList();

    }

    /**
     * Clears the current observableMemberList.
     * Copies data from memberList to observableMemberList.
     * DOES NOT UPDATE THE DISPLAY, only the observableMemberList that it reads.
     */
    private void updateObservableMemberList() {

        observableMemberList.clear();
        for (int i = 0; i < memberList.getSize(); i++) {
            observableMemberList.add(memberList.getMembers()[i]);
        }

    }

    /**
     * Assigns the observableList to the memberDisplay.
     * Sets up how each column will determine its value.
     */
    private void initializeMemberDisplay() {

        memberDisplay.setItems(observableMemberList);
        createCellValueFactory(firstNameColumn, member -> member.getProfile().getFname());
        createCellValueFactory(lastNameColumn, member -> member.getProfile().getLname());
        createCellValueFactory(dobColumn, member -> member.getProfile().getDob().toString());
        createCellValueFactory(expiresColumn, member -> member.getExpire().toString());
        createCellValueFactory(homeStudioColumn, member -> member.getHomeStudio().toString());
        createCellValueFactory(zipColumn, member -> member.getHomeStudio().getZip());
        createCellValueFactory(countyColumn, member -> member.getHomeStudio().getCounty());
        createCellValueFactory(typeColumn, member -> member.getClass().getSimpleName());
        createCellValueFactory(infoColumn, Member::getInfo);
        createCellValueFactory(dueColumn, member -> String.valueOf(member.bill()));
        dueColumn.setVisible(false);

    }

    /**
     * Defines how a column will determine its value.
     * @param column Table column to define the behavior of.
     * @param valueExtractor Callback to get the field of an object.
     * @param <T> Type from the observableList object. In this case, it's Member.
     */
    private <T> void createCellValueFactory(TableColumn<T, String> column, Callback<T, String> valueExtractor) {

        column.setCellValueFactory(cellData -> {
            String value = valueExtractor.call(cellData.getValue());
            return new SimpleStringProperty(value);
        } );

    }

}