package Barriers;

public interface obstruction {
    public default boolean canPass() {
        // wait what is defined as canPass here?
        return true;
    }
}
