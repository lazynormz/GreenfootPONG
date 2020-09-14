import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int dx;
    private int paddleSpeed;
    
    private boolean isPlayer = false;
    
    private Ball ball;
    
    private PingWorld world;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height, boolean isPlayer, Ball ball)
    {
        this.width = width;
        this.height = height;
        this.isPlayer = isPlayer;
        this.ball = ball;
        
        this.world = (PingWorld) this.getWorld();
        
        dx = 1;
        paddleSpeed = 2;
        //createImage();
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Movement nest
        if(isPlayer){
            if(Greenfoot.isKeyDown("Left")){
                if(!(getX() - width/2 <= 0))
                {
                    setLocation(getX() - paddleSpeed, getY());
                }
            }else if(Greenfoot.isKeyDown("Right")){
                if(!(getX() + width/2 >= getWorld().getWidth()))
                {
                    setLocation(getX() + paddleSpeed, getY());
                }
            }
        }else{
            if(checkIfAtEdge()){
                replace();
            }
            setLocation(getX() + paddleSpeed, getY());
        }
        
        //Collision nest
        if(isTouching(Ball.class)){
            if(ball.getDirection() == Dir.DOWN){
                if(isPlayer){
                    ball.wasHit();
                    ball.revertVertically();
                }
            }else if(ball.getDirection() == Dir.UP){
                if(!isPlayer){
                    ball.revertVertically();
                }
            }
        }
    } 

    private boolean checkIfAtEdge(){
        return (getX() + width/2 >= getWorld().getWidth());
    }
    
    private void replace(){
        int x = 0 + width/2;
        int y = Greenfoot.getRandomNumber(PingWorld.WORLD_HEIGHT - (height + PingWorld.PADDING));
        setLocation(x, y);
    }
    
    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void tryChangeDirection()
    {
        //Check to see if we are touching the outer boundaries of the world:
        // IF we are touching the right boundary OR we are touching the left boundary:
        if(getX() + width/2 >= getWorld().getWidth() || getX() - width/2 <= 0)
        {
            //Change our 'x' direction to the inverted direction:
            dx = dx * -1;
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    private void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.BLACK);
        image.fill();
        setImage(image);
    }

}
