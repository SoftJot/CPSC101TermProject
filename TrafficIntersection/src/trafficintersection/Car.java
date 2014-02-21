

package trafficintersection;


public class Car {

    private static char type;       // 'c'ar, 't'ruck, 'm'otorcycle, 's'emi indicates "size of vehicle"
    private static char heading;    // the direct car is heading 'n'orth 's'outh 'e'ast 'w'est
    private static char destination;// final goal direction 'n'orth 's'outh 'e'ast 'w'est
    private static int xLoc;        // where its "x" coordinate is on the 440x440 map
    private static int yLoc;        // where its "x" coordinate is on the 440x440 map
    private static int speed;       // speed of vehicle
    private static int color;      // color of vehicle
    private static int height;      // height of vehicle
    private static int width;      // width of vehicle
    

    public Car( char type, int x, int y, char heading, char destination, int speed) { // sim creation constructor
        setType(type);
        setXLoc(x);
        setYLoc(y);
        setHeading(heading);
        setDestination(destination);
        setSpeed(speed);
    }
	
	public Car( char type, int x, int y, char heading, int speed) { // drawing constructor
        setType(type);
        setXLoc(x);
        setYLoc(y);
        setHeading(heading);
        setSpeed(speed);
        setHeight();
        setWidth();
    }
    
    public static char getType (){
        return type;
    }
    
    public void setType (char v){
        type = v;
    }

    public static int getXLoc (){
        return xLoc;
    }

    public void setXLoc (int x){
        xLoc = x;
    }
    
    public static int getYLoc (){
        return yLoc;
    }

    public void setYLoc (int y){
        yLoc = y;
    }
    
    public char getDestination() {
        return destination;
    }

    public void setDestination(char dest) {
       destination = dest;
    }
    
    public static int getSpeed () {
        return speed;
    }

    public void setSpeed (int spd){
        speed = spd;
    }

    public void setHeading(char head) {
       heading = head;
    }

	public static char getHeading() {
        return heading;
    }


	public void setHeight () {
		if (getHeading() == 'n' || getHeading() == 's') {
			if (getType() =='c'){
				height = 30;
			} else {
				height = 40;
			}
		} else {
			height = 20;
		}
    }
    
	public void setWidth () {
		if (getHeading() == 'e' || getHeading() == 'w') {
			if (getType() == 'c'){
				width = 30;
			} else {
				width = 40;
			}
		} else {
			width = 20;
		}
    }

	public static int getHeight () {
		return height;
	}
    
	public static int getWidth () {
		return width;
	}

}
