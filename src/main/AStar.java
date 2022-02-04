package main;

import java.text.Collator;
import java.util.*;

public class AStar {
    private Node start;
    private Node end;
    private int heuristic;
    private int[][] board;
    private int xmax;
    private int ymax;
    private double score;
    private int actions;
    private boolean bash;

    public AStar(Node start, Node end, int[][] board, int heuristic) {
        this.start = start;
        this.end = end;
        //this.allNodes = allNodes;
        this.heuristic = heuristic;
        this.board = board;
        this.xmax = board[0].length;
        this.ymax = board.length;
        this.score = 100;
        this.actions = 0;
        this.bash = false;
    }
//
//    public LinkedList<Node> getFullPath() {
//        LinkedList<Node> currentPath = new LinkedList<>();
//        currentPath.add(start);
//
//        while (!isAt(currentPath.getLast(), end)) {
//            //System.out.println("up to y " + currentPath.getLast().getY() + ", x " + currentPath.getLast().getX());
//            currentPath = path(currentPath);
//        }
//
//        return currentPath;
//    };

    public Node getFullPath() {
        if(start == null || end == null) {
            return null;
        }

        LinkedList<Node> openPath = new LinkedList<>();
        LinkedList<Node> closePath = new LinkedList<>();
        start.setG(0);
        start.setFCost(start.getG() + findHeuristic(start));
        openPath.add(start);
        int step=0;

        while (!openPath.isEmpty()) {
            Node cur = openPath.peek();
            System.out.println("");
            System.out.println("CURRENTLY AT " + cur);
            if(cur.getX() == end.getX() && cur.getY() == end.getY()){
                score = 100 - cur.getG();
                return cur;
            }

            //printStartNode(cur);
            List<Node> curNeb = findNeighbors(cur);
            //System.out.println("SIZE: " + curNeb.size());

            for(Node neb: curNeb){
                //printStartNode(neb);
                //System.out.println(!listContainsNode(closePath, neb));
                double totalWeight = cur.getG() + neb.getWeight();
                if(!listContainsNode(openPath, neb) && !listContainsNode(closePath, neb)){
//                    if(neb.getX() == 1 && neb.getY() == 0){
//                        System.out.println(cur.getG() + ", should be 7");
//                        System.out.println(neb.getComplex() + ", should be 1!");
//                        System.out.println(totalWeight + ", weight so far - 9");
//                    }
                    neb.setParent(cur);
                    neb.setG(totalWeight + neb.getComplex());
                    neb.setFCost(neb.getG() + findHeuristic(neb));
                    openPath.add(neb);
                } else{
                    //double cost = cur.getG() + (neb.getG()- neb.getComplex());
                    //System.out.println("COST: "+ cost);
                    if(totalWeight < neb.getG()){
                        neb.setParent(cur);
                        neb.setG(totalWeight + neb.getComplex());
                        neb.setFCost(neb.getG() + findHeuristic(neb));
                        if(listContainsNode(closePath, neb)){
                            closePath.remove(neb);
                            openPath.add(neb);
                        }
                    }
                }
            }
            Collections.sort(openPath);
            openPath.remove(cur);
            closePath.add(cur);
            step++;
//            System.out.println("DIRECTION: " + cur.getDir());
//            printPathQueue(openPath, "OPEN Step: " + step + " ");
//            //printPathQueue(closePath, "CLOSED Step: " + step);
//            if(step == 20){
//                break;
//            }
        }
        return null;
    };

    public boolean listContainsNode(LinkedList<Node> list, Node n) {
        for (Node m : list) {
            if (m.getX() == n.getX() && m.getY() == n.getY()) return true;
        }
        return false;
    }

    public void printStartNode(Node n){
        if(n.getX() == 2 && n.getY() == 2){
            System.out.println("START COST: " + n.getFCost());
        }
    }
    public void printPathQueue(LinkedList<Node> queue, String message){
        for(Node n: queue){
            System.out.println(message + n);
        }
    }
    public void printPath(Node path){
        if(path == null){
            return;
        }

        List<Node> ns = new ArrayList();
        while(path.getParent() != null){
            ns.add(path);
            path = path.getParent();
        }
        ns.add(path);
        Collections.reverse(ns);

        for(Node n: ns){
            System.out.println(n);
        }
    }


