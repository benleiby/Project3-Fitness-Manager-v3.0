package clubframework;

/**
 * Basic membership. numClasses attended is recorded.
 * $10.00 charge for every class attended over 4 classes.
 * Does not have guest pass privileges.
 * Can only attend classes at homeStudio Location.
 * @author Ben Leiby
 */
public class Basic extends Member {

    private int numClasses;

    /**
     * Calls Member(). Initializes numClass to 0.
     * @param profile of member.
     * @param expire Date membership expires.
     * @param homeStudio Location of home studio.
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        numClasses = 0;
    }

    /**
     * Format: [Profile.toString()],(Basic) number of classes attended: [numClasses]
     * @return string representation of the num of classes attended
     */
    @Override
    public String toString() {
        return super.toString() + ",(Basic) number of classes attended: " + numClasses;
    }

    /**
     * Calculates and returns the membership fee for Basic membership
     * @return a double value representing the completed calculation
     */
    @Override
    public double bill () {

        if (numClasses > 4) {
            return 39.99 + 10.00 * (numClasses - 4);
        }
        return 39.99;

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
