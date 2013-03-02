package afstudeerproject.Basic;

import java.io.Serializable;

public class Vector implements Serializable {

    static final long serialVersionUID = 1L;
    public double x, y, z;
    public final double epsilon = 0.0000001;

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public String toVTK() {
        return x + " " + y + " " + z;
    }

    public Vector(double[] v) {
        this.x = v[0];
        this.y = v[1];
        this.z = v[2];
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector) {
            Vector o = (Vector) other;
            return o.x == this.x && o.y == this.y && o.z == this.z;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    public double cosSimilarity(Vector other) {
        return 1 - (this.angle(other) / Math.PI);
    }

    public double angle(Vector other) {
        return Math.acos(Vector.dot(this, other) / (this.length() * other.length()));
    }

    public Vector sameDomain(Vector domain) {
        return Vector.mul(this, Vector.dot(domain, this) < 0 ? -1 : 1);
    }

    public void mulInPlace(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }

    //Static method declaration
    public static Vector add(Vector c1, Vector c2) {
        return new Vector(c1.x + c2.x, c1.y + c2.y, c1.z + c2.z);
    }

    public static Vector sub(Vector c1, Vector c2) {
        return new Vector(c1.x - c2.x, c1.y - c2.y, c1.z - c2.z);
    }

    public static Vector div(Vector c, double s) {
        if (s == 0) {
            throw new ArithmeticException("Division by zero");
        } else {
            return new Vector(c.x / s, c.y / s, c.z / s);
        }
    }

    public static Vector mul(Vector c, double s) {
        return new Vector(c.x * s, c.y * s, c.z * s);
    }

    public static double dist(Vector c1, Vector c2) {
        return Math.sqrt(Math.pow(c2.x - c1.x, 2) + Math.pow(c2.y - c1.y, 2) + Math.pow(c2.z - c1.z, 2));
    }

    public static double dot(Vector a, Vector b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector normalized() {//returns the normalized version of this vector
        if (this.length() == 0) {
            throw new ArithmeticException("Normalizing vector of length 0");
        } else {
            Vector result = Vector.div(this, this.length());
            if(result.length() < 0.9999 || result.length() > 1.0001){
                System.err.println("culprit " + this);
                throw new ArithmeticException("Normalizing vector of very small length");
            }else{
                return result;
            }
        }
    }
}
