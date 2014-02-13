
package trafficintersection;

public class trafficFlow {
    
    private static final int IntervalUpdate = 30;
    private static int timeFrame = 0;
    private static int lengthOfSimulator = 60000;
    private static String data;
    
    public static void main(String[] args) {
    //    while (timeFrame < lengthOfSimulator) {

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
  		//	y = 228 for eastbound traffic
		//	y = 203 for westbound traffic
		//	x = 228 for northbound traffic
		//	x = 203 for southbound traffic

		// space delimited for each cars data 
		// - delimited for time slice data
		// , delimited to separate each car or light 

				// type  x y heading destination speed,
       data = "0-"
			+ "l 000 000 w w 2,"	// 'l'ights dont need a loc
			+ "l 000 000 e e 2," 	// dest=head	
			+ "l 000 000 n n 0,"	// 0 red 1 yellow 2 green instead of a speed
			+ "l 000 000 s s 0,"	// lights dont update unless they change color
			+ "c 450 203 w w 10," 
			+ "c 000 228 e n 8,"	//each line is a vehicle, loc, head/dest and speed
			+ "t 228 450 n n 6,"	
			+ "c 203 000 s e 4,"
			+ "-30-"				 //next time slice
			+ "c 440 203 w w 10,"
			+ "c 008 228 e n 8,"		//each vehicle has a new loc based on head and speed
			+ "t 228 444 n n 6,"
			+ "c 203 004 s e 4"
			+ "-60-"				//next time slice
			+ "c 430 203 w w 10,"
			+ "c 016 228 e n 8,"		//repeat till done
			+ "t 228 438 n n 6,"
			+ "c 203 008 s e 4"
			+ "-90-"				//next time slice
			+ "c 420 203 w w 10,"
			+ "c 024 228 e n 8,"
			+ "t 228 432 n n 6,"
			+ "c 203 012 s e 4"
			+ "-120-"				//next time slice
			+ "c 410 203 w w 10,"
			+ "c 032 228 e n 8,"
			+ "t 228 426 n n 6,"
			+ "c 203 016 s e 4"
			+ "-150-"				//next time slice
			+ "c 400 203 w w 10,"
			+ "c 040 228 e n 8,"
			+ "t 228 420 n n 6,"
			+ "c 203 020 s e 4"
			+ "-180-"				//next time slice
			+ "c 390 203 w w 10,"
			+ "c 048 228 e n 8,"
			+ "t 228 416 n n 6,"
			+ "c 203 024 s e 4"
			+ "-210-"				//next time slice
			+ "c 380 203 w w 10,"
			+ "c 056 228 e n 8,"
			+ "t 228 410 n n 6,"
			+ "c 203 028 s e 4"
			+ "-240-"				//next time slice 
			+ "c 370 203 w w 10,"
			+ "c 064 228 e n 8,"
			+ "t 228 404 n n 6,"
			+ "c 203 032 s e 4"
			+ "-270-"				//next time slice
			+ "c 360 203 w w 10,"
			+ "c 072 228 e n 8,"
			+ "t 228 398 n n 6,"
			+ "c 203 036 s e 4"
			+ "-300-"				//next time slice
			+ "c 350 203 w w 10,"
			+ "c 080 228 e n 8,"
			+ "t 228 392 n n 6,"
			+ "c 203 040 s e 4"
			+ "-330-"				//next time slice
			+ "c 340 203 w w 10,"
			+ "c 088 228 e n 8,"
			+ "t 228 386 n n 6,"
			+ "c 203 044 s e 4"
			+ "-360-"				//next time slice.
			+ "c 330 203 w w 10,"
			+ "c 096 228 e n 8,"
			+ "t 228 380 n n 6,"
			+ "c 203 048 s e 4"
			+ "-390-"				//next time slice
			+ "c 320 203 w w 10,"
			+ "c 104 228 e n 8,"
			+ "t 228 374 n n 6,"
			+ "c 203 052 s e 4"
			+ "-420-"				//next time slice
			+ "c 310 203 w w 10,"
			+ "c 112 228 e n 8,"
			+ "t 228 368 n n 6,"
			+ "c 203 056 s e 4"
			+ "-450-"				//next time slice
			+ "c 300 203w w 10,"
			+ "c 120 228 e n 8,"
			+ "t 228 362 n n 6,"
			+ "c 203 060 s e 4"
			+ "-480-"				//next time slice
			+ "c 290 203 w w 10,"
			+ "c 128 228 e n 8,"
			+ "t 228 356 n n 6,"
			+ "c 203 064 s e 4"
			+ "-510-"				//next time slice
			+ "c 280 203 w w 10,"
			+ "c 136 228 e n 8,"
			+ "t 228 350 n n 6,"
			+ "c 203 068 s e 4"
			+ "-540-"				//next time slice
			+ "c 270 203 w w 10,"
			+ "c 144 228 e n 8,"
			+ "t 228 344 n n 6,"
			+ "c 203 072 s e 4"
			+ "-570-"				//next time slice
			+ "c 260 203 w w 10,"
			+ "c 152 228 e n 8,"
			+ "t 228 338 n n 6,"
			+ "c 203 076 s e 4"
			+ "-600-"				//next time slice
			+ "c 250 203 w w 10,"
			+ "c 160 228 e n 8,"
			+ "t 228 332 n n 6,"		// i give up manually doing this LOL
			+ "c 203 080 s e 4";

			
			
        //Increment time interval        
        timeFrame += IntervalUpdate;

//        }
		TrafficStringBuffer.update(data);

        DrawIt.showAnimation();
    
    
    }
    
}
