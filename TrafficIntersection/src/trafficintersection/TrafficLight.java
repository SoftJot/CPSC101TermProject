

package trafficintersection;


public class TrafficLight {

    private char state; // 'g'reen, 'y'ellow, 'r'ed
    private char location; // the light is at and pointed 'n'orth 's'outh 'e'ast 'w'est
    
    public TrafficLight (char state, char loc) {
        setState(state);
        setLoc(loc);
    } 
    
    public void setLoc (char loc) {
        location = loc;
    }

    public char getLoc () {
        return location;
    }
    
    public void setState (char stt) {
        state = stt;
    }

    public char getState () {
        return state;
    }
    
    
    
}
