import greenfoot.*;

/**
 * The Ping World is where Balls and Paddles meet to play pong.
 * 
 * @author The teachers 
 * @version 1
 */
public class PingWorld extends World
{
    public static final int WORLD_WIDTH = 500;
    public static final int WORLD_HEIGHT = 700;
    
    public static final int PADDING = 120;
    public static final int PLAYER_HEIGHT = 50;

    private GreenfootSound bounce;
    private GreenfootSound gameOver;
    
    private Ball ball;
    
    private GreenfootImage background;
    
    private Label label;
    
    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        
        bounce = new GreenfootSound("bounce.wav");
        gameOver = new GreenfootSound("go.wav");
        
        if (gameStarted)
        {
            background = getBackground();
            background.setColor(Color.WHITE);
            
            
            ball = new Ball();
            label = new Label("Level: " + ball.getLevel());
            
            addObject(label, 100, 40);
            addObject(ball, WORLD_WIDTH/2, WORLD_HEIGHT/2);
            
            int randomHeight = Greenfoot.getRandomNumber(WORLD_HEIGHT - (PLAYER_HEIGHT + PADDING));
            
            addObject(new Paddle(100, 20, false, ball), 60, randomHeight);
            addObject(new Paddle(100, 20, true, ball), 60, WORLD_HEIGHT - PLAYER_HEIGHT);
            
            //DrawLevel();
        }
        else
        {
            Greenfoot.setWorld(new IntroWorld());
        }
    }

    public void DrawLevel(){
        //background.drawString("Level: " + ball.getLevel(), 100,100);
    }
    
    public Label getLabel(){
        return label;
    }
    
    public void bounce(){
        bounce.play();
    }
    
    public void gameOver(){
        gameOver.play();
    }
    
}
