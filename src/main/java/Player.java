import java.util.ArrayList;

public class Player extends Entity {
    // Attributes
    private int score = 0;
    private int lives = 5;
    private ArrayList<> inventory;

    // Constructor
    public Player() {
        inventory = new ArrayList<Weapon>();
    }

    // Getters
    public int  getScore(){ return this.score;}
    public int getLives(){ return this.lives;}

    public ArrayList getInventory() {
        return inventory;
    }

    // Setters
    public void setScore(int value){ this.score = value;}
    public void setLives(int value){ this.lives = value;}
    public void setInventory(ArrayList inventory) {
        inventory = inventory;
    }
    // Adders
    public void addScore(int dif){ this.score += dif;}
    public void addLives(int dif){ this.lives += dif;}

    // TODO: Hitbox config

    // TODO: Movement

    // TODO: Draw player

    // TODO : Object collision

    // TODO : enemy collision

    // TODO : see if dead




}


