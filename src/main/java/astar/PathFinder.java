package astar;
import tile.TileManager;

import java.util.ArrayList;

public class PathFinder {


    TileManager tileManager;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public int maxCol;
    public int maxRow;
public PathFinder(){

    tileManager = new TileManager(null, 60, 33, 32);
    maxCol = 59;
    maxRow = 32;
    instantiateNodes();
}
public void instantiateNodes() {
    node = new Node[maxCol][maxRow];

    int col = 0;
    int row = 0;

    while (col < maxCol && row < maxRow) {

        node[col][row] = new Node(col, row);
        col++;
        if (col == maxCol) {
            col = 0;
            row++;
        }
    }
}
public void resetNodes(){
    int col = 0;
    int row = 0;

    while (col < maxCol && row < maxRow) {

        node[col][row].open = false;
        node[col][row].checked = false;
        node[col][row].solid= false;
        col++;
        if (col == maxCol) {
            col = 0;
            row++;
        }
    }

    openList.clear();
    pathList.clear();
    goalReached = false;
    step =0;
}

public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
    resetNodes();

    startNode= node[startCol][startRow];
    currentNode = startNode;
    goalNode = node[goalCol][goalRow];
    openList.add(currentNode);

    int col = 0;
    int row = 0;

    while (col < maxCol && row < maxRow) {
        // set solid
        int tileNum = tileManager.getMapTileNum()[row][col];
        if(tileNum == 1){//todo
            node[col][row].solid = true;

        }

        getCost(node[col][row]);

        col++;
        if(col == maxCol){
            col=0;
            row++;
        }
    }


}

    private void getCost(Node node){
        //g cost(distance fromstart)
        int xDistance= Math.abs(node.col - startNode.col);
        int yDistance= Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //h cost ( distance from goal)
        xDistance= Math.abs(node.col - goalNode.col);
        yDistance= Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // f cost ( total)
        node.fCost = node.gCost + node.hCost;


    }

    public Boolean search(){
        while(!goalReached){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            // open the up node
            if(row-1 >=0) {
                openNode(node[col][row - 1]);
            }
            if(col-1 >=0) {
                // left node
                openNode(node[col - 1][row]);
            }
            if( row+1 < maxRow) {
                // down node
                openNode(node[col][row + 1]);
            }
            if(col +1 < maxCol) {
                // right node
                openNode(node[col + 1][row]);
            }

            //find best node
            int bestNodeIndex =0;
            int bestNodefCost = 999;

            for( int i=0; i< openList.size(); i++){
                //check if fcost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //if f cost equal use gcost
                else if( openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            if(openList.size() == 0){
                break;
            }

            // after loop we get best node -- next step
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }

        }

        return goalReached;
    }

    private void openNode(Node node){
        if( node.open == false && node.checked ==false && node.solid ==false){
            // oif node is not opnened, add to open list
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath(){
        Node current = goalNode;

        while(current != startNode){

            pathList.add(0,current);
            current = current.parent;

        }
    }


}
