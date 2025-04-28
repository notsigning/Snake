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
    public int getDirection()
    {
        return direction;
    }
    public void setDirection(int a)
    {
        direction = a;
    }
    public Snake()
    {
        super();
        reset();
    }
    public void reset()
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
    public void addPoint(int x,int y) 
    {
        xCoords.add(x);
        yCoords.add(y);
    }
    public boolean move() //returns false if you end up dying, returns true otherwise
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
        if(!(foodx == xCoords.get(xCoords.size()-1) && foody == yCoords.get(yCoords.size()-1))) //if not eating a dot
        {
            xCoords.remove(0);
            yCoords.remove(0);
        }
        else{
            //regenerate food coordinates
            foodx = (int)(Math.random()*20);
            foody = (int)(Math.random()*15);
            score++;
        }
        //check for death
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
    public boolean overlap()
    {
        for(int i=0;i<xCoords.size()-1;i++) //only need to check last one
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
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        int xintercept = 50,yintercept = 50;
        //field
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
        //food
        
        //snake
        g2.setColor(new Color(75,75,255)); 
        //add first segment
        for(int i=xCoords.size()-1;i>=0;i--)//place the snake body
        {
            //later element = earlier created segment
            Rectangle part2 = new Rectangle(30*xCoords.get(i)+xintercept,30*yCoords.get(i)+yintercept,30,30);
            g2.fill(part2);
        }
        g2.setColor(new Color(255,75,75));
        Ellipse2D.Double part = new Ellipse2D.Double(30*foodx+xintercept,30*foody+yintercept,30,30);
        g2.fill(part);
        //add final segment
        //bonus: screw time complexity, when did you want to play usaco-paced snake
        

    }
}