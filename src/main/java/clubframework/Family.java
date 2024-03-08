package clubframework;

/**
 * Family membership.
 * Membership includes 1 guest pass for the home studio.
 * May attend classes at all locations.
 * Billed every 3 months.
 * @author Benjamin Leiby
 */
public class Family extends Member {

    private boolean guest;

    private static final double RATE = 49.99;
    private static final int BILLING_PERIOD = 3;

    /**
     * Default constructor.
     */
    public Family() {}

    /**
     * Overloaded constructor. Initializes all instance variables.
     * @param profile Member profile.
     * @param expire Date of membership expiration.
     * @param homeStudio Member home studio.
     */
    public Family(Profile profile, Date expire, Location homeStudio) {

        super(profile, expire, homeStudio);
        guest = true;

    }

    /**
     * Overloaded constructor. Initializes all instance variables.
     * Sets membership to expire in 3 months.
     * @param profile Member profile.
     * @param homeStudio Member home studio.
     */
    public Family(Profile profile, Location homeStudio) {

        super(profile, homeStudio);

        Date expire = new Date();
        expire.setToTimeFromToday(BILLING_PERIOD, 0);
        super.setExpire(expire);

        guest = true; // true if member has a guest pass available.

    }

    /**
     * Copy constructor.
     * @param other Family membership to copy to this object.
     */
    public Family(Family other) {

        this.setProfile(other.getProfile());
        this.setExpire(other.getExpire());
        this.setHomeStudio(other.getHomeStudio());
        this.guest = other.guest;

    }

    /**
     * Format: [Profile.toString()],(Family) guest-pass remaining: [guestPassesRemaining]
     * @return The String representation of this Family membership.
     */
    @Override
    public String toString() {

        if (!this.isExpired()) {
            return super.toString() + ",(Family) guest-pass remaining: " + (guest ? 1 : 0);
        }

        return super.toString() + ",(Family) guest-pass remaining: not eligible";

    }

    /**
     * Calculates the next due amount.
     * @return The double value of the next due amount.
     */
    @Override
    public double bill () {
        return RATE * BILLING_PERIOD;
    }

    public boolean hasGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

}
