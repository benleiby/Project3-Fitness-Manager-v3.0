package clubframework;

/**
 * Template for all available memberships.
 * @author Benjamin Leiby
 */
public class Member implements Comparable <Member> {

    private Profile profile;
    private Date expire;
    private Location homeStudio;

    /**
     * Default constructor.
     */
    public Member() {}

    /**
     * Overloaded constructor. Initializes all instance variables.
     * @param profile Member profile.
     * @param expire Date of membership expiration.
     * @param homeStudio Member home studio.
     */
    public Member(Profile profile, Date expire, Location homeStudio) {

        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;

    }

    /**
     * Copy constructor.
     * @param other Member to copy to this object.
     */
    public Member(Member other) {

        this.profile = other.profile;
        this.expire = other.expire;
        this.homeStudio = other.homeStudio;

    }

    /**
     * Checks if the expiration date has passed.
     * @return true if the membership is expired. If not, return false.
     */
    public boolean isExpired() {
        return expire.isBeforeToday();
    }

    /**
     * Compares the members first by county, then zip, then profile, and finally by expiration date.
     * @param m The other Member to compare with this one.
     * @return Negative int if this < m. Return a positive int if this > m. Return 0 if equal.
     */
    @Override
    public int compareTo(Member m) {

        if (this.homeStudio.getCounty().compareTo(m.homeStudio.getCounty()) != 0) {
            return this.homeStudio.getCounty().compareTo(m.homeStudio.getCounty());
        }
        if (this.homeStudio.getZip().compareTo(m.homeStudio.getZip()) != 0) {
            return this.homeStudio.getZip().compareTo(m.homeStudio.getZip());
        }
        if (this.profile.compareTo(m.profile) != 0) {
            return this.profile.compareTo(m.profile);
        }
        if (this.expire.compareTo(m.expire) != 0) {
            return this.expire.compareTo(m.expire);
        }
        return 0;

    }

    /**
     * Uses compareTo method to see if this Member equals another Member.
     * @param o The other Member to compare with this one.
     * @return True if this and o are equal. If not, return false.
     */
    @Override
    public boolean equals(Object o) {

        if (o.getClass() != Member.class) { return false; }
        Member member = (Member) o;
        return (this.compareTo(member) == 0);

    }

    /**
     * Format: [First Name]:[Last Name]:[Date of Birth], Membership [expiration status] [expiration date],
     * Location: [Location], [Zip Code], [County]
     * @return The String representation of this Member's info.
     */
    @Override
    public String toString() {

        String expMsg;
        if (this.isExpired()) { expMsg = ", Membership expired "; }
        else { expMsg = ", Membership expires "; }

        return profile.toString() + expMsg + expire.toString()
        + ", Location: " + homeStudio + ", " + homeStudio.getZip()
        + ", " + homeStudio.getCounty().toUpperCase();

    }

    /**
     * Calculates the next due amount.
     * @return The double value of the next due amount.
     */
    public double bill() {
        return 0.00;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Location getHomeStudio() {
        return homeStudio;
    }

    public void setHomeStudio(Location homeStudio) {
        this.homeStudio = homeStudio;
    }

}