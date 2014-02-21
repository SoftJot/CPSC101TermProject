/*
	Goals: ranked more important to less

	finish parse data
	
	make cars turn, nicely (maybe not for this project)
	make SlowMo/SloRewind buttons work

	add backwards capability

	make motorbike ??
	make rig  ??
	add pleasing things, clouds. day/night (headlights)


*/
package trafficintersection;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawIt extends JPanel implements ActionListener{
	
	private final int CAR_WIDTH = 30, CAR_HEIGHT = 20;
	private final int LIGHT_HEIGHT = 7;
	private final int BASE_BLOCK = 200;  // convenient constant
	private final int BASE_BLOCK_AND_ROAD = BASE_BLOCK+(BASE_BLOCK/4);

	private int fps = 10;			//draw screen rate, used for fastforward/reverse
	private int refresh = 1000/fps;	// timer will tick this fast
	private int initialDelay = 100;
	
	private int speedOffSetX = 0, speedOffSetY = 0; // movement magnitude in my basic scheme

	private int northLightState = 0;	// initial light states for each light
	private int southLightState = 0;	// 0red,1orange,2green
	private int eastLightState = 2;
	private int westLightState = 2;
	
	private Timer timer;
	private boolean paused = false;
	
	private boolean northRed = ((northLightState != 0));   //temp lightcyle variable
	private int cycleCount=0; 
	private boolean pickOne = true; //swap between lights styles
		
	private final static BasicStroke stroke = new BasicStroke(2.0f);
	private final static float dash[] = {10.0f};  //for dotted lines along road
	private final static BasicStroke dashed = new BasicStroke(1.0f,	BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

	private JButton restartButton, reverseButton, forwardButton, pauseButton;
	private JButton fastButton, slowButton;
	private JCheckBox tryStyles;

	DrawIt(){

		this.setLayout(null);  //add the buttons to the right side
		restartButton = new JButton("restart");
		restartButton.setMnemonic(KeyEvent.VK_E);  //alt+E will fire
		restartButton.setEnabled(true);
		restartButton.setBounds(470, 50, 100, 25);
		restartButton.setActionCommand("restart");
		add(restartButton);

		slowButton = new JButton("slower");
		slowButton.setMnemonic(KeyEvent.VK_R);//alt+R will fire
		slowButton.setEnabled(true);
		slowButton.setBounds(470, 100, 100, 25);
		slowButton.setActionCommand("slower");
		add(slowButton);

		reverseButton = new JButton("reverse");
		reverseButton.setMnemonic(KeyEvent.VK_R);//alt+R will fire
		reverseButton.setEnabled(false);
		reverseButton.setBounds(470, 137, 100, 25);
		reverseButton.setActionCommand("reverse");
		add(reverseButton);

		pauseButton = new JButton("pause");
		pauseButton.setMnemonic(KeyEvent.VK_P);//alt+P will fire
		pauseButton.setEnabled(true);
		pauseButton.setBounds(470, 162, 100, 25);
		pauseButton.setActionCommand("pause");
		add(pauseButton);

		forwardButton = new JButton("forward");
		forwardButton.setMnemonic(KeyEvent.VK_F);//alt+F will fire
		forwardButton.setEnabled(false);
		forwardButton.setBounds(470, 187, 100, 25);
		forwardButton.setActionCommand("forward");
		add(forwardButton);

		fastButton = new JButton("faster");
		fastButton.setMnemonic(KeyEvent.VK_F);//alt+F will fire
		fastButton.setEnabled(true);
		fastButton.setBounds(470, 225, 100, 25);
		fastButton.setActionCommand("faster");
		add(fastButton);

		tryStyles = new JCheckBox("styles");
		tryStyles.setMnemonic(KeyEvent.VK_0);
		tryStyles.setEnabled(true);
		tryStyles.setBounds(470, 300, 100, 25);
		tryStyles.setActionCommand("styles");
		add(tryStyles);

		tryStyles.addActionListener(this);
		restartButton.addActionListener(this);
		slowButton.addActionListener(this);
		reverseButton.addActionListener(this);
		pauseButton.addActionListener(this);
		forwardButton.addActionListener(this);
		fastButton.addActionListener(this);

		restartButton.setToolTipText("Restart Simulation");
		slowButton.setToolTipText("Decrease speed by 10 or 1 fps if < 10 already");
		reverseButton.setToolTipText("Step back one frame");
		pauseButton.setToolTipText("Pause Simulation");
		forwardButton.setToolTipText("Step forward one frame");
		fastButton.setToolTipText("Increase speed by 10 fps");
		
		timer = new Timer(refresh, this); // sets FPS
		timer.setInitialDelay(initialDelay);
		timer.start();

	}


	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color seaGreen = new Color(46, 139, 87);
		Color signalOrange = new Color(255, 153, 51);
		Color fireBrick = new Color(178, 34, 34);

		g2d.setColor(Color.GRAY); //draw pavement
		g2d.fillRect(0, 0, 450, 450);

		g2d.setColor(Color.WHITE); //dotted road lines
		g2d.setStroke(dashed);
		g2d.drawRect(225, -10, 240, 460);
		g2d.drawRect(-20, 225, 470, 240);
		g2d.setStroke(stroke);

		g2d.setColor(Color.GRAY); //draw center pavement over the dots
		g2d.fillRect(BASE_BLOCK, BASE_BLOCK, 51, 51);

		g2d.setColor(seaGreen); //draw 4 grassy areas

		g2d.fillRoundRect(0, 0, BASE_BLOCK, BASE_BLOCK,30,30);					//draw rounded edges on squares
		g2d.fillRoundRect(BASE_BLOCK_AND_ROAD, 0, BASE_BLOCK, BASE_BLOCK,30,30);
		g2d.fillRoundRect(0, BASE_BLOCK_AND_ROAD, BASE_BLOCK, BASE_BLOCK,30,30);
		g2d.fillRoundRect(BASE_BLOCK_AND_ROAD, BASE_BLOCK_AND_ROAD, BASE_BLOCK, BASE_BLOCK,30,30);
		
		g2d.fillRect(0, 0, BASE_BLOCK, BASE_BLOCK-CAR_WIDTH);	// redraw in corners we dont want rounded
		g2d.fillRect(0, 0, BASE_BLOCK-CAR_WIDTH,BASE_BLOCK);					
		
		g2d.fillRect(BASE_BLOCK_AND_ROAD, 0, BASE_BLOCK, BASE_BLOCK-CAR_WIDTH);
		g2d.fillRect(BASE_BLOCK_AND_ROAD+CAR_WIDTH, 0, BASE_BLOCK,BASE_BLOCK);	

		g2d.fillRect(0, BASE_BLOCK_AND_ROAD, BASE_BLOCK-CAR_WIDTH, BASE_BLOCK);
		g2d.fillRect(0, BASE_BLOCK_AND_ROAD+CAR_WIDTH, BASE_BLOCK, BASE_BLOCK);

		g2d.fillRect(BASE_BLOCK_AND_ROAD, BASE_BLOCK_AND_ROAD+CAR_WIDTH, BASE_BLOCK, BASE_BLOCK);
		g2d.fillRect(BASE_BLOCK_AND_ROAD+CAR_WIDTH, BASE_BLOCK_AND_ROAD, BASE_BLOCK, BASE_BLOCK);
		
		//draw in stop lines
		g2d.setColor(Color.WHITE);
		g2d.fillRect(BASE_BLOCK - 10, BASE_BLOCK+CAR_HEIGHT + 8, 2, CAR_HEIGHT);			//west
		g2d.fillRect(BASE_BLOCK_AND_ROAD + 10, BASE_BLOCK + 3, 2, CAR_HEIGHT);				//north
		g2d.fillRect(BASE_BLOCK + 3, BASE_BLOCK - 10, CAR_HEIGHT,2);						//east
		g2d.fillRect(BASE_BLOCK + CAR_WIDTH - 2, BASE_BLOCK_AND_ROAD + 10, CAR_HEIGHT, 2);	//south
		
		// end playing field 

		// draw vehicles for next "layer"

			Car thisCar = new Car('c',203,120,'n',4); // token delimited from big StringBuffer
			int x = thisCar.getXLoc();
			int y = thisCar.getYLoc();
			int h = thisCar.getHeight();
			int w = thisCar.getWidth();
			char d = thisCar.getHeading();
			char t = thisCar.getType();
			
			Animate newFrame = new Animate(x, y, d, w, h, t);

			// draw vehicle body
			g2d.setColor(((t=='t')?fireBrick:Color.blue));			
			g2d.fillRoundRect(x,y,w,h,5,5);
//			g2d.fillRect(x,y,w,h);

			// draw headlights (always on)
			g2d.setColor(Color.white);
			g2d.fillRect(newFrame.hlXl,newFrame.hlYl, ((d =='n' || d =='s')? 4:1),((d =='n' || d =='s') ? 1:4)); //left light
			g2d.fillRect(newFrame.hlXr,newFrame.hlYr, ((d =='n' || d =='s')? 4:1),((d =='n' || d =='s') ? 1:4)); //right light
			
			// get proper color then draw tail lights
			if (isBlinking() || thisCar.getSpeed() == 0) {
				g2d.setColor(Color.RED);		
			} else {
				g2d.setColor(Color.yellow);
			}
			g2d.fillRect(newFrame.tlXl,newFrame.tlYl, ((d =='n' || d =='s')? 4:1),((d =='n' || d =='s') ? 1:4)); //left light
			g2d.fillRect(newFrame.tlXr,newFrame.tlYr, ((d =='n' || d =='s')? 4:1),((d =='n' || d =='s') ? 1:4)); //right light

			// front signal lights
			if (isBlinking()) {
				g2d.setColor(signalOrange);		
			} 
			g2d.fillRect(newFrame.slXl,newFrame.slYl, ((d =='n' || d =='s')? 1:2),((d =='n' || d =='s') ? 2:1)); //left light
			g2d.fillRect(newFrame.slXr,newFrame.slYr, ((d =='n' || d =='s')? 1:2),((d =='n' || d =='s') ? 2:1)); //right light

			g2d.setColor(Color.LIGHT_GRAY);	//windshield
			g2d.fillRect(newFrame.wsX, newFrame.wsY, ((d =='n' || d =='s')? 16:4),((d =='n' || d =='s') ? 4:16));

			if (t=='t') {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(newFrame.tbX, newFrame.tbY, 16, 16);
			}	//truckbox


		
		//draw 12 Traffic lights now, since cars go under the lights
		g2d.setColor(Color.black);
		g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2, LIGHT_HEIGHT,LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + (2*LIGHT_HEIGHT), LIGHT_HEIGHT, LIGHT_HEIGHT);

		if (eastLightState == 0) { //eastbound lights
			g2d.setColor(Color.red);
			g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2, LIGHT_HEIGHT,LIGHT_HEIGHT);
		} else if (eastLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillOval(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + (2*LIGHT_HEIGHT), LIGHT_HEIGHT, LIGHT_HEIGHT);
		}

		g2d.setColor(Color.black);
		g2d.fillOval(BASE_BLOCK + 2, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK + 2 + LIGHT_HEIGHT, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK + 2 +(2*LIGHT_HEIGHT), BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);

		if (southLightState == 0) {  //southbound
			g2d.setColor(Color.red);
			g2d.fillOval(BASE_BLOCK + 2 +(2*LIGHT_HEIGHT), BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else if (southLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillOval(BASE_BLOCK + 2 + LIGHT_HEIGHT, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillOval(BASE_BLOCK + 2, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		}
		
		g2d.setColor(Color.black);
		g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2, BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2 + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2 +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);

		if (northLightState == 0) {
			g2d.setColor(Color.red);
			g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2, BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else if (northLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2 + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillOval(BASE_BLOCK + CAR_WIDTH - 2 +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD - 2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		}

		g2d.setColor(Color.black);
		g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2 + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_HEIGHT);
		g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2 +(2*LIGHT_HEIGHT), LIGHT_HEIGHT, LIGHT_HEIGHT);

		if (westLightState == 0) {  //westbound traffic
			g2d.setColor(Color.red);
			g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2 +(2*LIGHT_HEIGHT), LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else if (westLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2 + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillOval(BASE_BLOCK_AND_ROAD -2, BASE_BLOCK +2, LIGHT_HEIGHT, LIGHT_HEIGHT);
		}

		g2d.setColor(Color.WHITE); // control panel background
		g2d.fillRect(BASE_BLOCK_AND_ROAD + BASE_BLOCK, 0, BASE_BLOCK, BASE_BLOCK_AND_ROAD + BASE_BLOCK);
		g2d.setColor(Color.BLACK); // fps meter
		g2d.drawString("FPS:" + Integer.toString(fps), 500, 15);
	}

	private boolean isBlinking() {
		return true;
	}
	
	
	public static void showAnimation() {

		JFrame f = new JFrame("Traffic Intersection GUI");
		JPanel p = new DrawIt();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(p);
		f.setSize(600, 479);
		f.setResizable(false);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		p.setVisible(true);

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if ("pause".equals(e.getActionCommand())) {
			if (paused == false) {
				paused = true;
				pauseButton.setText("resume");
				pauseButton.setToolTipText("Resume Simulation");
			} else {
				paused = false;
				pauseButton.setText("pause");
			}
			reverseButton.setEnabled(paused);
			forwardButton.setEnabled(paused);
		}

		else if ("restart".equals(e.getActionCommand())) {
			repaint();
			updateScene();
		}
		
		else if ("faster".equals(e.getActionCommand())) {
			fps+=10;
			refresh=1000/fps;
			timer.stop();
			timer.setDelay(refresh);
			timer.setInitialDelay(10);
			timer.start();
		}

		else if ("slower".equals(e.getActionCommand())) {
			if (fps > 10) {
				fps-=10;
				refresh=1000/fps;
				timer.stop();
				timer.setDelay(refresh);
				timer.setInitialDelay(10);
				timer.start();
			} else if (fps > 1) {
				fps-=1;
				refresh=1000/fps;
				timer.stop();
				timer.setDelay(refresh);
				timer.setInitialDelay(10);
				timer.start();
			}
		}

		else if ("styles".equals(e.getActionCommand())) {
			pickOne = !pickOne;
		}

		else if (!paused) {
			repaint();
			updateScene();
		}
	}

	private void updateScene() {

		if (cycleCount < 360) {		//the magic ratio asked for , 3000, 600
									//about 13second cycles real time
			if (cycleCount <= 300) {
					if (northRed) {
						northLightState = 0;
						southLightState = northLightState;
						eastLightState = 2; // 
						westLightState = eastLightState;
					} else {
						northLightState = 2;
						southLightState = northLightState;
						eastLightState = 0; // 
						westLightState = eastLightState;
					}
			} else {
					if (northRed) {
						northLightState = 0;
						southLightState = northLightState;
						eastLightState = 1; // 
						westLightState = eastLightState;
					} else {
						northLightState = 1;
						southLightState = northLightState;
						eastLightState = 0; // 
						westLightState = eastLightState;
					}
			}
			
			cycleCount++;
			
		} else {
			northRed = !northRed;
			cycleCount=0;
		}
	}

}
