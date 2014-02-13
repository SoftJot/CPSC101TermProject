/*
	Goals: ranked more important to less

	actually parse data
	add blinkers on cars
	make cars turn, nicely
	(done)change lights location to reflect reality
	add a stop line
	make SlowMo/SloRewind buttons work
	add fast forward ( increase FPS )
	add backwards capability (ugh, read the file backwards from that point?)

	round street corners to look less akward
	make motorbike
	make rig
	add fps meter

	change lights to circles
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

	final int CAR_WIDTH = 30, CAR_HEIGHT = 20;
	final int LIGHT_HEIGHT = 7, LIGHT_WIDTH = 3;
	final int BASE_BLOCK = 200;
	final int BASE_BLOCK_AND_ROAD = BASE_BLOCK+(BASE_BLOCK/4);
	int truckWidth = 40;

	int fps = 30;			//draw screen rate
	int refresh = 1000/fps;	// timer will tick this fast

	int car1LocX = -30, car1LocY = 228;  //fake car start spots
	int car2LocX = 450, car2LocY = 203;

	int rectDx = 3, rectDy = 0; // movement magnitude in my basic scheme

	int northLightState = 0;	// initial light states for each light
	int southLightState = 0;	// 0red,1orange,2green
	int eastLightState = 2;
	int westLightState = 2;

	boolean paused = false;
	boolean northRed = ((northLightState != 0));   //temp lightcyle variable
	int cycleCount=0; 
	boolean pickOne = true;
		
	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static float dash1[] = {10.0f};  //for dotted lines along road
	final static BasicStroke dashed = new BasicStroke(1.0f,	BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	protected JButton restartButton, reverseButton, forwardButton, pauseButton;
	protected JCheckBox tryStyles;

	DrawIt(){

		this.setLayout(null);  //add the buttons to the right side
		restartButton = new JButton("restart");
		restartButton.setMnemonic(KeyEvent.VK_E);  //alt+E will fire
		restartButton.setEnabled(true);
		restartButton.setBounds(470, 50, 100, 25);
		restartButton.setActionCommand("restart");
		add(restartButton);

		reverseButton = new JButton("reverse");
		reverseButton.setMnemonic(KeyEvent.VK_R);//alt+R will fire
		reverseButton.setEnabled(false);
		reverseButton.setBounds(470, 150, 100, 25);
		reverseButton.setActionCommand("reverse");
		add(reverseButton);


		pauseButton = new JButton("pause");
		pauseButton.setMnemonic(KeyEvent.VK_P);//alt+P will fire
		pauseButton.setEnabled(true);
		pauseButton.setBounds(470, BASE_BLOCK, 100, 25);
		pauseButton.setActionCommand("pause");
		add(pauseButton);


		forwardButton = new JButton("forward");
		forwardButton.setMnemonic(KeyEvent.VK_F);//alt+F will fire
		forwardButton.setEnabled(false);
		forwardButton.setBounds(470, 250, 100, 25);
		forwardButton.setActionCommand("forward");
		add(forwardButton);

		tryStyles = new JCheckBox("styles");
		tryStyles.setMnemonic(KeyEvent.VK_0);
		tryStyles.setEnabled(true);
		tryStyles.setBounds(470, 300, 100, 25);
		tryStyles.setActionCommand("styles");
		add(tryStyles);

		tryStyles.addActionListener(this);
		restartButton.addActionListener(this);
		reverseButton.addActionListener(this);
		pauseButton.addActionListener(this);
		forwardButton.addActionListener(this);

		restartButton.setToolTipText("Restart Simulation");
		reverseButton.setToolTipText("Step back one frame");
		pauseButton.setToolTipText("Pause Simulation");
		forwardButton.setToolTipText("Step forward one frame");
		
		Timer timer = new Timer(refresh, this); // sets current FPS
		timer.setInitialDelay(BASE_BLOCK);
		timer.start();

	}


	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color seaGreen = new Color(46, 139, 87);
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
		g2d.fillRect(BASE_BLOCK-10, BASE_BLOCK+CAR_HEIGHT+8, 2, CAR_HEIGHT); //west
		g2d.fillRect(BASE_BLOCK_AND_ROAD+10, BASE_BLOCK+3, 2, CAR_HEIGHT); //north
		g2d.fillRect(BASE_BLOCK+3, BASE_BLOCK-10, CAR_HEIGHT,2); //east
		g2d.fillRect(BASE_BLOCK+CAR_WIDTH-2, BASE_BLOCK_AND_ROAD+10, CAR_HEIGHT, 2); //south
		
		// end playing field 

		// draw some vehicles now, so they go over the road

		g2d.setColor(Color.blue);  //blue car!!
		g2d.fillRect(car1LocX, car1LocY, CAR_WIDTH, CAR_HEIGHT);
		g2d.setColor(Color.LIGHT_GRAY);  //windshield
		g2d.fillRect(car1LocX + 16, car1LocY + 2, CAR_WIDTH - 25, CAR_HEIGHT - 4);

		g2d.setColor(Color.red);  //red truck!!
		g2d.fillRect(car2LocX + 10, car2LocY, truckWidth, CAR_HEIGHT);
		g2d.setColor(Color.LIGHT_GRAY);  //windshield
		g2d.fillRect(car2LocX + 18, car2LocY + 2, truckWidth - 35, CAR_HEIGHT - 4);

		g2d.setColor(fireBrick);  //truckbox
		g2d.fillRect(car2LocX + 34, car2LocY + 2, truckWidth - 25, CAR_HEIGHT - 4);

		
		//draw 12 Traffic lights now, so cars go under the lights
	if (pickOne) {	
		if (eastLightState == 0) { //eastbound lights
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else if (eastLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK -2, BASE_BLOCK + CAR_WIDTH -2 + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		}

		if (southLightState == 0) {  //southbound
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + 2, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + 2 + LIGHT_HEIGHT, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK + 2 +(2*LIGHT_HEIGHT), BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else if (southLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + 2, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK + 2 + LIGHT_HEIGHT, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + 2 +(2*LIGHT_HEIGHT), BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK + 2, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + 2 + LIGHT_HEIGHT, BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + 2 +(2*LIGHT_HEIGHT), BASE_BLOCK -2, LIGHT_HEIGHT, LIGHT_WIDTH);
		}

		if (northLightState == 0) {
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else if (northLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + 2, LIGHT_HEIGHT, LIGHT_WIDTH);
		}

		if (westLightState == 0) {  //westbound traffic
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + LIGHT_HEIGHT + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK +(2*LIGHT_HEIGHT) + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
		} else if (westLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + LIGHT_HEIGHT + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK +(2*LIGHT_HEIGHT) + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK + LIGHT_HEIGHT + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + 2, BASE_BLOCK +(2*LIGHT_HEIGHT) + 2, LIGHT_WIDTH, LIGHT_HEIGHT);
		}
	} else {
			if (eastLightState == 0) { //eastbound lights
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else if (eastLightState == 1) {
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, BASE_BLOCK + CAR_WIDTH + (2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		}

		if (southLightState == 0) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else if (southLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + LIGHT_HEIGHT, BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_HEIGHT), BASE_BLOCK_AND_ROAD + LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		}

		if (northLightState == 0) {
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else if (northLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_HEIGHT, BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_HEIGHT), BASE_BLOCK - LIGHT_HEIGHT, LIGHT_HEIGHT, LIGHT_WIDTH);
		}

		if (westLightState == 0) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK +(2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else if (westLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.orange);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK +(2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK + LIGHT_HEIGHT, LIGHT_WIDTH, LIGHT_HEIGHT);
			g2d.fillRect(BASE_BLOCK- LIGHT_HEIGHT, BASE_BLOCK +(2*LIGHT_HEIGHT), LIGHT_WIDTH, LIGHT_HEIGHT);
		}
	}
		g2d.setColor(Color.WHITE); // control panel background
		g2d.fillRect(BASE_BLOCK_AND_ROAD + BASE_BLOCK, 0, BASE_BLOCK, BASE_BLOCK_AND_ROAD + BASE_BLOCK);

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
				reverseButton.setEnabled(paused);
				forwardButton.setEnabled(paused);
			} else {
				paused = false;
				pauseButton.setText("pause");
				reverseButton.setEnabled(paused);
				forwardButton.setEnabled(paused);
			}
		}

		if ("restart".equals(e.getActionCommand())) {
			System.out.println("restarting");
			// reset loop from start, but use this for now
			car1LocX = -20;
			car1LocY = 228;  //proof of function
			car2LocX = 450;
			car2LocY = 203;
			repaint();
			updateScene();
		}
		
		if ("styles".equals(e.getActionCommand())) {
			pickOne = !pickOne;
		}

		if (!paused) {
			repaint();
			updateScene();
		} else {

		}

	}

	private void updateScene() {
		// get the next "timeSlice"

		// while (cars in list) {
		//		produce cars in proper loc
		// }



		if (car1LocX > 450) { //cheezy repeating loop, car one
			car1LocX = -20;
		}

		car2LocX += (-rectDx - 2); // faking forward/backward movement until I read real data
		car2LocY += rectDy;
		car1LocX += rectDx;
		car1LocY += rectDy;

		if (car2LocX < -30) { // cheezy repeating loop for car two
			car2LocX = 400;
		}



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
			System.out.println("times up!");
			northRed = !northRed;
			cycleCount=0;
		}
	}

}