    /**
     * The core of A*, setting all the gCost, hCost and direction of the path
     */
    public List<Node> findNeighbors(Node source){
        List<Node> neighbors = new ArrayList<>();
        Node[] nebs = getAdj(source);
        int dir = source.getDir();
        for(int i=0; i<nebs.length; i++){
            Node n = nebs[i];
            //double g;
            double weight;

            if (n != null) {
                // Computes G value based on direction compared to direction robot is currently facing
//              System.out.println("Facing direction: " + dir);
                //g = n.getComplex();
                if (dir == i) { //|| i == 4
                    // Forward direction or bash
                    weight = 0;
                    if(i != 4){
                        n.setDir(i);
                    }
                } else if (Math.abs(dir - i) == 1 || Math.abs(dir - i) == 3) {
                    // Left or right (adds cost of )
                    weight = source.getComplex() / 2;
                    n.setDir(Math.abs(dir-i));
                } else if (i==4){
                    weight = 3;
                }
                else {
                    // Backward direction (technically shouldn't occur at all except for at the start)
                    n.setDir(Math.abs(i));
                    weight = source.getComplex();
                }

                // Setup neighbor node
                n.setWeight(weight);
//                if(n.getX() == 1 && n.getY() == 0){
//                    System.out.println(weight + "should be 2");
//                }
                neighbors.add(n);
            }
        }
        //System.out.println("SIZE: " +neighbors.size());
        return neighbors;
    }



//    public LinkedList<Node> path(LinkedList<Node> path) {
//        Node s = path.getLast();
//        Node[] adj = getAdj(s);
//
//        double fVals[] = new double[5];
//        double gVals[] = new double[5];
//
//        for (int i = 0; i < adj.length; i++) {
//            Node n = adj[i];
//            double h;
//            double g;
//            if (n != null) {
//                h = findHeuristic(n);
//            } else h = 99;
//
//            if (n != null) {
//                // Computes G value based on direction compared to direction robot is currently facing
//                //System.out.println("Facing direction: " + dir);
////                if(bash){
////                    // Only forward
////                    if (dir == i){
////                        g = n.getComplex();
////                        bash = false;
////                        //System.out.println("Forward after bash: "+ g);
////                        fVals[i] = h + g;
////                        gVals[i] = g;
////                        continue;
////                    }
////                }
//
//                if (dir == i || i == 4) {
//                    // Forward direction or bash
//                    g = n.getComplex();
//                } else if (Math.abs(dir - i) == 1 || Math.abs(dir - i) == 3) {
//                    // Left or right (adds cost of )
//                    g = s.getComplex() / 2 + n.getComplex();
//                } else {
//                    // Backward direction (technically shouldn't occur at all except for at the start)
//                    g = s.getComplex() + n.getComplex();
//                }
//            } else g = 99;
//
//            fVals[i] = h + g;
//            gVals[i] = g;
//        }
//
//        double min = 100;
//        int minIndex = 0;
//        for (int i = 0; i < fVals.length; i++) {
//            if (adj[i] == null || pathContains(path, adj[i])) continue;
//            if (adj[i].getX() == end.getX() && adj[i].getY() == end.getY()) {
//                //System.out.println("Complex at the end: " + end.getComplex());
//                minIndex = i;
//                break;
//            }
//            if (fVals[i] < min) {
//                min = fVals[i];
//                minIndex = i;
//            }
//        }
//
//        System.out.println("Cost: " + fVals[minIndex]);
//        path.add(adj[minIndex]);
//        dir = minIndex;
//        actions++;
//        score -= gVals[minIndex];
//        switch (minIndex) {
//            case 0:
//                System.out.println("North");
//                break;
//            case 1:
//                System.out.println("East");
//                break;
//            case 2:
//                System.out.println("South");
//                break;
//            case 3:
//                System.out.println("West");
//                break;
//            case 4:
//                System.out.println("Bash");
//                break;
//        }
//        return path;
//
//    }

    public Node[] getAdj(Node n) {
        Node[] adj = new Node[5];
        int dir = n.getDir();
        int x = n.getX();
        int y = n.getY();

        boolean north = coordInBounds(x, y-1);
        boolean east = coordInBounds(x+1, y);
        boolean south = coordInBounds(x, y+1);
        boolean west = coordInBounds(x-1, y);

        //N
        if (north){
            adj[0] = new Node(x, y-1, board[y-1][x]);
        } else adj[0] = null;

        //E
        if (east){
            adj[1] = new Node(x+1, y, board[y][x+1]);
        } else adj[1] = null;

        //S
        if (south){
            adj[2] = new Node(x, y+1, board[y+1][x]);
        } else adj[2] = null;

        //W
        if (west){
            adj[3] = new Node(x-1, y, board[y][x-1]);
        } else adj[3] = null;

        // Bash
        switch(dir){
            case 0:
                if(coordInBounds(x, y-2)){
                    adj[4] = new Node(x, y-2, board[y-2][x]);
                } else adj[4] = null;
                break;
            case 1:
                if(coordInBounds(x+2, y)){
                    adj[4] = new Node(x+2, y, board[y][x+2]);
                } else adj[4] = null;
                break;
            case 2:
                if(coordInBounds(x, y+2)){
                    adj[4] = new Node(x, y+2, board[y+2][x]);
                } else adj[4] = null;
                break;
            case 3:
                if(coordInBounds(x-2, y)){
                    adj[4] = new Node(x-2, y, board[y][x-2]);
                } else adj[4] = null;
                break;
        }
        return adj;

    }

    public boolean coordInBounds(int x, int y) {
        return x < xmax && x >= 0 && y < ymax && y >= 0;
    }

    private double findHeuristic(Node n){
        double h;
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
            case 5:
                h = euclideanDistance(n, end);
                break;
            case 6:
                h = nonAdmissible(n, end);
                break;
            default:
                h = 0;
                break;
        }
        return h;
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

    private double euclideanDistance(Node nodeA, Node nodeB)
    {
        double dx = Math.abs(nodeA.getX() - nodeB.getX());
        double dy = Math.abs(nodeA.getY() - nodeB.getY());
        return Math.sqrt(dx*dx + dy*dy);
    }

    private double nonAdmissible(Node nodeA, Node nodeB)
    {
        return 3 * euclideanDistance(nodeA, nodeB);
    }


    public boolean pathContains(LinkedList<Node> p, Node n) {
        for (Node x : p) {
            if (x.getX() == n.getX() && x.getY() == n.getY()) return true;
        }
        return false;
    }

    public boolean isAt(Node a, Node b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    public int getActions() {
        return actions;
    }

    public double getScore() {
        return score;
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
