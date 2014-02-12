package trafficintersection;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawIt extends JPanel implements ActionListener{

	final int CAR_WIDTH = 30, CAR_HEIGHT = 20;
	final int LIGHT_SIZE = 7;
	final int BASE_BLOCK = 200;
	
	int truckWidth = 40;

	int car1LocX = -30, car1LocY = 228;
	int car2LocX = 450, car2LocY = 203;

	int rectDx = 3, rectDy = 0; // movement magnitude in my basic scheme

	int northLightState = 0;	// light states for each light
	int southLightState = 0;	// 0red,1yellow,2green
	int eastLightState = 2;
	int westLightState = 2;
	
	boolean paused = false;

	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static float dash1[] = {10.0f};  //for dotted lines along road
	final static BasicStroke dashed = new BasicStroke(1.0f,	BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	
	protected JButton restartButton, reverseButton, forwardButton, pauseButton;
	
	DrawIt(){
		this.setLayout(null);
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

		restartButton.addActionListener(this);
		reverseButton.addActionListener(this);
		pauseButton.addActionListener(this);
		forwardButton.addActionListener(this);

		restartButton.setToolTipText("Restart Simulation");
		reverseButton.setToolTipText("Step back one frame");
		pauseButton.setToolTipText("Pause Simulation");
		forwardButton.setToolTipText("Step forward one frame");
		
				Timer timer = new Timer(33, this); // sets current FPS
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
		
		g2d.setColor(Color.GRAY); //draw intersection pavement over the dots
		g2d.fillRect(BASE_BLOCK, BASE_BLOCK, 51, 51);

		g2d.setColor(seaGreen); //draw 4 grassy areas

		g2d.fillRect(0, 0, BASE_BLOCK, BASE_BLOCK);
		g2d.fillRect(250, 0, BASE_BLOCK, BASE_BLOCK);
		g2d.fillRect(0, 250, BASE_BLOCK, BASE_BLOCK);
		g2d.fillRect(250, 250, BASE_BLOCK, BASE_BLOCK);


		// draw some vehicles
		
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
		
		//draw 12 Traffic light containers
		if (westLightState == 0) {
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		} else if (westLightState == 1) {
			g2d.setColor(Color.yellow);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK + CAR_WIDTH - 2 +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		}
		
		if (northLightState == 0) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK + LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_SIZE), BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
		} else if (northLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.yellow);
			g2d.fillRect(BASE_BLOCK + LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_SIZE), BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK +(2*LIGHT_SIZE), BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
		}
		
		if (southLightState == 0) {
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_SIZE, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_SIZE), BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
		} else if (southLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.yellow);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_SIZE, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_SIZE), BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
		} else {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 + LIGHT_SIZE, BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH -2 +(2*LIGHT_SIZE), BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
		}

		if (eastLightState == 0) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.red);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		} else if (eastLightState == 1) {
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.yellow);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		} else {
			g2d.setColor(Color.green);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK, LIGHT_SIZE, LIGHT_SIZE);
			g2d.setColor(Color.black);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK + LIGHT_SIZE, LIGHT_SIZE, LIGHT_SIZE);
			g2d.fillRect(BASE_BLOCK + CAR_WIDTH + CAR_HEIGHT - LIGHT_SIZE, BASE_BLOCK +(2*LIGHT_SIZE), LIGHT_SIZE, LIGHT_SIZE);
		}
		
		g2d.setColor(Color.WHITE); // control panel background
		g2d.fillRect(451, 0, BASE_BLOCK, 450);
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
		
		car1LocX += rectDx;
		car1LocY += rectDy;

		if (car1LocX > 450) { //cheezy repeating loop, car one
			car1LocX = -20;
		}

		car2LocX += (-rectDx - 2); // faking forward/backward movement until I read real data
		car2LocY += rectDy;

		if (car2LocX < -30) { // cheezy repeating loop for car two
			car2LocX = 400;
		}
		
		northLightState = 0; // light states for each light
		southLightState = northLightState;
		eastLightState = 2;
		westLightState = eastLightState;

	}

}
