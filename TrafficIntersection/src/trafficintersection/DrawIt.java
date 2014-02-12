package trafficintersection;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawIt extends JPanel implements ActionListener{

	final int CAR_WIDTH = 30, CAR_HEIGHT = 20;
	int truckWidth = 40;

	int car1StartX = -20, car1StartY = 228;
	int car2StartX = 400, car2StartY = 203;

	int rectDx = 3, rectDy = 0; // allows movement in my basic scheme

	int northLightState = 0; // light states for each light
	int southLightState = 0;
	int eastLightState = 0;
	int westLightState = 0;
	
	boolean paused = false;

	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static float dash1[] = {10.0f};  //for dotted lines along road
	final static BasicStroke dashed = new BasicStroke(1.0f,
											BasicStroke.CAP_BUTT,
											BasicStroke.JOIN_MITER,
											10.0f, dash1, 0.0f);

	DrawIt(){
		this.setLayout(null);
		JButton restartButton = new JButton("restart");
		restartButton.setMnemonic(KeyEvent.VK_E);
		restartButton.setActionCommand("restart");
		restartButton.addActionListener(this);
		restartButton.setBounds(470, 50, 100, 25);
		restartButton.setLayout(null);
		add(restartButton);
		
		JButton reverseButton = new JButton("reverse");
		reverseButton.setMnemonic(KeyEvent.VK_R);
		reverseButton.setActionCommand("rewind");
		reverseButton.addActionListener(this);
		reverseButton.setBounds(470, 150, 100, 25);
		add(reverseButton);
		
		
		JButton pauseButton = new JButton("pause");
		pauseButton.setMnemonic(KeyEvent.VK_P);
		pauseButton.setActionCommand("pause");
		pauseButton.addActionListener(this);
		pauseButton.setBounds(470, 200, 100, 25);
		add(pauseButton);
		
		
		JButton forwardButton = new JButton("foward");
		forwardButton.setMnemonic(KeyEvent.VK_F);
		forwardButton.setActionCommand("restart");
		forwardButton.addActionListener(this);
		forwardButton.setBounds(470, 250, 100, 25);
		add(forwardButton);
		
		
		

		Timer timer = new Timer(33, this); // sets current FPS
		timer.setInitialDelay(200);
		timer.start();
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.GRAY); //draw pavement
		g2d.fillRect(0, 0, 450, 450);

		g2d.setColor(Color.WHITE); //dotted road lines      
		g2d.setStroke(dashed);
		g2d.drawRect(225, -10, 240, 460);
		g2d.drawRect(-20, 225, 470, 240);
		g2d.setStroke(stroke);
		
		g2d.setColor(Color.GRAY); //draw intersection pavement over the dots
		g2d.fillRect(200, 200, 51, 51);

		Color seaGreen = new Color(46, 139, 87);
		g2d.setColor(seaGreen); //draw 4 grassy areas
		g2d.fillRect(250, 0, 200, 200);
		g2d.fillRect(0, 250, 200, 200);
		g2d.fillRect(250, 250, 200, 200);
		g2d.fillRect(0, 0, 200, 200);

		g2d.setColor(Color.blue);  //blue car!!
		g2d.fillRect(car1StartX, car1StartY, CAR_WIDTH, CAR_HEIGHT);
		g2d.setColor(Color.LIGHT_GRAY);  //windshield
		g2d.fillRect(car1StartX + 16, car1StartY + 2, CAR_WIDTH - 25, CAR_HEIGHT - 4);

		g2d.setColor(Color.red);  //red truck!!
		g2d.fillRect(car2StartX + 10, car2StartY, truckWidth, CAR_HEIGHT);
		g2d.setColor(Color.LIGHT_GRAY);  //windshield
		g2d.fillRect(car2StartX + 18, car2StartY + 2, truckWidth - 35, CAR_HEIGHT - 4);

		Color firebrick = new Color(178, 34, 34);
		g2d.setColor(firebrick);  //truckbox
		g2d.fillRect(car2StartX + 34, car2StartY + 2, truckWidth - 25, CAR_HEIGHT - 4);

		g2d.setColor(Color.WHITE); // control panel background
		g2d.fillRect(451, 0, 200, 450);
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
			if (paused == false) paused = true;
			else if (paused == true) paused = false;
		}
		if (!paused) {
			repaint();
			updateScene();
		} 
	}

	private void updateScene() {
		car1StartX += rectDx;
		car1StartY += rectDy;

		if (car1StartX > 450) {
			System.out.println("YAY! Starting again.");
			car1StartX = -20;
		}

		car2StartX += (-rectDx - 2);
		car2StartY += rectDy;

		if (car2StartX < -30) {
			System.out.println("Zoooooooooom...");
			car2StartX = 400;
		}

	}

}
