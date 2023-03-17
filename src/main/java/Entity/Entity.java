package Entity;

/**
 * The Entity class represent a generic entity object
 */
public class Entity {
    public int x,y;
    public int speed;

    /**
     * getter for getting entity horizontal position
     * @return dimension x
     */
    public int getHorizontalPosition(){
        return x;
    }

    /**
     * getter for getting entity vertical position
     * @return dimension y
     */
    public int getVerticalPosition(){
        return y;
    }


}
