package main;

import java.util.HashSet;
import java.util.LinkedList;

public class AStar {
    private int startID;
    private int endID;
    private LinkedList<Node> allNodes;
    private char dir;
    private int heuristic;

    public AStar(int startID, int endID, LinkedList<Node> allNodes, int heuristic) {
        this.startID = startID;
        this.endID = endID;
        this.allNodes = allNodes;
        this.dir = 'N';
        this.heuristic = heuristic;
    }

    /**
     * The core of A*, setting all the gCost, hCost and direction of the path
     */

    public LinkedList<Node> path(LinkedList<Node> path) {
        Node s = path.getLast();
        Node[] adj = getAdj(s);

        double fVals[] = new double[4];

        for (int i = 0; i < adj.length; i++) {
            Node n = adj[i];
            double h;
            double g;
            if (n != null) {
                switch (heuristic) {
                    case 1:
                        h = 0;
                        break;
                    case 2:
                        h = getLowerDist(n, end);
                        break;
                    case 3:
                        h = getHigherDist(n, end);
                        break;
                    case 4:
                        h = sumDist(n, end);
                        break;
                    default:
                        h = 0;
                        break;
                }
            }
        }
        
        double min = fVals[0];
        int minIndex = 0;
        for (int i = 0; i < fVals.length; i++) {
            if (adj[i].getX() == end.getX() && adj[i].getY() == end.getY()) {
                minIndex = i;
                break;
            }
            if (fVals[i] < min) {
                min = fVals[i];
                minIndex = i;
            }
        }
        
        path.add(adj[minIndex]);
        return path;

    }

    public Node[] getAdj(Node n) {
        Node[] adj = new Node[4];
        int x = n.getX();
        int y = n.getY();

        //N
        if (coordInBounds(x, y+1)){
            adj[0] = new Node(x, y+1, board[x][y+1]);
        } else adj[0] = null;

        //E
        if (coordInBounds(x+1, y)){
            adj[1] = new Node(x+1, y, board[x+1][y]);
        } else adj[1] = null;

        //S
        if (coordInBounds(x, y-1)){
            adj[2] = new Node(x, y-1, board[x][y-1]);
        } else adj[2] = null;

        //W
        if (coordInBounds(x-1, y)){
            adj[3] = new Node(x-1, y, board[x-1][y]);
        } else adj[3] = null;

        return adj;

    }

    public boolean coordInBounds(int x, int y) {
        return x < xmax && x >= 0 && y < ymax && y >= 0;
    }

    private double getLowerDist(Node nodeA, Node nodeB) {
        double xdist = Math.abs(nodeA.getX() - nodeB.getX());
        double ydist = Math.abs(nodeA.getY() - nodeB.getY());

        if (xdist < ydist) return xdist;
        else return ydist;
    }

    private double getHigherDist(Node nodeA, Node nodeB) {
        double xdist = Math.abs(nodeA.getX() - nodeB.getX());
        double ydist = Math.abs(nodeA.getY() - nodeB.getY());

        if (xdist > ydist) return xdist;
        else return ydist;
    }

    private double sumDist(Node nodeA, Node nodeB) {
        return Math.abs(nodeA.getX() - nodeB.getX()) + Math.abs(nodeA.getY() - nodeB.getY());
    }

//    private void findPath1() {
//        Node starting = getNodeFromID(startID);
//        Node ending = getNodeFromID(endID);
//
//        // list of nodes that are open to be evaluated
//        LinkedList<Node> openNodes = new LinkedList<>();
//
//        // Hashset of nodes has already been visited and evaluated
//        HashSet<Node> visitedNodes = new HashSet<>();
//
//        // adding the starting node to the openNodes list
//        openNodes.add(starting);

    // looping
//        while(!openNodes.isEmpty()){
//
//            Node currentNode = openNodes.getFirst();
//
//            // traverse through the open list and find the node with the lowest fCost
//            for (Node n: openNodes){
//                if (lowerFCost(n,currentNode)){
//                    currentNode = n;
//                }
//            }
//            //remove the current node from the openNodes list and add it to the visited nodes list
//            openNodes.remove(currentNode);
//            visitedNodes.add(currentNode);
//
//            // if true, path has been found, then return
//            if (isEnd(currentNode)){return; }
//
//            for (Node neighbor: adjacenciesToNodes(currentNode) ){ //adjacencies has to list of  nodes
//
//                // we need to discuss about the conditions that make a node "walkable" - all our nodes are walkable
//                //skip to the next neighbor
//                if (visitedNodes.contains(neighbor))
//                    continue;
//
//                double h;
//
//                switch (heuristic) {
//                    case 0:
//                        h = 0;
//                        break;
//                    case 1:
//                        h = getLowerDist(currentNode, neighbor);
//                        break;
//                    case 2:
//                        h = getHigherDist(currentNode, neighbor);
//                        break;
//                    case 3:
//                        h = sumDist(currentNode, neighbor);
//                        break;
//                    default:
//                        h = getDistance(currentNode, neighbor);
//                }
//
//                double newMovementCostToNeighbor = currentNode.getG() + h;
//
//                if (newMovementCostToNeighbor < neighbor.getG() || !openNodes.contains(neighbor)){
//                    neighbor.setG(newMovementCostToNeighbor);
//
//                    switch (heuristic) {
//                        case 0:
//                            neighbor.setH(0);
//                            break;
//                        case 1:
//                            neighbor.setH(getLowerDist(neighbor, ending));
//                            break;
//                        case 2:
//                            neighbor.setH(getHigherDist(neighbor, ending));
//                            break;
//                        case 3:
//                            neighbor.setH(sumDist(neighbor, ending));
//                            break;
//                        default:
//                            neighbor.setH(getDistance(neighbor, ending));
//
//                    }
//                    neighbor.setParent(currentNode);
//
//                    if (!openNodes.contains(neighbor)) openNodes.add(neighbor);
//                }
//            }
//        }
//    }

//    public LinkedList<Node> adjacenciesToNodes(Node node) {
//        LinkedList<Node> list = new LinkedList<>();
//        for (int s : node.getAdjacencies()) {
//            for (Node n : allNodes) {
//                if (n.getID() == s) list.add(n);
//            }
//        }
//        return list;
//    }

//    private Node getNodeFromID(int ID){
//        try {
//            for (Node n : allNodes) {
//                if (ID == n.getID()) {
//                    return n;
//                }
//            }
//        }
//        catch (Exception e){
//            return null;
//        }
//        return null;
//    }

//    public boolean lowerFCost(Node n , Node currentNode) {
//        return n.getFCost() < currentNode.getFCost() ||
//                (n.getFCost() == currentNode.getFCost() && n.getH() < currentNode.getH());
//    }
//
//    public boolean isEnd(Node n) {
//        return n.getID() == endID;
//    }
//
//    private double getDistance(Node nodeA, Node nodeB){
//        double nodeAXCoordinate = nodeA.getX();
//        double nodeAYCoordinate = nodeA.getY();
//        double nodeBXCoordinate = nodeB.getX();
//        double nodeBYCoordinate = nodeB.getY();
//
//        double xDifference = nodeBXCoordinate - nodeAXCoordinate;
//        double yDifference = nodeBYCoordinate - nodeAYCoordinate;
//
//        return (int)Math.sqrt(xDifference*xDifference + yDifference*yDifference);
//    }
//


}
