package astar;


import java.awt.*;


public class Node  {
    Node parent;
    public int col,row, gCost, hCost, fCost;
    boolean start, goal, solid, open, checked;



    public Node( int col, int row){
        this.col = col;
        this.row = row;

    }
}
