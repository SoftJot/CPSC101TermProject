
package trafficintersection;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class TrafficStringBuffer {

     public static void update(String newData) {
        try { 
            BufferedWriter writer = new BufferedWriter( new FileWriter("trafficData.txt"));	
            writer.write(newData);
            writer.close();
			System.out.println("File written");
        }
        catch (Exception e) {
        }

    }
    
}
