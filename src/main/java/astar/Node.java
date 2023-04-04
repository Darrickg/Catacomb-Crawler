package astar;

/**
 * The PathFinder function base on Node class
 */
public class Node  {
    Node parent;
    public int col,row, gCost, hCost, fCost;
    boolean start, goal, solid, open, checked;


    /**
     * The node takes col and row as input
     * @param col col
     * @param row row
     */
    public Node( int col, int row){
        this.col = col;
        this.row = row;

    }
}
