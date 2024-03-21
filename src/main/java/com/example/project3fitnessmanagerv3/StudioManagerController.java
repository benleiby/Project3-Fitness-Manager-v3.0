package com.example.project3fitnessmanagerv3;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.File;

/**
 * Controller class for the Studio Manager application
 * @author Benjamin Leiby
 * @author Matteus Coste
 */
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
    private TableView<FitnessClass> classesDisplay;
    @FXML
    private TableColumn<FitnessClass, String> classInfoColumn;
    @FXML
    private TableColumn<FitnessClass, String> instructorColumn;
    @FXML
    private TableColumn<FitnessClass, String> attStudioColumn;
    @FXML
    private TableColumn<FitnessClass, String> timeColumn;
    @FXML
    private TableView<Member> attendanceDisplay;
    @FXML
    private TableColumn<Member, String> attFirstNameColumn;
    @FXML
    private TableColumn<Member, String> attLastNameColumn;
    @FXML
    private TableColumn<Member, String> attDobColumn;
    @FXML
    private TextField attFirstNameField;
    @FXML
    private TextField attLastNameField;
    @FXML
    private DatePicker attDobPicker;
    @FXML
    private Button attClearInputButton;
    @FXML
    private Button attGuestButton;
    @FXML
    private Button attRemoveButton;

    // Backend Components
    private MemberList memberList;
    private Schedule schedule;

    private ObservableList<Member> observableMemberList;
    private ObservableList<FitnessClass> observableClassList;
    private ObservableList<Member> observableAttendanceList;



    /**
     * Initializes the controller
     */
    @FXML
    public void initialize() {

       initializeMemberLists();
       initializeMemberDisplay();

       initializeSchedule();
       initializeClassesDisplay();

       initializeAttendanceDisplay();

       ObservableList<Location> locationList = FXCollections.observableArrayList(Location.values());
       studioBox.setItems(locationList);

       ObservableList<String> memberTypes = FXCollections.observableArrayList("Basic", "Family", "Premium");
       typeBox.setItems(memberTypes);

    }

    /**
     * Removes the selected member from the member list
     * @param event
     */
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

    /**
     * Handles the event when the "Remove Attendance" button is clicked.
     * It removes the selected member from the attendance list of the selected fitness class.
     * If the member is a family or premium member, it adjusts the main member's guest status or guest pass count accordingly.
     * Displays a confirmation dialog before removing the member.
     */
    @FXML
    public void onRemoveAttendanceClick() {

        if (attendanceDisplay.getSelectionModel().getSelectedItem() == null) {
            Alert removeFailure = new Alert(Alert.AlertType.ERROR);
            removeFailure.setTitle("Failed to Un-Attend:");
            removeFailure.setContentText("Please select a member to remove from the class.");
            removeFailure.showAndWait();
            return;
        }

        Member member = attendanceDisplay.getSelectionModel().getSelectedItem();
        Member mainMember = null;
        FitnessClass fitClass = classesDisplay.getSelectionModel().getSelectedItem();

        if (memberList.find(member) == -1) {

            if (member.getClass().equals(Family.class)) {
                String fName = member.getProfile().getFname().split(" ")[0];
                Profile profile = new Profile(fName, member.getProfile().getLname(), member.getProfile().getDob());
                mainMember = new Family(profile, member.getHomeStudio());
                mainMember = memberList.getMembers()[memberList.find(mainMember)];
                System.out.println(mainMember.toString());
            }
            if (member.getClass().equals(Premium.class)) {
                String fName = member.getProfile().getFname().split(" ")[0];
                Profile profile = new Profile(fName, member.getProfile().getLname(), member.getProfile().getDob());
                mainMember = new Premium(profile, member.getHomeStudio());
                mainMember = memberList.getMembers()[memberList.find(mainMember)];
            }

        }

        Alert removeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        removeConfirmation.setTitle("Confirm Un-Attend Member:");
        removeConfirmation.setContentText(
            "Are you sure you want to remove " +
            member.getProfile().getFname() + " " + member.getProfile().getLname()
            + " from " + fitClass.toString() +" ?"
        );
        removeConfirmation.showAndWait();
        if (removeConfirmation.getResult().getText().equals("OK")) {
            fitClass.getMembers().remove(member);

            if (mainMember != null && member.getClass().equals(Family.class)) {
                ((Family) mainMember).setGuest(true);
                memberList.getMembers()[memberList.find(mainMember)] = mainMember;
            }
            if (mainMember != null && member.getClass().equals(Premium.class)) {
                ((Premium) mainMember).setGuestPass(((Premium) mainMember).getGuestPass() + 1);
                memberList.getMembers()[memberList.find(mainMember)] = mainMember;
            }

            updateObservableMemberList();
            memberDisplay.setItems(observableMemberList);
            updateObservableAttendanceList(fitClass);
            attendanceDisplay.setItems(observableAttendanceList);
        }

    }

    /**
     * Clears the input fields for adding a new member
     * @param event
     */
    @FXML
    public void clearInput(ActionEvent event) {

        firstNameField.clear();
        lastNameField.clear();
        dobPicker.setValue(null);
        studioBox.setValue(null);
        typeBox.setValue(null);

    }

    /**
     * Handles the event when the "Add Member" button is clicked
     * @param event
     */
    @FXML
    public void attClearInput(ActionEvent event) {

        attFirstNameField.clear();
        attLastNameField.clear();
        attDobPicker.setValue(null);

    }

    /**
     * Handles the event when the "Add" button is clicked to create a new member.
     * Validates the input fields and displays an error message if any field is empty or if the member is under 18 years old.
     * Creates a new member with the provided details and adds them to the member database.
     * Displays a success message upon successful addition.
     * @param event
     */
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

    /**
     * Generates alert content detailing which fields are empty when adding a member.
     * @return
     */
    @FXML
    public void onAttendButtonClick(ActionEvent event) {

        String alertContent = getAttendMemberAlertContent();
        if (!alertContent.isEmpty()) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Failed to Attend Member:");
            errorMessage.setContentText(alertContent);
            errorMessage.showAndWait();
            return;
        }

        String fName = attFirstNameField.getText();
        String lName = attLastNameField.getText();
        FitnessClass fitClass = classesDisplay.getSelectionModel().getSelectedItem();

        Date dob = new Date();
        dob.setDateFromPickerString(attDobPicker.getValue().toString());

        Member member = new Member();
        member.setProfile(new Profile(fName, lName, dob));

        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle("Failed to Attend Member:");

        if (memberList.find(member) == -1) {
            errorMessage.setContentText("Member is not in the database");
            errorMessage.showAndWait();
            return;
        }
        member = memberList.getMembers()[memberList.find(member)];
        if (member.isExpired()) {
            errorMessage.setContentText("Membership expired on " + member.getExpire() + ".");
            errorMessage.showAndWait();
            return;
        }
        if (member.getClass().equals(Basic.class) && member.getHomeStudio() != fitClass.getStudio()) {
            errorMessage.setContentText("Member can only attend at their home studio.");
            errorMessage.showAndWait();
            return;
        }

        for (int i = 0; i < schedule.getClasses().length; i++) {
            if (schedule.getClasses()[i].getTime().equals(fitClass.getTime())) {
                // check if member is on list.
                if (schedule.getClasses()[i].getMembers().find(member) != -1) {
                    errorMessage.setContentText("Time conflict: member is already" +
                            " attending a class at this time.");
                    errorMessage.showAndWait();
                    return;
                }
            }
        }

        if (!fitClass.getMembers().add(member)) {
            errorMessage.setContentText("Member is already attending this class.");
            errorMessage.showAndWait();
            return;
        }

        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
        addNotification.setTitle("Attendance Recorded:");
        addNotification.setContentText(
            member.getProfile().getFname() + " " +
            member.getProfile().getLname() +
            " added to " + fitClass.toString()
        );
        addNotification.showAndWait();

        updateObservableAttendanceList(fitClass);
        attendanceDisplay.setItems(observableAttendanceList);

    }

    /**
     * Manages guest attendance for a fitness class, ensuring eligibility and updating member and attendance
     * displays with appropriate notifications. Handles validation checks for guest membership status and available guest passes.
     * */
    @FXML
    public void onAttendGuestClick() {

        String alertContent = getAttendMemberAlertContent();
        if (!alertContent.isEmpty()) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Failed to Attend Guest:");
            errorMessage.setContentText(alertContent);
            errorMessage.showAndWait();
            return;
        }

        String fName = attFirstNameField.getText();
        String lName = attLastNameField.getText();
        FitnessClass fitClass = classesDisplay.getSelectionModel().getSelectedItem();

        Date dob = new Date();
        dob.setDateFromPickerString(attDobPicker.getValue().toString());

        Member member = new Member();
        member.setProfile(new Profile(fName, lName, dob));

        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle("Failed to Attend Guest:");

        if (memberList.find(member) == -1) {
            errorMessage.setContentText("Member is not in the database");
            errorMessage.showAndWait();
            return;
        }
        member = memberList.getMembers()[memberList.find(member)];
        if (member.isExpired()) {
            errorMessage.setContentText("Membership expired on " + member.getExpire() + ".");
            errorMessage.showAndWait();
            return;
        }
        if (member.getClass().equals(Basic.class)) {
            errorMessage.setContentText("Basic members don't have guest privileges.");
            errorMessage.showAndWait();
            return;
        }

        // 2 scenarios: premium member and family member

        if (member.getClass().equals(Family.class)) {
            if (((Family) member).hasGuest()) {
                Member guest = new Family(new Profile(fName + " (Guest)", lName, dob), member.getHomeStudio());
                fitClass.getMembers().add(guest);
                ((Family) member).setGuest(false);
                memberList.getMembers()[memberList.find(member)] = member;
            }
            else {
                errorMessage.setContentText("Guest pass not available.");
                errorMessage.showAndWait();
                return;
            }
        }
        if (member.getClass().equals(Premium.class)) {
            if ((((Premium) member).getGuestPass()) > 0) {

                int guestNum = -(((Premium) member).getGuestPass()) + 4;
                Member guest = new Premium(
                    new Profile(fName + " (Guest " + guestNum + "/3)", lName, dob), member.getHomeStudio()
                );
                fitClass.getMembers().add(guest);
                ((Premium) member).setGuestPass(((Premium) member).getGuestPass() - 1);
                memberList.getMembers()[memberList.find(member)] = member;
            }
            else {
                errorMessage.setContentText("Guest pass not available.");
                errorMessage.showAndWait();
                return;
            }
        }

        Alert addNotification = new Alert(Alert.AlertType.INFORMATION);
        addNotification.setTitle("Guest Attendance Recorded:");
        addNotification.setContentText("Guest of " +
                member.getProfile().getFname() + " " +
                member.getProfile().getLname() +
                " added to " + fitClass.toString()
        );
        addNotification.showAndWait();

        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);
        updateObservableAttendanceList(fitClass);
        attendanceDisplay.setItems(observableAttendanceList);

    }

    /**
     * Constructs the content for an alert message indicating any missing or incomplete information needed to add a new member.
     * It checks if the first name, last name, date of birth, studio selection, and membership type fields are empty,
     * appending corresponding messages to the alert content.
     * @return the content for the alert message, highlighting any missing or incomplete information.
     */
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

    /**
     * Constructs the content for an alert message indicating any missing or incomplete information
     * needed to attend a class.
     * It checks if the first name, last name, date of birth, and class selection fields are empty, appending corresponding
     * messages to the alert content.
     * @return the content for the alert message, highlighting any missing or incomplete information
     */
    private String getAttendMemberAlertContent() {

        String alertContent = "";
        if (attFirstNameField.getText() == null || attFirstNameField.getText().trim().isEmpty()) {
            alertContent += "\nFirst name field is empty.";
        }
        if (attLastNameField.getText() == null || attLastNameField.getText().trim().isEmpty()) {
            alertContent += "\nLast name field is empty.";
        }
        if (attDobPicker.getValue() == null) {
            alertContent += "\nDOB field is empty.";
        }
        if (classesDisplay.getSelectionModel().getSelectedItem() == null) {
            alertContent += "\nNo class has been selected";
        }
        return alertContent;

    }

    /**
     * Displays an error alert indicating that a member with the provided profile
     * already exists in the database.
     * @param profile
     */
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

    /**
     * Adds a basic member to the member list based on the provided profile and home studio.
     * If the addition fails due to a duplicate member, displays an error alert.
     * @param profile
     * @param homeStudio
     */
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

    /**
     * Adds a family member to the member list based on the provided profile and home studio.
     * If the addition fails due to a duplicate member, displays an error alert.
     * @param profile
     * @param homeStudio
     */
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

    /**
     * Adds a premium member to the member list based on the provided profile and home studio.
     * If the addition fails due to a duplicate member, displays an error alert.
     * @param profile
     * @param homeStudio
     */
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

    /**
     * Sorts the member display table by profile
     * @param event
     */
    @FXML
    public void sortDisplayByProfile(ActionEvent event) {

        memberList.sortByMember();
        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    /**
     * Sorts the member display table by county
     * @param event
     */
    @FXML
    public void sortDisplayByCounty(ActionEvent event) {

        memberList.sortByCounty();
        updateObservableMemberList();
        memberDisplay.setItems(observableMemberList);

    }

    /**
     * Toggle the visibility of the fee column
     * @param event
     */
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
     * Initializes the schedule by loading class schedule data from a file and populating the observable class list.
     * The method loads the schedule from a file, creates observable lists for both classes and attendance, and updates the observable class list.
     */
    private void initializeSchedule() {

        schedule = new Schedule();
        schedule.load(new File("src/classSchedule.txt"));

        observableClassList = FXCollections.observableArrayList();
        observableAttendanceList = FXCollections.observableArrayList();
        updateObservableClassList();

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
     * Updates the observable class list with the classes from the schedule.
     * The method clears the current observable class list and populates it with classes from the schedule.
     */
    private void updateObservableClassList() {

        observableClassList.clear();
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            observableClassList.add(schedule.getClasses()[i]);
        }

    }

    /**
     * Updates the observable attendance list for a given fitness class.
     * Clears the current observable attendance list and populates it with members attending the specified fitness class.
     * @param fitClass
     */
    private void updateObservableAttendanceList(FitnessClass fitClass) {

        observableAttendanceList.clear();
        MemberList attendance = schedule.getClasses()[schedule.find(fitClass)].getMembers();
        for (int i = 0; i < attendance.getSize(); i++) {
            observableAttendanceList.add(attendance.getMembers()[i]);
        }

    }

    /**
     * Displays the attendance for the selected fitness class.
     * Retrieves the selected fitness class from the classes display, updates the observable attendance list accordingly,
     * and sets the attendance display with the updated list.
     * @param mouseEvent
     */
    @FXML
    public void displaySelectedAttendance(MouseEvent mouseEvent) {

        FitnessClass fitClass;
        try {
            fitClass = classesDisplay.getSelectionModel().getSelectedItem();
        }
        catch (NullPointerException ignored) {
            return;
        }
        updateObservableAttendanceList(fitClass);
        attendanceDisplay.setItems(observableAttendanceList);

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
     * Initializes the classes display by setting its items to the observable class list and creating cell value factories
     * for various columns such as class info, instructor, studio, and time.
     */
    private void initializeClassesDisplay() {

        classesDisplay.setItems(observableClassList);
        createCellValueFactory(classInfoColumn, fitnessClass -> fitnessClass.getClassInfo().toString());
        createCellValueFactory(instructorColumn, fitnessClass -> fitnessClass.getInstructor().toString());
        createCellValueFactory(attStudioColumn, fitnessClass -> fitnessClass.getStudio().toString());
        createCellValueFactory(timeColumn, fitnessClass -> fitnessClass.getTime().toString());

    }

    /**
     * Initializes the attendance display by setting its items to the observable attendance list and creating cell value factories
     * for columns representing the first name, last name, and date of birth of the members.
     */
    private void initializeAttendanceDisplay() {

        attendanceDisplay.setItems(observableAttendanceList);
        createCellValueFactory(attFirstNameColumn, member -> member.getProfile().getFname());
        createCellValueFactory(attLastNameColumn, member -> member.getProfile().getLname());
        createCellValueFactory(attDobColumn, member -> member.getProfile().getDob().toString());

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