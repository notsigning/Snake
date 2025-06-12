import javax.swing.*;
import java.awt.event.*;
public class SnakeMain{
    public static void main(String[] args) throws InterruptedException
    {
        //create new window
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        Snake snek = new Snake();





        //buttons, for people who use touchscreen
        JButton up = new JButton("^");
        JButton down = new JButton("V");
        JButton left = new JButton("<");
        JButton right = new JButton(">");

        //north/up
        ActionListener n = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(snek.getDirection()!=3) snek.setDirection(1);
            }
        };
        up.addActionListener(n);
        up.setBounds(800,250,50,50);


        //south/down
        ActionListener s = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(snek.getDirection()!=1) snek.setDirection(3);
            }
        };
        down.addActionListener(s);
        down.setBounds(800,350,50,50);


        //west/left
        ActionListener w = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(snek.getDirection()!=2) snek.setDirection(4);
            }
        };
        left.addActionListener(w);
        left.setBounds(750,300,50,50);


        //east/right
        ActionListener e = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(snek.getDirection()!=4) snek.setDirection(2);
            }
        };
        right.addActionListener(e);
        right.setBounds(850,300,50,50);

        
        frame.add(up);
        frame.add(down);
        frame.add(left);
        frame.add(right);


        //Keybinds
        //with controls to prevent player from moving backwards by accident and dying instantly as a result
        frame.setFocusable(true);
        KeyListener keyListener = new KeyListener() { //named after a friend who told me to add keybinds
            @Override
            public void keyPressed(KeyEvent e)
            {
                int key = e.getKeyCode();
                System.out.println("Press");
                if (key == KeyEvent.VK_LEFT) {
                    if(snek.getDirection()!=2) snek.setDirection(4);
                }
            
                if (key == KeyEvent.VK_RIGHT) {
                    if(snek.getDirection()!=4) snek.setDirection(2);
                }
            
                if (key == KeyEvent.VK_UP) {
                    if(snek.getDirection()!=3) snek.setDirection(1);
                }
            
                if (key == KeyEvent.VK_DOWN) {
                    if(snek.getDirection()!=1) snek.setDirection(3);
                }
                //snek.move();
            }
            //basically unused
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        };

        frame.addKeyListener(keyListener);

        //snake
        frame.add(snek);
        frame.setVisible(true);
        int delay = 150; // milliseconds
        ActionListener ticker = new ActionListener() {
            int frames = 0;
            public void actionPerformed(ActionEvent evt) {
                if (!snek.move()) {
                    ((Timer) evt.getSource()).stop(); // stop the game if snake dies

                    //prompt user about restarting
                    if(JOptionPane.showConfirmDialog(frame,"Your score: " + snek.score + "\nDo you want to restart?","You Died!",0) == 0){
                        //restart game
                        snek.reset();
                        ((Timer) evt.getSource()).start();
                    }
                    //If not restarting, exit game
                    else frame.setVisible(false);
                    
                }
                else {
                    snek.repaint();
                    System.out.println(frames); //mostly for debugging purposes, remove as needed
                    frames++;
                }
            }
        };
        Timer timer = new Timer(delay, tick);
        timer.start();
    }
}
