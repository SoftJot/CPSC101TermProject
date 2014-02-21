

package trafficintersection;


public class Animate {

		public static int slXl, slYl, hlXl, hlYl, tlXl, tlYl, wsX, tbX;
		public static int slXr, slYr, hlXr, hlYr, tlXr, tlYr, wsY, tbY;
	
	
	Animate (int x, int y, char d, int w, int h, char t) {
		
		tbX = getWindShieldX(x,d,w);
		tbY = getWindShieldY(y,d,h);

		wsX = getWindShieldX(x,d,w);
		wsY = getWindShieldY(y,d,h);

		slXl = getSLightXl(x,d,w);
		slXr = getSLightXr(x,d,w);

		slYl = getSLightYl(y,d,h);
		slYr = getSLightYr(y,d,h);

		hlXl = getHLightXl(x,d,w);
		hlXr = getHLightXr(x,d,w);

		hlYl = getHLightYl(y,d,h);
		hlYr = getHLightYr(y,d,h);

		tlXl = getTLightXl(x,d,w);
		tlXr = getTLightXr(x,d,w);

		tlYl = getTLightYl(y,d,h);
		tlYr = getTLightYr(y,d,h);
		
	}	

	private int	getHLightXl(int x, char d, int w){
		if (d == 'n' || d == 's') {
			x = x + 2;
		} else if ( d == 'w') {
			x = x - 1;
		} else {
			x = x + w;
		}	
		return x;
	}
	
	private int	getHLightXr(int x, char d, int w){
		if (d == 'n' || d == 's') {
			x = x + w - 6;
		} else if ( d == 'w') {
			x = x - 1;
		} else {
			x = x + w;
		}	
		return x;
	}
	
	private int getHLightYl(int y, char d, int h) {
		if (d == 'e' || d == 'w') {
			y = y + 2;
		} else if ( d == 'n') {
			y = y - 1;
		} else {
			y = y + h;
		}	
		return y;
	}
	
	private int getHLightYr(int y, char d, int h){
		if (d == 'e' || d == 'w') {
			y = y + h - 6;
		} else if ( d == 'n') {
			y = y - 1;
		} else {
			y = y + h;
		}	
		return y;
	}
	
	private int	getTLightXl(int x, char d, int w){
		if (d == 'n' || d == 's') {
			x = x + 2;
		} else if ( d == 'w') {
			x = x + w - 1;
		} 	
		return x;
	}
	
	private int	getTLightXr(int x, char d, int w){
		if (d == 'n' || d == 's') {
			x = x + w - 6;
		} else if ( d == 'w') {
			x = x + w - 1;
		} 	
		return x;
	}
	
	private int getTLightYl(int y, char d, int h) {
		if (d == 'e' || d == 'w') {
			y = y + 2;
		} else if ( d == 'n') {
			y = y + h - 1;
		} 
		return y;
	}
	
	private int getTLightYr(int y, char d, int h){
		if (d == 'e' || d == 'w') {
			y = y + h - 6;
		} else if ( d == 'n') {
			y = y + h - 1;
		} 
		return y;
	}
	
	private int	getSLightXl(int x, char d, int w){
		if ( d == 'e') {
			x = x + w - 3;
		} if (d == 'w') {
			x = x + 1;
		}	
		return x;
	}
	
	private int	getSLightXr(int x, char d, int w){
		if (d == 'n' || d == 's') {
			x = x + w - 1;
		} else if ( d == 'e' ) {
			x = x + w - 3;
		} else {
			x = x + 1;
		}	
		return x;
	}
	
	private int getSLightYl(int y, char d, int h) {
		if ( d == 'n') {
			y = y + 1;
		} else if ( d == 's' ){
			y = y + h - 3;
		}
		return y;
	}
	
	private int getSLightYr(int y, char d, int h){
		if (d == 'e' || d == 'w') {
			y = y + h - 1;
		} else if ( d == 'n' ) {
			y = y + 1;
		} else {
			y = y + h - 3;
		}
		return y;
	}

	private int getWindShieldX(int x, char d, int w) {
		if (d == 'n' || d == 's') {
			x = x + 2;
		} else if ( d == 'w' ) {
			x = x + 12;
		} else {
			x = x + w - 16;
		}	
		return x;
	}
	
	private int getWindShieldY(int y, char d, int h) {
		if (d == 'e' || d == 'w') {
			y = y + 2;
		} else if ( d == 'n' ) {
			y = y + 12;
		} else {
			y = y + h - 16;
		}	
		return y;
	}	
	
	private int getTruckBoxX(int x, char d, int w) {
		if (d == 'n' || d == 's') {
			x = x + 2;
		} else if ( d == 'w' ) {
			x = x + 26;
		} else {
			x = x + w - 30;
		}	
		return x;
	}
	
	private int getTruckBoxY(int y, char d, int h) {
		if (d == 'e' || d == 'w') {
			y = y + 2;
		} else if ( d == 'n' ) {
			y = y + 12;
		} else {
			y = y + h - 16;
		}	
		return y;
	}


}
