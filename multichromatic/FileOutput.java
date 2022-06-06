package multichromatic;

import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;

public class FileOutput{
    static String outputFile = Constants.outputFile;

    static void appendOutput() {
        try {
        	for(int color=0;color<Constants.numberOfColors;color++)
        	{
        		String currColor="color_"+String.valueOf(color);
        		String fileNameColor = currColor + "_"+ outputFile;
        		
        	System.out.println("current color for writting file is"+color);
            
            FileWriter myWriter = new FileWriter(fileNameColor, true);
            
            ArrayList<Patterns> out=Periodicity.Daily_pattern[color];
        
            //System.out.println(out);
           

            System.out.println(out.size()+"this is size for daily" );
            myWriter.write("Daily pattern\n");
          
            for (Patterns cy : out) {
                Hotspot_Point ct = cy.center;
                // ct.x;
                // ct.y;
                // ct.z;
                // ct.color
                // pt.radius
                // pt.height;
                // pt.llr;
                String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                        cy.height, cy.llr,cy.p_value);
                //System.out.println(currLine + "**********"+ct.x + " "+ct.y);
                
                myWriter.write(currLine);
            }
            
            myWriter.write("Weekdays pattern\n");
            out=Periodicity.weekdays[color];
            if(out.size()!=0)
            Collections.sort(out, new PatternComperator());
            
            System.out.println(out.size()+"this is size for weekdays" );
            for (Patterns cy : out) {
                Hotspot_Point ct = cy.center;
                // ct.x;
                // ct.y;
                // ct.z;
                // ct.color
                // pt.radius
                // pt.height;
                // pt.llr;
                String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                        cy.height, cy.llr,cy.p_value);
                myWriter.write(currLine);
            }
            
            
            myWriter.write("Weekends pattern\n");
            out=Periodicity.weekends[color];
            if(out.size()!=0)
            Collections.sort(out, new PatternComperator());
           
            System.out.println(out.size()+"this is size for weekends" );
            for (Patterns cy : out) {
            	Hotspot_Point ct = cy.center;
                // ct.x;
                // ct.y;
                // ct.z;
                // ct.color
                // pt.radius
                // pt.height;
                // pt.llr;
            	 String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                         cy.height, cy.llr,cy.p_value);
                myWriter.write(currLine);
            }
            
            
            
            
            myWriter.write("weekly pattern\n");
            out=Periodicity.weekly_patterns[color];
            if(out.size()!=0)
            Collections.sort(out, new PatternComperator());
          
