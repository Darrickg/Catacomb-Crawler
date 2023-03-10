package Rewards;

public class bonus extends rewards {
    private int life_time;

    public bonus(int x, int y, int rewardWidth, int rewardHeight, int value) {
        super(x, y, rewardWidth, rewardHeight, value);
    }

    public void dissapear() {
        if (this.life_time == 0)
        {
            // how do i make this dissapear?
            this.remove();
        }
        else
        {
            System.out.println("life time not 0 yet");
        }
    }
}
