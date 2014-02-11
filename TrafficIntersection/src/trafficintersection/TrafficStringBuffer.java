
package trafficintersection;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class TrafficStringBuffer {

    public TrafficStringBuffer (String newData) {
        update(newData);
    }
    
    public static void update(String newData) {
        try { 
            BufferedWriter writer = new BufferedWriter( new FileWriter("trafficData.txt"));	
            writer.write(newData);
            writer.close();
        }
        catch (Exception e) {
        }

    }
    
}
