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
        
        //dx = 1;
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
        if(isPlayer) {
            if(Greenfoot.isKeyDown("Left")) {
                if(!(getX() - width/2 <= 0)) {
                    setLocation(getX() - paddleSpeed, getY());
                }
            } else if(Greenfoot.isKeyDown("Right")){
                if (!(getX() + width/2 >= getWorld().getWidth()))
                {
                    setLocation(getX() + paddleSpeed, getY());
                }
            }
        } else {
            if(checkIfAtEdge()){
                replace();
            }
            setLocation(getX() + paddleSpeed, getY());
        }
        
        //Collision nest
        if(isTouching(Ball.class)) {
            if(ball.getDirection() == Dir.DOWN) {
                if(isPlayer) {
                    ball.wasHit();
                    ball.revertVertically();
                }
            } else if(ball.getDirection() == Dir.UP) {
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
}
