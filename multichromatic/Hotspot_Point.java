package multichromatic;

import java.util.ArrayList;

public class Hotspot_Point {
int x,y,start,end;
int color;
public Hotspot_Point(int x,int y,int start,int end,int color) {
	// TODO Auto-generated constructor stub
	
	this.x=x;
	this.y=y;
	this.start=start;
	this.end=end;
	this.color=color;
	
}

void printer() {
    System.out.printf("x: %d, y: %d, start: %d, end :%d, color: %d\n", x, y, start,end, color);
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