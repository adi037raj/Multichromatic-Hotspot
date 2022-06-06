package multichromatic;







public class Point {
    int x, y, z;
    int color;

    Point(int x, int y, int z, int color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    Point() {
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.color = -1;
    }

    void printer() {
        System.out.printf("x: %d, y: %d, z: %d, color: %d\n", x, y, z, color);
        // System.out.print(this.x);
        // System.out.print(" ");
        // System.out.print(this.y);
        // System.out.print(" ");
        // System.out.print(this.z);
        // System.out.print(" ");
        // System.out.print(this.color);
        // System.out.println();
    }
}