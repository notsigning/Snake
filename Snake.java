import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
public class Snake extends JComponent{
    private ArrayList<Integer> xCoords;
    private ArrayList<Integer> yCoords;
    private int direction; 
    /*
     * 1: north
     * 2: east
     * 3: south
     * 4: west
     */
    private int foodx;
    private int foody;
    int score;
    public int getDirection() //returns direction
    {
        return direction;
    }
    public void setDirection(int a) //changes direction
    {
        direction = a;
    }
    public Snake() //constructor
    {
        super();
        reset();
    }
    public void reset() //resets the game
    {
        xCoords = new ArrayList<Integer>();
        yCoords = new ArrayList<Integer>();
        for(int i=5;i<=10;i++)
        {
            addPoint(i,7);
        }
        direction = 2;
        foodx = 15;
        foody = 7;
        score = 0;
    }
    public void addPoint(int x,int y) //adds a snake segment to the arraylist
    {
        xCoords.add(x);
        yCoords.add(y);
    }
    /*
    Moves the player forward, and accounts for all actions that may happen, e.g. eating a dot
    Returns true if player is still alive, returns false otherwise
    */
    public boolean move()
    {
        switch (direction)
        {
            case 1:
                xCoords.add(xCoords.get(xCoords.size()-1));
                yCoords.add(yCoords.get(yCoords.size()-1)-1);
                break;
            case 2:
                xCoords.add(xCoords.get(xCoords.size()-1)+1);
                yCoords.add(yCoords.get(yCoords.size()-1));
                break;
            case 3:
                xCoords.add(xCoords.get(xCoords.size()-1));
                yCoords.add(yCoords.get(yCoords.size()-1)+1);
                break;
            case 4:
                xCoords.add(xCoords.get(xCoords.size()-1)-1);
                yCoords.add(yCoords.get(yCoords.size()-1));
                break;
        }

        
        //surprisingly efficient algorithm to simulate dot eating
        if(!(foodx == xCoords.get(xCoords.size()-1) && foody == yCoords.get(yCoords.size()-1)))
        //if not eating a dot, remove the last segment of the snake
        {
            xCoords.remove(0);
            yCoords.remove(0);
        }
        else{ //is eating a dot
            //regenerate food coordinates
            foodx = (int)(Math.random()*20);
            foody = (int)(Math.random()*15);
            score++;
        }

        
        //check for death; all output statements are for debugging and may be removed
        if(overlap())
        {
            System.out.println("You committed ouroboros!");
            return false;
        }
        if(19 < xCoords.get(xCoords.size()-1) || xCoords.get(xCoords.size()-1) < 0) //out of bounds - x
        {
            System.out.println("Out of bounds!");
            return false;
        }
        else if(14 < yCoords.get(yCoords.size()-1) || yCoords.get(yCoords.size()-1) < 0) //out of bounds - y
        {
            System.out.println("Out of bounds!");
            return false;
        }
        return true;
    }
    public boolean overlap() //see if the snake has collided with itself
    {
        for(int i=0;i<xCoords.size()-1;i++) //only need to check if the head is colliding with anything
        {
            if(xCoords.get(i) == xCoords.get(xCoords.size()-1))
            {
                if(yCoords.get(i) == yCoords.get(xCoords.size()-1))
                {
                    return true;
                }
            }
        }
        return false;
    }
    //main display of the game
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        int xintercept = 50,yintercept = 50;

        
        //draws a 20 by 15 field
        for(int i=0;i<20;i++)
        {
            for(int j=0;j<15;j++)
            {
                //draw square based on the sum of i and j
                Rectangle grid = new Rectangle(30*i+xintercept,30*j+yintercept,30,30);
                if((i+j)%2 == 1)
                {
                    g2.setColor(new Color(120,255,120));
                }
                else
                {
                    g2.setColor(new Color(50,255,50));
                }
                g2.fill(grid);
            }
        }    

        
        //draws snake
        g2.setColor(new Color(75,75,255)); 
        //soon to be added: individual head segment implementation
        for(int i=xCoords.size()-1;i>=0;i--)//place the snake body
        {
            //later element = earlier created segment
            Rectangle part2 = new Rectangle(30*xCoords.get(i)+xintercept,30*yCoords.get(i)+yintercept,30,30);
            g2.fill(part2);
        }

        
        //draws food
        g2.setColor(new Color(255,75,75));
        Ellipse2D.Double part = new Ellipse2D.Double(30*foodx+xintercept,30*foody+yintercept,30,30);
        g2.fill(part);
    }
}
