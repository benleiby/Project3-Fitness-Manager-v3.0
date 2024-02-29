package clubframework;

/**
 * Container for unique Member identification info.
 * @author Benjamin Leiby
 */
public class Profile implements Comparable <Profile> {

    private String fname;
    private String lname;
    private Date dob;

    /**
     * Default constructor.
     */
    public Profile() {}

    /**
     * Overloaded constructor. Assigns all variables.
     * @param fname Member first name.
     * @param lname Member last name.
     * @param dob Member date of birth.
     */
    public Profile(String fname, String lname, Date dob) {

        this.fname = fname;
        this.lname = lname;
        this.dob = dob;

    }

    /**
     * Copy constructor.
     * @param other Profile to copy to this object.
     */
    public Profile(Profile other) {

        this.fname = other.fname;
        this.lname = other.lname;
        this.dob = other.dob;

    }

    /**
     * Compares the profiles first based on last name, then first name, and finally date of birth.
     * @param p The other Profile to compare with this one.
     * @return Negative int if this < p. Return a positive int if this > p. Return 0 if equal.
     */
    @Override
    public int compareTo(Profile p) {

        if (this.lname.compareTo(p.lname) != 0) {
            return this.lname.compareToIgnoreCase(p.lname);
        }
        if (this.fname.compareTo(p.fname) != 0) {
            return this.fname.compareToIgnoreCase(p.fname);
        }
        if (this.dob.compareTo(p.dob) != 0) {
            return this.dob.compareTo(p.dob);
        }
        return 0;

    }

    /**
     * Uses compareTo method to see if this Profile equals another Profile.
     * @param o The other Profile to compare with this one.
     * @return True if this and o are equal. If not, return false.
     */
    @Override
    public boolean equals(Object o) {

        if (o.getClass() != Profile.class) {
            return false;
        }
        Profile profile = (Profile) o;
        return (this.compareTo(profile) == 0);

    }

    /**
     * Format: [First Name]:[Last Name]:[Date of Birth]
     * @return The String representation of this Profile.
     */
    @Override
    public String toString() {
        return fname + ":" + lname + ":" + dob.toString();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}