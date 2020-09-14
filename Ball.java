import greenfoot.*;


/**
 * A Ball is a thing that bounces of walls and paddles (or at least i should).
 * 
 * @author The teachers 
 * @version 1
 */
public class Ball extends Actor
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 15;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;

    private Dir currentDirection;
    
    private int timesHit;
    private int level;
    private int speed;
    
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;

    /**
     * Initialize the ball settings.
     */
    private void init()
    {
        level = 1;
        speed = 2 + (level - 1);
        timesHit = 0;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH)+STARTING_ANGLE_WIDTH/2);
        currentDirection = Dir.DOWN;
    }
    
    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball()
    {
        //createImage();
        init();
    }

    /**
     * Creates and sets an image of a black ball to this actor.
     */
    private void createImage()
    {
        GreenfootImage ballImage = new GreenfootImage(BALL_SIZE,BALL_SIZE);
        ballImage.setColor(Color.BLACK);
        ballImage.fillOval(0, 0, BALL_SIZE, BALL_SIZE);
        setImage(ballImage);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            move(speed + (level - 1));
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkRestart();
        }
    }    

    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }

    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball is bouncing off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingSides())
        {
            if (! hasBouncedHorizontally)
            {
                revertHorizontally();
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }

    /**
     * Check to see if the ball should bounce off the ceiling.
     * If touching the ceiling the ball is bouncing off.
     */
    private void checkBounceOffCeiling()
    {
        if (isTouchingCeiling())
        {
            if (! hasBouncedVertically)
            {
                revertVertically();
            }
        }
        else
        {
            hasBouncedVertically = false;
        }
    }
    
    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkRestart()
    {
        if (isTouchingFloor())
        {
            Greenfoot.playSound("go.wav");
            init();
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        }
    }

    /**
     * Bounces the ball back from a vertical surface.
     */
    private void revertHorizontally()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX) - BOUNCE_DEVIANCE_MAX / 2;
        setRotation((180 - getRotation()+ randomness + 360) % 360);
        hasBouncedHorizontally = true;
        Greenfoot.playSound("bounce.wav");
    }

    /**
     * Bounces the bal back from a horizontal surface.
     */
    public void revertVertically()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX);//- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((360 - getRotation()+ randomness + 360) % 360);
        hasBouncedVertically = true;
        if(currentDirection == Dir.DOWN)
            currentDirection = Dir.UP;
        else
            currentDirection = Dir.DOWN;
            
        Greenfoot.playSound("bounce.wav");
    }

    public void wasHit(){
        timesHit++;
        if(timesHit % 10 == 0){
            level++;
            PingWorld w = (PingWorld) this.getWorld();
            w.getLabel().setText("Level: " + this.level);
            timesHit = 0;
        }
    }
    
    public int getLevel(){
        return this.level;
    }
    
    public Dir getDirection(){
        return this.currentDirection;
    }
    
}
