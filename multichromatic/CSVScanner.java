package multichromatic;


import java.io.*;
import java.util.ArrayList;

public class CSVScanner {
    public ArrayList<Point> readCSV(String fileName) {
        ArrayList<Point> ret = new ArrayList<Point>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            // sc.useDelimiter("\n");
            // sc.next();
            // sc.next();
            // sc.next();
            // sc.next();
            // sc.next();
            // sc.next();
            // NOTE : remove the header line in the csv file manually
            
            int xx=1;
            
            while (true) {
                try {
                    // x y slot colr
                    String lineInput = br.readLine();
                    if (lineInput == null) {
                        break;
                    }
                    if(xx==1)
                    {
                    	xx++;
                    	continue;
                    }
                    String[] inp = lineInput.split(",", 100);
                   // System.out.println(inp);
                    int x = (int)Float.parseFloat(inp[1]);
                    int y =(int) Float.parseFloat(inp[2]);
                    int z = (int)Float.parseFloat(inp[3]);
                    
                    int color = (int)Float.parseFloat(inp[4]);
                    Point currPoint = new Point(x, y, z, color);
                   //System.out.println(x+ " "+ y + " "+z+" "+color);
                    ret.add(currPoint);
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("exit with exception in CSV Scanner2");
                    break;
                }
            }
            br.close();
            
            
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("exit with exception in CSV Scanner2");
        }
        
        
        return ret;
    }

}