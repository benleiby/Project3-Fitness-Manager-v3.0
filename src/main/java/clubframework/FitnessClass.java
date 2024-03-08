package clubframework;

/**
 * Represents a fitness class, storing information such as class type, instructor,
 * studio location, and time.
 * @author Matteus Coste
 * @author Benjamin Leiby
 */
public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;


    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }

    public FitnessClass(Offer classInfo, Instructor instructor, Location studio) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
    }

    /**
     *  Checks if two FitnessClass objects are equal based on their classInfo,
     *  instructor, and studio attributes.
     * @param o
     * @return true if the classInfo, instructor, and studio of both FitnessClass
     * objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        FitnessClass other = (FitnessClass) o;
        return this.classInfo.equals(other.getClassInfo())
                && this.instructor.equals(other.getInstructor())
                && this.studio.equals(other.getStudio());

    }

    public Offer getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Offer classInfo) {
        this.classInfo = classInfo;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Location getStudio() {
        return studio;
    }

    public void setStudio(Location studio) {
        this.studio = studio;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public MemberList getMembers() {
        return members;
    }

    public void setMembers(MemberList members) {
        this.members = members;
    }

    public MemberList getGuests() {
        return guests;
    }

    public void setGuests(MemberList guests) {
        this.guests = guests;
    }

    /**
     * Format: [classInfo] - [instructor], [hour]:[minute], [studio]
     * @return string representation of the time
     */
    public String toString() {
        return  classInfo + " - " + instructor + ", " + time.getHour() + ":"
                + String.format("%02d", time.getMinute()) + ", " + studio;
    }

    public static void main (String [] args) {

        FitnessClass c1 = new FitnessClass(Offer.PILATES, Instructor.DAVIS, Location.BRIDGEWATER, Time.AFTERNOON);
        FitnessClass c2 = new FitnessClass(Offer.PILATES, Instructor.DAVIS, Location.BRIDGEWATER);

        System.out.println(c1.equals(c2));
        System.out.println("yay");


    }
}