            System.out.println(out.size()+"this is size for weekly" );
            for (Patterns cy : out) {
                Hotspot_Point ct = cy.center;
                // ct.x;
                // ct.y;
                // ct.z;
                // ct.color
                // pt.radius
                // pt.height;
                // pt.llr;
                String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                        cy.height, cy.llr,cy.p_value);
                myWriter.write(currLine);
            }
            
            
            
            
            
            
            
            myWriter.close();
        } 
        }
        catch (Exception e) {
            System.out.println("exception in color");
            System.out.println(e.getMessage());

        }
    }

    static void clearOutputFile() {
        try {
        	
        	for(int color=0;color<Constants.numberOfColors;color++)
        	{
        		String currColor="color_"+String.valueOf(color);
        		String fileNameColor = currColor + "_"+ outputFile;
        		 FileWriter myWriter = new FileWriter(fileNameColor);
                 // clear the file
                 myWriter.close();
                 
        	}
           
        } catch (Exception e) {
        }
    }
    
    
    
    
    
    static void writeFileforAssociation(String name,ArrayList<Integer> colorList) throws Exception {
    	String fileName = name + outputFile;
    	FileWriter myWriter = new FileWriter(fileName);
    	
    	myWriter.close();
    	
    	myWriter = new FileWriter(fileName, true);
    	
    	// daily pattern
    	
    	ArrayList<Patterns> out=AssociationRuleMining.daily_Association;
    	out.sort(new PatternComperator());
        out=MergeOverlapCylinders.mergeOverlap(out);
    	System.out.println(out.size()+"this is size for daily" );
        myWriter.write("Daily pattern\n");
      
        for (Patterns cy : out) {
            Hotspot_Point ct = cy.center;
            // ct.x;
            // ct.y;
            // ct.z;
            // ct.color
            // pt.radius
            // pt.height;
            // pt.llr;
            String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                    cy.height, cy.llr,cy.p_value);
            //System.out.println(currLine + "**********"+ct.x + " "+ct.y);
            
            myWriter.write(currLine);
        }
        
        myWriter.write("Weekdays pattern\n");
        out=AssociationRuleMining.weekdays_Association;
        out.sort(new PatternComperator());
        out=MergeOverlapCylinders.mergeOverlap(out);
        System.out.println(out.size()+"this is size for weekdays" );
        for (Patterns cy : out) {
            Hotspot_Point ct = cy.center;
            // ct.x;
            // ct.y;
            // ct.z;
            // ct.color
            // pt.radius
            // pt.height;
            // pt.llr;
            String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                    cy.height, cy.llr,cy.p_value);
            myWriter.write(currLine);
        }
        
        
        myWriter.write("Weekends pattern\n");
        out=AssociationRuleMining.weekends_Association;
        out.sort(new PatternComperator());
        out=MergeOverlapCylinders.mergeOverlap(out); 
        System.out.println(out.size()+"this is size for weekends" );
        for (Patterns cy : out) {
        	Hotspot_Point ct = cy.center;
            // ct.x;
            // ct.y;
            // ct.z;
            // ct.color
            // pt.radius
            // pt.height;
            // pt.llr;
        	 String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                     cy.height, cy.llr,cy.p_value);
            myWriter.write(currLine);
        }
        
        
        
        
        myWriter.write("weekly pattern\n");
        out=AssociationRuleMining.weekly_Association;
        out.sort(new PatternComperator());
        out=MergeOverlapCylinders.mergeOverlap(out);
        System.out.println(out.size()+"this is size for weekly" );
        for (Patterns cy : out) {
            Hotspot_Point ct = cy.center;
            // ct.x;
            // ct.y;
            // ct.z;
            // ct.color
            // pt.radius
            // pt.height;
            // pt.llr;
            String currLine = String.format("%d %d %d %d %d %d %d %f %f\n", ct.x, ct.y, ct.start, ct.end, ct.color, cy.radius,
                    cy.height, cy.llr,cy.p_value);
            myWriter.write(currLine);
        }
        
        
        
        
        
        
        
        myWriter.close();
    	
    	
    }
    
    static String getString(ArrayList<Integer> a)
    {
            String s="";
        for(int i:a)

        {
            s+=Integer.toString(i)+" ";
        }
            return s;
    }
   static void ChangeDetectionOutputFile()
   {

    String filename = "Change detection" +outputFile;
    FileWriter myWriter;
    try {
        myWriter = new FileWriter(filename);
        myWriter.close();
        myWriter = new FileWriter(filename, true);
        myWriter.write("patterns start");
        Collections.sort(Periodicity.Daily_pattern_CD,new PatternComperator2());
        for(ChangeDetection c:Periodicity.Daily_pattern_CD)
        {
            String colorlist  = getString(c.colorlist);
            String currLine = String.format("x is %d,y is %d,starttime is %d,endtime is %d,radius is %d,height is %d,llr is %f,entropy is %f, start day is %d,endday is %d,list of colors %s\n", c.x, c.y, c.start, c.end, c.radius,
            c.height, c.llr,c.entropy,c.daystart,c.dayend,colorlist);
            myWriter.write(currLine);
        }







        myWriter.close();

    } catch (Exception e) {
        
        e.printStackTrace();
    }
    	
    	
    	
    	

   } 
    
    
    
}