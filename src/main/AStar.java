package main;

import java.util.HashSet;
import java.util.LinkedList;

public class AStar {
    private int startID;
    private int endID;
    private LinkedList<Node> allNodes;
    private char dir;

    public AStar(int startID, int endID, LinkedList<Node> allNodes) {
        this.startID = startID;
        this.endID = endID;
        this.allNodes = allNodes;
        this.dir = 'N';
    }

    /**
     * The core of A*, setting all the gCost, hCost and direction of the path
     */
    private void findPathPreGame() {
        Node starting = getNodeFromID(startID);
        Node ending = getNodeFromID(endID);

        // list of nodes that are open to be evaluated
        LinkedList<Node> openNodes = new LinkedList<>();

        // Hashset of nodes has already been visited and evaluated
        HashSet<Node> visitedNodes = new HashSet<>();

        // adding the starting node to the openNodes list
        openNodes.add(starting);

        //HashMap<String, AlgoNode> Nodes = allNodes;

        // looping
        while(!openNodes.isEmpty()){

            Node currentNode = openNodes.getFirst();

            // traverse through the open list and find the node with the lowest fCost
            for (Node n: openNodes){
                if (lowerFCost(n,currentNode)){
                    currentNode = n;
                }
            }
            //remove the current node from the openNodes list and add it to the visited nodes list
            openNodes.remove(currentNode);
            visitedNodes.add(currentNode);

//            // if true, path has been found, then return
//            if (isEnd(currentNode)){return; }
//
//            for (AlgoNode neighbor: adjacenciesToNodes(currentNode) ){ //adjacencies has to list of  nodes
//
//                // we need to discuss about the conditions that make a node "walkable" - all our nodes are walkable
//                //skip to the next neighbor
//                if (visitedNodes.contains(neighbor))
//                    continue;
//
//                int newMovementCostToNeighbor = currentNode.get_gCost() + getDistance(currentNode,neighbor  );
//
//                if (newMovementCostToNeighbor < neighbor.get_gCost() || !openNodes.contains(neighbor)){
//                    neighbor.set_gCost(newMovementCostToNeighbor);
//                    neighbor.set_hCost(getDistance(neighbor,ending));
//                    neighbor.setParent(currentNode);
//
//                    if (!openNodes.contains(neighbor)) openNodes.add(neighbor);
//                }
//            }
        }
    }

    private Node getNodeFromID(int ID){
        try {
            for (Node n : allNodes) {
                if (ID == n.getID()) {
                    return n;
                }
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    public boolean lowerFCost(Node n , Node currentNode) {
        return n.getFCost() < currentNode.getFCost() ||
                (n.getFCost() == currentNode.getFCost() && n.getH() < currentNode.getH());
    }


}
