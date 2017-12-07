package application;

import javafx.scene.image.Image;
/*  A class able to transform a (latitude, longitude) into pixel coordinates for a large number of projections.
 *  As the name says, the background image is assumed to be a map of the world.
 */
public class WorldMap {
    public enum Projection {
         MERCATOR, ECKERT_IV
    }

    private Projection proj;
    private double middleMeridian; // O for Greenwich，子午线
    // ------- Hufnagel projections parameters
    private double[] paramAngleTable;
    private double[] latitudeTable;
    private double[] yTable;
    private final int tabSize = 101;
    private double A;
    private double B;
    private double C;
    private double K;
    private final double epsilon = 0.001;  // Precision
    private double psiMax;
    private double pixelWidth;
    // ------- Winkel Tripel projection parameters
    private final double phi1 = Math.acos(2 / Math.PI);
    // ---------------------------------------
    private double pixelHeight;
    private double xcoef;
    private double ycoef;

    WorldMap(Image background, Projection proj, double middleMeridian) {
        double alpha = 0;
        double[] ref;

        this.proj = proj;
        this.pixelWidth = background.getWidth();
        this.pixelHeight = background.getHeight();
        this.middleMeridian = middleMeridian;
        switch (proj) {
            case ECKERT_IV: this.A = 1.0; this.B = 0.0; this.psiMax = Math.PI / 4; alpha = 2.0;
                break;
            default:
                break;
        }
        switch (proj) {
                case ECKERT_IV:
                // Compute C
                this.C = Math.sqrt(alpha * Math.sin(psiMax) * Math.sqrt((1 + this.A * Math.cos(2 * psiMax) + this.B * Math.cos(4 * psiMax))
                        / (1 + this.A + this.B)));
                initHufnagel();
                // Compute the largest x value (ref[0])，It's half the width of the image
                ref = getXY(0, 180 + middleMeridian);
                xcoef = pixelWidth / ref[0] / 2;
                // Compute the largest y value (ref[1])，It's half the height of the image
                ref = getXY(90, 0);
                ycoef = pixelHeight / ref[1] / 2;
                break;
            case MERCATOR:
                xcoef = 1.0;
                ycoef = 1.0;
                break;
            default:
                break;
        }
    }

    private void initHufnagel() {
        double psi; // Radians here
        double phi; // Radians here
        double r;
        double y;
        paramAngleTable = new double[tabSize];
        latitudeTable = new double[tabSize];
        yTable = new double[tabSize];

        this.K = (2 * Math.sqrt(Math.PI)) / Math.sqrt(2 * psiMax + (1 + A - B / 2) * Math.sin(2 * psiMax)
                + ((A + B) / 2) * Math.sin(4 * psiMax) + (B / 2) * Math.sin(6 * psiMax)); // From equation 3
        for (int i = 0; i < tabSize; i++) {
            psi = (i * psiMax) / (tabSize - 1);
            if (i == 0) {
                phi = 0;
            } else if (i == tabSize - 1) {
                phi = Math.PI / 2.0;
            } else {
                phi = Math.asin((2 * psi + (1 + A - B / 2) * Math.sin(2 * psi) + ((A + B) / 2) * Math.sin(4 * psi)
                        + (B / 2) * Math.sin(6 * psi)) * (K * K) / 4 / Math.PI);
                // From equation 3
            }
            r = Math.sqrt(1 + A * Math.cos(2 * psi) + B * Math.cos(4 * psi)); // Equation 1
            y = (K / C) * r * Math.sin(psi); // Equation 5
            if (i > 0) {
                if ((y < yTable[i - 1])
                        || (phi < latitudeTable[i - 1])) {
                    // folding graticule exception
                    throw new IllegalStateException();
                }
            }
            paramAngleTable[i] = psi;
            latitudeTable[i] = phi;
            yTable[i] = y;
        }
    }

