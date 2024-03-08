package clubframework;

/**
 * Basic membership. numClasses attended is recorded.
 * Does not have guest pass privileges.
 * Can only attend classes at homeStudio Location.
 * Additional charges if attends extra classes.
 * Billed every month.
 * @author Ben Leiby
 */
public class Basic extends Member {

    private int numClasses;

    private final static double RATE = 39.99;
    private final static int FREE_CLASSES = 4;
    private final static double EXTRA_CLASS_CHARGE = 10.99;
    private final static int BILLING_PERIOD = 1;


    /**
     * Default constructor.
     */
    public Basic() {}

    /**
     * Overloaded constructor. Initializes all instance variables.
     * @param profile Member profile.
     * @param expire Date of membership expiration.
     * @param homeStudio Member home studio.
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {

        super(profile, expire, homeStudio);
        numClasses = 0;

    }

    /**
     * Overloaded constructor. Initializes all instance variables.
     * Sets membership to expire in 1 month.
     * @param profile Member profile.
     * @param homeStudio Member home studio.
     */
    public Basic(Profile profile, Location homeStudio) {

        super(profile, homeStudio);

        Date expire = new Date();
        expire.setToTimeFromToday(BILLING_PERIOD, 0);
        super.setExpire(expire);

        numClasses = 0;

    }

    /**
     * Copy constructor.
     * @param other Basic member to copy to this object.
     */
    public Basic(Basic other) {

        this.setProfile(other.getProfile());
        this.setExpire(other.getExpire());
        this.setHomeStudio(other.getHomeStudio());
        this.numClasses = other.numClasses;

    }

    /**
     * Gets String of format: [Profile.toString()],(Basic) number of classes attended: [numClasses]
     * @return The String representation of the member info.
     */
    @Override
    public String toString() {
        return super.toString() + ",(Basic) number of classes attended: " + numClasses;
    }

    /**
     * Calculates the next due amount.
     * @return The double value of the next due amount.
     */
    @Override
    public double bill () {

        if (numClasses > FREE_CLASSES) {
            return RATE + (EXTRA_CLASS_CHARGE * (numClasses - FREE_CLASSES));
        }
        else
            return RATE;

    }

    /**
     * Gets number of classes attended.
     * @return int number of classes attended.
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Sets number of classes attended.
     * @param numClasses attended.
     */
    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }

}
