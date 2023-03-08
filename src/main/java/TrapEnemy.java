public class TrapEnemy extends Enemy {

    public TrapEnemy(int x, int y,int damage) {
        super(x,y,damage);
    }

    // override the abstract method to handle trap enemy behavior
    @Override
    public void handleCollision(Player player) {
        player.takeDamage(damage);
        // TODO : remove the trap enemy from the game, or mark it as triggered so it won't cause damage again
        //
    }

    // trap enemies don't move, so no need for movement methods

}
