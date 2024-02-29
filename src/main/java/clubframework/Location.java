package clubframework;

/**
 * Location information cannot be altered.
 * This constant contains the studio COUNTY and ZIP code.
 * @author Benjamin Leiby
 */
public enum Location {

    BRIDGEWATER ("08807", "Somerset"),
    EDISON ("08837", "Middlesex"),
    FRANKLIN ("08873", "Somerset"),
    PISCATAWAY ("08854", "Middlesex"),
    SOMERVILLE ("08876", "Somerset");

    private final String ZIP;
    private final String COUNTY;

    Location(String zip, String county) {
        this.ZIP = zip;
        this.COUNTY = county;
    }

    public String getZip() {
        return ZIP;
    }

    public String getCounty() {
        return COUNTY;
    }

}