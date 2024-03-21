# Project Overview:

The Project3 Fitness Manager v3.0 is a fitness management application aimed at helping fitness centers 
and gym owners manage their classes, members, and attendance efficiently. The project aims to provide a user-friendly 
interface for administrators to organize classes, track member attendance, and manage membership details.

# Installation Instructions:

To set up and run the project:

1. Clone the repository from GitHub: Project3 Fitness Manager v3.0.
2. Ensure you have Java Development Kit (JDK) installed on your system.
3. Open the project in a Java IDE of your choice.
4. Build and run the project.

# Bug Description: 

There is a known bug related to the removal of guests from the attendance list. Users must follow a specific order when removing 
guests: the last guest added must be removed first. For instance, if a premium member has three guests on the attendance sheet, users 
must remove the third guest first. Similarly, if two guests of a premium member are present, users must remove the second guest first.

# Class Attendance Tab Usage: 

To interact with the class attendance tab:

1. Navigate to the "Class Attendance" tab in the application.
2. Select a fitness class from the list displayed, it will be highlighted.
3. View the list of members and guests currently attending the selected class.
4. To remove a member or guest from the attendance list, select one and follow the specific order mentioned in the bug description.
5. Use the provided functionalities to add new members or guests to the attendance list as needed.
