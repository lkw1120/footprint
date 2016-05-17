package footprint.footprint;

/**
 *
 */
public class LBRS {
    protected int id = 0;
    protected double latitude = 0.0f;
    protected double longitude = 0.0f;
    protected int cnt = 0;

    public LBRS(int id, double latitude, double longitude, int cnt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cnt = cnt;
    }
}
