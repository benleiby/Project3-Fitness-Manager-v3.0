package com.example.project3fitnessmanagerv3;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ChoiceBox<Location> studioBox;
    @FXML
    private ChoiceBox<String> typeBox;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button clearInputButton;
    @FXML
    private Button removeButton;
    @FXML
    private TextField addFirstNameField;
    @FXML
    private TextField addLastNameField;
    @FXML
    private DatePicker addDobPicker;

    @FXML
    private TableView<FitnessClass> attendanceTable;
    private Schedule schedule;
    private ObservableList<FitnessClass> observableAttendanceList;
    @FXML
    private TableColumn<FitnessClass, String> classInfoColumn;
    @FXML
    private TableColumn<FitnessClass, String> instructorColumn;
    @FXML
    private TableColumn<FitnessClass, String> timeColumn;
    @FXML
    private TableColumn<FitnessClass, String> studioColumn;
    @FXML
    private ChoiceBox<Offer> classTypeBox;
    @FXML
    private ChoiceBox<Location> studioBoxAtt;
    @FXML
    private ChoiceBox<Instructor> instructorBox;
    @FXML
    private TableView<Member> memberAttendanceTable;
    @FXML
    private TableColumn<Member, String> firstNameAttColumn;
    @FXML
    private TableColumn<Member, String> lastNameAttColumn;
    @FXML
    private TableColumn<Member, String> dobAttColumn;
    @FXML
    private TextField firstNameFieldAtt;
    @FXML
    private TextField lastNameAttField;
    @FXML
    private DatePicker dobPickerAtt;
    @FXML
    private Button addMemberAttButton;



    // Backend Components
    private MemberList memberList;
    private ObservableList<Member> observableMemberList;



    @FXML
    public void initialize() throws IOException {

       initializeMemberLists();
       initializeMemberDisplay();

       ObservableList<Location> locationList = FXCollections.observableArrayList(Location.values());
       studioBox.setItems(locationList);

       ObservableList<String> memberTypes = FXCollections.observableArrayList("Basic", "Family", "Premium");
       typeBox.setItems(memberTypes);

       initializeAttendance();
       initializeAttendanceLists();
       initializeAttendanceDisplay();

    }

    @FXML
    public void onRemoveButtonClick(ActionEvent event) {

        if (memberDisplay.getSelectionModel().getSelectedItem() == null) {
            Alert removeFailure = new Alert(Alert.AlertType.ERROR);
            removeFailure.setTitle("Failed to Remove Member:");
            removeFailure.setContentText("Please select a member to remove.");
            removeFailure.showAndWait();
            return;
        }

        Member member = memberDisplay.getSelectionModel().getSelectedItem();

        Alert removeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        removeConfirmation.setTitle("Confirm Member Removal:");
        removeConfirmation.setContentText(
                "Are you sure you want to remove " +
                member.getProfile().getFname() + " " + member.getProfile().getLname() + "?"
        );
        removeConfirmation.showAndWait();
        if (removeConfirmation.getResult().getText().equals("OK")) {
            memberList.remove(member);
            updateObservableMemberList();
            memberDisplay.setItems(observableMemberList);
        }

    }

    @FXML
    public void clearInput(ActionEvent event) {

        firstNameField.clear();
        lastNameField.clear();
        dobPicker.setValue(null);
        studioBox.setValue(null);
        typeBox.setValue(null);

    }

    @FXML
    public void onAddButtonClick(ActionEvent event) {

        String alertContent = getAddMemberAlertContent();
        if (!alertContent.isEmpty()) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Failed to Create Member:");
            errorMessage.setContentText(alertContent);
            errorMessage.showAndWait();
            return;
        }

        String fName = firstNameField.getText();
        String lName = lastNameField.getText();

        Date dob = new Date();
        dob.setDateFromPickerString(dobPicker.getValue().toString());

        if (!dob.isAdultDob()) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Failed to Create Member:");
            errorMessage.setContentText("Member must be 18 or older.");
            errorMessage.showAndWait();
            return;
        }

        Location homeStudio = studioBox.getValue();

        Profile profile = new Profile(fName, lName, dob);
        if (typeBox.getValue().equals("Basic")) {
            addBasic(profile, homeStudio);
        }
        else if (typeBox.getValue().equals("Family")) {
            addFamily(profile, homeStudio);
        }
        else
            addPremium(profile, homeStudio);

    }

    private String getAddMemberAlertContent() {

        String alertContent = "";
        if (firstNameField.getText() == null || firstNameField.getText().trim().isEmpty()) {
            alertContent += "\nFirst name field is empty.";
        }
        if (lastNameField.getText() == null || lastNameField.getText().trim().isEmpty()) {
            alertContent += "\nLast name field is empty.";
        }
        if (dobPicker.getValue() == null) {
            alertContent += "\nDOB field is empty.";
        }
        if (studioBox.getValue() == null) {
            alertContent += "\nStudio field is empty.";
        }
        if (typeBox.getValue() == null) {
            alertContent += "\nMembership type field is empty.";
        }
        return alertContent;

    }

    private void duplicateMemberError(Profile profile) {

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Failed to Create Member:");
        error.setContentText(
            profile.getFname() + " " +
            profile.getLname() + " " +
            "is already in the member database."
        );
        error.showAndWait();

    }

    private void addBasic(Profile profile, Location homeStudio) {

        if (!memberList.add(new Basic(profile, homeStudio))) {
            duplicateMemberError(profile);
            return;
        }

        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
        addNotification.setTitle("Basic Member Added:");
        addNotification.setContentText(
            profile.getFname() + " " +
            profile.getLname() + " " +
            "added to member database."
        );
        addNotification.showAndWait();

        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    private void addFamily(Profile profile, Location homeStudio) {

        if (!memberList.add(new Family(profile, homeStudio))) {
            duplicateMemberError(profile);
            return;
        }

        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
        addNotification.setTitle("Family Member Added:");
        addNotification.setContentText(
                profile.getFname() + " " +
                profile.getLname() + " " +
                "added to member database."
        );
        addNotification.showAndWait();

        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    private void addPremium(Profile profile, Location homeStudio) {

        if (!memberList.add(new Premium(profile, homeStudio))) {
            duplicateMemberError(profile);
            return;
        }

        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
        addNotification.setTitle("Premium Member Added:");
        addNotification.setContentText(
            profile.getFname() + " " +
            profile.getLname() + " " +
            "added to member database."
        );
        addNotification.showAndWait();

        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

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

    @FXML
    public void initializeAttendance() throws IOException {

        ObservableList<Offer> offerList = FXCollections.observableArrayList(Offer.values());
        classTypeBox.setItems(offerList);

        ObservableList<Instructor> instructorList = FXCollections.observableArrayList(Instructor.values());
        instructorBox.setItems(instructorList);

        ObservableList<Location> locationList = FXCollections.observableArrayList(Location.values());
        studioBoxAtt.setItems(locationList);

    }

    private void initializeAttendanceLists() throws IOException {
        schedule = new Schedule();
        schedule.load(new File("src/classSchedule.txt"));

        observableAttendanceList = FXCollections.observableArrayList();
        updateObservableAttendanceList();
    }

    private void updateObservableAttendanceList() {
        observableAttendanceList.clear();
        observableAttendanceList.addAll(Arrays.asList(schedule.getClasses()));
    }

    private void initializeAttendanceDisplay() {
        attendanceTable.setItems(observableAttendanceList);

        createCellValueFactory(classInfoColumn, schedule -> schedule.getClassInfo().toString());
        createCellValueFactory(instructorColumn, schedule -> schedule.getInstructor().toString());
        createCellValueFactory(timeColumn, schedule -> schedule.getTime().toString());
        createCellValueFactory(studioColumn, schedule -> schedule.getStudio().toString());

        memberAttendanceTable.setVisible(false);
        createCellValueFactory(firstNameAttColumn, member -> member.getProfile().getFname());
        createCellValueFactory(lastNameAttColumn, member -> member.getProfile().getLname());
        createCellValueFactory(dobAttColumn, member -> member.getProfile().getDob().toString());

        attendanceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                memberAttendanceTable.setVisible(true);
                updateMemberAttendanceTable(newSelection);
            } else {
                memberAttendanceTable.setVisible(false);
            }
        });
    }

    private void updateMemberAttendanceTable(FitnessClass selectedClass) {
        memberAttendanceTable.getItems().clear();
        memberAttendanceTable.getItems().addAll(selectedClass.getMembers().getMembers());
    }

    @FXML
    private void onAddMemberAttButtonClick(ActionEvent event) {
        String fname = firstNameFieldAtt.getText();
        String lname = lastNameAttField.getText();

        Date dob = new Date();
        dob.setDateFromPickerString(dobPickerAtt.getValue().toString());

        if (!dob.isAdultDob()) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Failed to Create Member:");
            errorMessage.setContentText("Member must be 18 or older.");
            errorMessage.showAndWait();
            return;
        }
        Profile profile = new Profile(fname, lname, dob);
        Member member = new Member(profile, null, null);
        memberAttendanceTable.getItems().add(member);

        firstNameFieldAtt.clear();
        lastNameAttField.clear();
        dobPickerAtt.setValue(null);

//        if (!memberAttendanceTable.add(member) {
//            duplicateMemberError(profile);
//            return;
//        }
//
//        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
//        addNotification.setTitle("Basic Member Added:");
//        addNotification.setContentText(
//                profile.getFname() + " " +
//                        profile.getLname() + " " +
//                        "added to member database."
//        );
//        addNotification.showAndWait();
    }

}