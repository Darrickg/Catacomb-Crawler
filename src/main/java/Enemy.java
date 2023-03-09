import Entity.Player;

public abstract class Enemy extends Entity{
        protected int damage; // amount of damage the enemy does to the player

        public Enemy(int damage) {
            this.damage = damage;
        }

        public int getDamage() {
            return damage;
        }

        // abstract method to handle enemy behavior
        public abstract void handleCollision(Player player);
    }



