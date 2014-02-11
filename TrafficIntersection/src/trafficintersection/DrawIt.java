

package trafficintersection;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

  
public class DrawIt extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D)g;  

        //drawing goes here!!
    }
            
        
       
 //   public static void main(String[] args) {
 //      showTraffic();
 //   }
	

    public static void showTraffic() {
		
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new DrawIt());  
        f.setSize(900,900);
	f.setLocationRelativeTo(null);		
        f.setResizable(true);	
        f.setVisible(true);
    }  


    
}