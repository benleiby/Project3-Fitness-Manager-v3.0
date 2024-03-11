package com.example.project3fitnessmanagerv3;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Manages and organizes fitness classes
 * @author Matteus Coste
 * @author Benjamin Leiby
 */
public class Schedule {
    private FitnessClass [] classes;
    private int numClasses;
    private static final int NOT_FOUND = -1;

    public Schedule() {
        numClasses = 0;
    }

    public FitnessClass[] getClasses() {
        return classes;
    }

    public void setClasses(FitnessClass[] classes) {
        this.classes = classes;
    }

    public int getNumClasses() {
        return numClasses;
    }

    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }

    /**
     * Checks if there's a time conflict for a member
     * @param member
     * @param time
     * @return true if there is a time conflict, false otherwise
     */
    public boolean checkTimeConflict(Member member, Time time) {

        // loop through all fitnessClasses in schedule. check if there is an instance of member with this time.

        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass.getMembers().find(member) != NOT_FOUND && fitnessClass.getTime().equals(time)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Find the index of a fitness class
     * @param fitnessClass
     * @return the found index
     */
    public int find (FitnessClass fitnessClass) {

        for (int i = 0; i < classes.length; i++) {
            if (classes[i].equals(fitnessClass)) {
                return i;
            }
        }
        return NOT_FOUND;

    }

    /**
     * Checks if a fitness class is in the schedule
     * @param fitnessClass
     * @return true if the fitness class is found, false otherwise
     */
    public boolean containsClass(FitnessClass fitnessClass) {

        return find(fitnessClass) != NOT_FOUND;

    }

    /**
     * Reads fitness class information from a file and populates the classes list
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {
        FitnessClass[] tempClasses = new FitnessClass[10];
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    Offer classInfo = Offer.valueOf(parts[0].toUpperCase());
                    Instructor instructor = Instructor.valueOf(parts[1].toUpperCase());
                    Time time = Time.valueOf(parts[2].toUpperCase());
                    Location studio = Location.valueOf(parts[3].toUpperCase());

                    FitnessClass fitnessClass = new FitnessClass(classInfo, instructor, studio, time);

                    if (numClasses == tempClasses.length) {
                        FitnessClass[] newArray = new FitnessClass[tempClasses.length * 2];
                        System.arraycopy(tempClasses, 0, newArray, 0, numClasses);
                        tempClasses = newArray;
                    }
                    tempClasses[numClasses++] = fitnessClass;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        classes = new FitnessClass[numClasses];
        System.arraycopy(tempClasses, 0, classes, 0, numClasses);
    }

    /**
     * Prints the schedule of fitness classes
     */
    public void printSchedule() {
        System.out.println("-fitness classes-");
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass != null) {
                System.out.println(fitnessClass);
                if (!fitnessClass.getMembers().isEmpty()) {
                    System.out.println("[Attendees]");
                    fitnessClass.getMembers().printMembers();
                }
                if (!fitnessClass.getGuests().isEmpty()) {
                    System.out.println("[Guests]");
                    fitnessClass.getGuests().printMembers();
                }
            }
        }
        System.out.println("-end of class list-");
    }

    /**
     * Prints the loaded fitness classes
     */
    public void printLoadedClasses() {
        System.out.println("-Fitness classes loaded-");
        for (int i = 0; i < numClasses; i++) {
            System.out.println(classes[i]);
        }
        System.out.println("end of class list.");
    }

    public static void main(String [] args) throws IOException {
        Schedule schedule = new Schedule();

        File file = new File("classSchedule.txt");
        schedule.load(file);
        schedule.printLoadedClasses();

    }
}
