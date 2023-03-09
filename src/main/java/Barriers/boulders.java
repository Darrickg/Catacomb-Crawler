package Barriers;

public class boulders extends barriers implements obstruction {
    public boolean canPass() {
        return false;
    }

    public boolean pushed() {
        // i dont get the implementation here
        return false;
    }
}
