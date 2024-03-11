package com.example.project3fitnessmanagerv3;

/**
 * Premium membership.
 * Membership includes 3 guest passes for the home studio.
 * May attend classes at all locations.
 * Billed annually with 1 month free.
 * @author Benjamin Leiby
 */
public class Premium extends Member {

    private int guestPass;

    private static final int NUM_PASSES = 3;
    private static final int BILLING_PERIOD = 12;
    private static final double RATE = 59.99;

    /**
     * Default constructor.
     */
    public Premium() {}

    /**
     * Overloaded constructor. Initializes all instance variables.
     * @param profile Member profile.
     * @param expire Date of membership expiration.
     * @param homeStudio Member home studio.
     */
    public Premium(Profile profile, Date expire, Location homeStudio) {

        super(profile, expire, homeStudio);
        guestPass = NUM_PASSES;

    }

    /**
     * Overloaded constructor. Initializes all instance variables.
     * Sets membership to expire in 12 months.
     * @param profile Member profile.
     * @param homeStudio Member home studio.
     */
    public Premium(Profile profile, Location homeStudio) {

        super(profile, homeStudio);

        Date expire = new Date();
        expire.setToTimeFromToday(0, 1);
        super.setExpire(expire);

        guestPass = NUM_PASSES;

    }

    /**
     * Copy constructor.
     * @param other Premium member to copy to this object.
     */
    public Premium(Premium other) {

        this.setProfile(other.getProfile());
        this.setExpire(other.getExpire());
        this.setHomeStudio(other.getHomeStudio());
        this.guestPass = other.guestPass;

    }
    /**
     * Format: [Profile.toString()], (Premium) guest-pass remaining: [number of guest passes]
     * @return String representation of this Premium member.
     */
    @Override
    public String toString() {

        if (!this.isExpired()) {
            return super.toString() + ",(Premium) guest-pass remaining: " + guestPass;
        }

        return super.toString() + ",(Premium) guest-pass remaining: not eligible";

    }

    @Override
    public String getInfo() {

        if (!this.isExpired()) {
            return "Guest-pass: " + guestPass;
        }

        return "Guest-pass: not eligible";

    }

    /**
     * Calculates the next due amount.
     * @return The double value of the next due amount.
     */
    @Override
    public double bill () {
        return (BILLING_PERIOD - 1) * RATE;
    }

    public int getGuestPass() {
        return guestPass;
    }

    public void setGuestPass(int guestPass) {
        this.guestPass = guestPass;
    }

}
