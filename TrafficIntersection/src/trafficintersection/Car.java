

package trafficintersection;


public class Car {

    private char type;       // 'c'ar, 't'ruck, 'm'otorcycle, 's'emi indicates "size of vehicle"
    private char heading;    // the direct car is heading 'n'orth 's'outh 'e'ast 'w'est
    private char destination;// final goal direction 'n'orth 's'outh 'e'ast 'w'est
    private int xLoc;        // where its "x" coordinate is on the 440x440 map
    private int yLoc;        // where its "x" coordinate is on the 440x440 map
    private int speed;       // speed of vehicle
    
    
    public Car() { //default empty contructor , Do we need?

    }

    public Car( char type, int x, int y, char heading, char destination, int speed) { //proper constructor
        setType(type);
        setXLoc(x);
        setYLoc(y);
        setHeading(heading);
        setDestination(destination);
        setSpeed(speed);
    }
    
    public char getType (){
        return type;
    }
    
    public void setType (char t){
        type = t;
    }

    public int getXLoc (){
        return xLoc;
    }

    public void setXLoc (int x){
        xLoc = x;
    }
    
    public int getYLoc (){
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
    
    public int getSpeed () {
        return speed;
    }

    public void setSpeed (int spd){
        speed = spd;
    }

    public char getHeading() {
        return heading;
    }

    public void setHeading(char head) {
       heading = head;
    }

}
