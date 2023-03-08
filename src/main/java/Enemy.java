public abstract class Enemy {
    private int x;
    private int y;
    protected int damage; // amount of damage the enemy does to the player

    public Enemy(int x, int y,int damage) {
        this.damage = damage;
        this.x =x;
        this.y=y;
        }


    public int getDamage() {
            return damage;
        }

    // override the abstract method to handle moving enemy behavior
    public abstract void handleCollision(Player player);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}



