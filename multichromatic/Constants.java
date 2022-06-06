package multichromatic;


import java.util.ArrayList;

public class Constants {
    static String fileName = "//home//aaditya//Downloads//dataset_2Multichromatic.csv";
    static String outputFile = "output_2_Multichromatic_.txt";
    static int a = 25;          // max x limit
    static int b = 25;            // max y limit
    static int weeks=8;
    static int c = 5376;          // max time limit (hours * slot * days * weeks)
    static int dayCount = 7*weeks;          //     total days  
    static int dayTimeSlot=96;      // No. of slots in a day
    static int centerThreshold = 2;    // experimental
    static int htMin = 4;                  //  Not in use currently
    static int htMax = 12;                // NOt in use currently
    static int rMin = 1;                
    static int rMax = 12;             // iterating every possible radius 
    static int logThreshold_daily =  60000;       // experimental value to be decided
    static int logThreshold_weekly = 4000;
    static int logThreshold_weekends=10000;
    static int logThreshold_weekdays=40000;
    static double rTol = 0.1f; // percentage tolerance in radius for shifted hotspots
    static double hTol = 0.1f;
    static ArrayList<Cummulative_Grid> cummulativeGridList=new ArrayList<>(dayCount+1);
    static int monte_carlo_simus=500;
    static double p_value_threshold =1;
    static int total_no_of_points =0;
    static int overlappingPercentage=40;
    static int numberOfColors=10;
    static int grid[][][][];
    static Boolean colorUnused[]=new Boolean[numberOfColors];
    static int []totalPointsbyColor=new int[numberOfColors];
    static double colorPercentage=.20;
    static double colorChangePercentage=.60;
    final static int slidingWindowCD=7;
    static int changeDetectionThreshold=200;
    // percentage tolerance in height for shifted hotspots
}