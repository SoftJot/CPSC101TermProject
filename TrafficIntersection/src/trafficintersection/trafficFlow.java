
package trafficintersection;

public class trafficFlow {
    
    private static int IntervalUpdate = 100;
    private static int timeFrame = 0;
    private static int lengthOfSimulator = 60000;
    private static String data;
    
    public static void main(String[] args) {
        while (timeFrame < lengthOfSimulator) {

            /*
            
            getLights(); // for/while
            getCar(); //   for/while
            
            carsCollision(); // for/while
            setSpeed();      // for/while
            setCarsLocation();
            setLights();
            
            put each 100ms loop into data
*/ 
            //Write to Buffer       
        
        TrafficStringBuffer.update(data);

        //Increment time interval        
        timeFrame += IntervalUpdate;

        }
        DrawIt.showAnimation();
    
    
    }
    
}
