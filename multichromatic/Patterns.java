package multichromatic;

import java.util.regex.Pattern;

public class Patterns {
    Hotspot_Point center;
    int radius;
    int height;
    double llr;
    double p_value;

    Patterns(Hotspot_Point center, int radius, int height, double llr) {
        this.center = center;
        this.radius = radius;
        this.height = height;
        this.llr = llr;
        this.p_value=1;
    }
    Patterns(Patterns p)    // Copy Constructor
    {
    	this.center=p.center;
    	this.llr=p.llr;
    	this.height=p.height;
    	this.radius=p.radius;
    	this.p_value=p.p_value;
    }
    

    void printer() {
        center.printer();
        System.out.printf("r: %d, ht: %d, llr: %f %f\n", radius, height, llr,p_value);
    }
}