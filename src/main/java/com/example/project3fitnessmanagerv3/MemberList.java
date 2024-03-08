package com.example.project3fitnessmanagerv3;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Array-based implementation of a resizable array.
 * Array length only grows by 4. Array cannot shrink.
 * @author Benjamin Leiby
 * @author Matteus Coste
 */
public class MemberList {

    private Member [] members;
    private int size; // Represents number of elements currently in the array.

    private static final int LENGTH_FACTOR  = 4;
    private static final int NOT_FOUND = -1;

    /**
     * Overloaded constructor. Initializes all instance variables.
     */
    public MemberList() {

        members = new Member [LENGTH_FACTOR];
        size = 0;

    }

    /**
     * Copy constructor.
     * @param other MemberList to copy to this object.
     */
    public MemberList(MemberList other) {

        this.members = other.members;
        this.size = other.size;

    }

    /**
     * Based on Profile information, gets the index of a specified Member in the memberList.
     * @param member To find the index of.
     * @return int Index of member in the list.
     */
    public int find(Member member) {

        for (int i = 0; i < size; i++) {
            if (members[i].getProfile().equals(member.getProfile())) { return i; }
        }

        return NOT_FOUND;

    }

    /**
     * Increase the capacity of members by 4.
     */
    private void grow() {

        Member [] nextArray = new Member [members.length + LENGTH_FACTOR];

        for (int i = 0; i < members.length; i++) {
            nextArray[i] = members[i];
        }

        members = nextArray;

    }

    /**
     * Adds a member to the list if they are not already in it.
     * Grow the array if size > array length.
     * @param member to add to the list.
     * @return True if the addition is successful. If not, return false.
     */
    public boolean add(Member member) {

        if (find(member) != NOT_FOUND) { return false; }

        size++;
        if (size > members.length) {
            grow();
        }

        members[size - 1] = member;
        return true;

    }

    /**
     * Removes a member from the list if they are already in it.
     * @param member to remove from the list.
     * @return True if removal is successful. If not, return false.
     */
    public boolean remove (Member member) {

        int memberIndex = find(member);
        if(memberIndex == NOT_FOUND) { return false; }
        members[memberIndex] = null;

        for (int i = memberIndex + 1; i < size; i++) {
            members[i - 1] = members[i];
        }

        members[size - 1] = null; // sets last duplicate element to null
        size--;
        return true;

    }

     /**
     * Read member information from a file and populates the member list
     * @param file
     * @throws IOException
     */
     public void load(File file) throws IOException {

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 6) {
                    char memType = parts[0].charAt(0);
                    String fname = parts[1];
                    String lname = parts[2];
                    Date dob = new Date(parts[3]);
                    Date memEndDate = new Date(parts[4]);
                    Location location = Location.valueOf(parts[5]);

                    Profile profile = new Profile(fname, lname, dob);
                    Member member;

                    switch (memType) {
                        case 'B':
                            member = new Basic(profile, memEndDate, location);
                            break;
                        case 'F':
                            member = new Family(profile, memEndDate, location);
                            break;
                        case 'P':
                            member = new Premium(profile, memEndDate, location);
                            break;
                        default:
                            System.out.println("Invalid input line: " + line);
                            continue;
                    }
                    add(member);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Print the list of members. NEEDS TO BE DELETED / EDITED BEFORE SUBMISSION.
     */
    public void printMembers() {
        for (Member member : members) {
            if (member != null) {
                System.out.println(member.toString());
            }
        }
    }

    /**
     * Print the list of members sorted by county. NEEDS TO BE DELETED / EDITED BEFORE SUBMISSION.
     */
    public void printByCounty () {

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (members[i].compareTo(members[j]) > 0) {
                    swap(i,j);
                }
            }
        }
        System.out.println("-list of members sorted by county, then zipcode-");
        printMembers();
        System.out.println("-end of list-");

    }

    /**
     * Print the list of members sorted by member profile. NEEDS TO BE DELETED / EDITED BEFORE SUBMISSION.
     */
    public void printByMember() {

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (members[i].getProfile().compareTo(members[j].getProfile()) > 0) {
                    swap(i,j);
                }
            }
        }
        System.out.println("-list of members sorted by member profiles-");
        printMembers();
        System.out.println("-end of list-");

    }

    /**
     * Helper method for sorting.
     * Swaps two elements in an array given the element's indices.
     * @param index1 Index of first element.
     * @param index2 Index of second element.
     */
    private void swap (int index1, int index2) {

        Member temp = members[index1];
        members[index1] = members[index2];
        members[index2] = temp;

    }

    /**
     * Print the list of members with fee amounts. NEEDS TO BE DELETED / EDITED BEFORE SUBMISSION.
     */
    public void printFees() {
        System.out.println("List of members with next dues.");
        for (int i = 0; i < size; i++) {
            Member member = members[i];
            System.out.println(member.toString() + " [next due: " + member.bill() + "]");
        }
    }

    public Member [] getMembers() {
        return members;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public static void main (String [] args) {

        MemberList testList = new MemberList();

        Profile p1, p2, p3, p4, p5;
        Member m1, m2, m3, m4, m5;

        p1 = new Profile("Benjamin", "Leiby", new Date("09/05/2003"));
        p2 = new Profile("Cenjamin", "Leiby", new Date("09/06/2003"));
        p3 = new Profile("Denjamin", "Leiby", new Date("09/07/2003"));
        p4 = new Profile("Eenjamin", "Leiby", new Date("09/08/2003"));
        p5 = new Profile("Fenjamin", "Leiby", new Date("09/09/2003"));

        m1 = new Basic(p1, Location.EDISON);
        m2 = new Premium(p2, Location.PISCATAWAY);
        m3 = new Family(p3, Location.BRIDGEWATER);
        m4 = new Basic(p4, Location.FRANKLIN);
        m5 = new Premium(p5, Location.SOMERVILLE);

        testList.add(m1);
        testList.add(m2);
        testList.add(m3);
        testList.add(m4);
        testList.add(m5);

        testList.printMembers();

        testList.remove(m5);

        testList.printMembers();

        testList.printFees();

    }

}