    private double findHufnagelSeed(double phi) {
        int iMin = 0;
        int iMax = tabSize;
        int iMid;
        boolean loop = true;
        double phi1;
        double phi2;
        double weight;
        double psi0;
        double psi1;
        double psi2;

        while (loop) {
            iMid = (iMin + iMax) / 2;
            if (iMid == iMin) {
                loop = false;
            } else if (Math.abs(phi) > latitudeTable[iMid]) {
                iMin = iMid;
            } else {
                iMax = iMid;
            }
        }
        phi1 = latitudeTable[iMin];
        phi2 = latitudeTable[iMin + 1];
        weight = (Math.abs(phi) - phi1) / (phi2 - -phi1);
        psi1 = paramAngleTable[iMin];
        psi2 = paramAngleTable[iMin + 1];
        psi0 = weight * (psi2 - psi1) + psi1;
        return (phi < 0 ? -1 * psi0 : psi0);
    }

    private double Neville(double z, int i, int j, double[] x, double[] y) {
        if (i == j) {
            return y[i];
        } else {
            return ((z - x[j]) * Neville(z, i, j - 1, x, y) - (z - x[i]) * Neville(z, i + 1, j, x, y))/ (x[i] - x[j]);
        }
    }

    private double sinc(double x) {
        if (x == 0) {
            return 1.0;
        }
        return Math.sin(x) / x;
    }

    public double[] getWinkelXY(double lambda, double phi) {
        double[] xy = new double[2];
        double alpha = Math.acos(Math.cos(phi) * Math.cos(lambda/2.0));
        xy[0] = 0.5 * (lambda * Math.cos(phi1) + 2 * Math.cos(phi) * Math.sin(lambda/2) / sinc(alpha));
        xy[1] = 0.5 * (phi + Math.sin(phi) / sinc(alpha));
        return xy;
    }

    public double[] getHufnagelXY(double lambda, double phi) {
        double psi = findHufnagelSeed(phi);
        double deltaPsiNumerator;
        double deltaPsiDenominator;
        double deltaPsi;
        double r;
        boolean loop = true;
        int cnt = 0;
        while (loop) {
            deltaPsiNumerator = (K * K / 4) * (2 * psi + (1 + A - B / 2) * Math.sin(2*psi)
                        + ((A + B) / 2) * Math.sin(4*psi) + (B / 2) * Math.sin(6*psi))- Math.PI * Math.sin(phi);
            // System.err.println(Math.abs(deltaPsiNumerator));// Equation 8
            if (Math.abs(deltaPsiNumerator) < epsilon) {
                loop = false;
            } else {
                deltaPsiDenominator = (K * K / 2) * (1 + (1 + A - B / 2) * Math.cos(2*psi)
                      + (A + B) * Math.cos(4*psi) + ((3 * B) / 2) * Math.cos(6*psi)); // Equation 8
                deltaPsi = deltaPsiNumerator / deltaPsiDenominator;
                psi -= deltaPsi;
            }
            cnt++;
            if (cnt > 20) {
              loop = false;
            }
        }
        r =  Math.sqrt(1 + A * Math.cos(2*psi) + B * Math.cos(4*psi)); // Equation 1
        double[] xy = new double[2];
        xy[0] =  (K * C) * lambda * r * Math.cos(psi); // Equation 5
        xy[1] =  (K / C) * r * Math.sin(psi); // Equation 5
        return xy;
    }

    private double[] getMercatorXY(double lambda, double phi) {
        double[] coordinates = new double[2];
        coordinates[0] = (pixelWidth / 2.0 / Math.PI) * lambda;
        coordinates[1] = (pixelWidth / 2.0 / Math.PI) * Math.log(Math.tan(Math.PI / 4.0 + (phi / 2.0)));
        return coordinates;
    }

    private double[] getXY(double latitude, double longitude) {
        double   new_longitude;

        new_longitude = (longitude - middleMeridian);
        if (new_longitude < -180) {
            new_longitude += 360;
        }
        if (new_longitude > 180) {
            new_longitude -= 360;
        }
        switch (proj) {
            case ECKERT_IV:
            case MERCATOR:
               return getMercatorXY(new_longitude * Math.PI / 180.0,latitude * Math.PI / 180.0);
            default:
                return null;
        }
    }

    public int[] imgxy(double latitude,double longitude) {
        double[] xy = getXY(latitude, longitude);
        if (xy == null) {
            return null;
        }
        int[] imgcoord = new int[2];
        imgcoord[0] = (int)(Math.round(xy[0] * xcoef) + Math.round(pixelWidth/2));
        imgcoord[1] = (int)(Math.round(pixelHeight/2) - Math.round(xy[1] * ycoef));
        return imgcoord;
    }
}
