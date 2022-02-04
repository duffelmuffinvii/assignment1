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
    private int score;
    private int actions;
    private boolean bash;
    private int expandedNode;

    public AStar(Node start, Node end, int[][] board, int heuristic) {
        this.start = start;
        this.end = end;
        this.heuristic = heuristic;
        this.board = board;
        this.xmax = board[0].length;
        this.ymax = board.length;
        this.score = 100;
        this.actions = 0;
        this.bash = false;
        this.expandedNode = 0;
    }

    public Node getFullPath() {
        if(start == null || end == null) {
            return null;
        }

        LinkedList<Node> openPath = new LinkedList<>();
        LinkedList<Node> closePath = new LinkedList<>();
        start.setG(0);
        start.setFCost(start.getG() + findHeuristic(start));
        openPath.add(start);

        while (!openPath.isEmpty()) {
            Node cur = openPath.peek();
            if(cur.getX() == end.getX() && cur.getY() == end.getY()){
                score = (int) (100 - cur.getG());
                return cur;
            }
            List<Node> curNeb = findNeighbors(cur);

            for(Node neb: curNeb){
                double totalWeight = cur.getG() + neb.getWeight();
                if(!listContainsNode(openPath, neb) && !listContainsNode(closePath, neb)){
                    neb.setParent(cur);
                    neb.setG(totalWeight + neb.getComplex());
                    neb.setFCost(neb.getG() + findHeuristic(neb));
                    openPath.add(neb);
                    expandedNode++;
                } else{
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
        }
        return null;
    };

    public boolean listContainsNode(LinkedList<Node> list, Node n) {
        for (Node m : list) {
            if (m.getX() == n.getX() && m.getY() == n.getY()) return true;
        }
        return false;
    }

    public void printPath(Node path){
        if(path == null){
            return;
        }

        List<Node> ns = new ArrayList();
        List<String> moves = new ArrayList<>();
        while(path.getParent() != null){
            ns.add(path);
            moves.add(checkMove(path.getParent(), path));
            path = path.getParent();
        }
        ns.add(path);
        Collections.reverse(ns);
        Collections.reverse(moves);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Score: " + score);
        System.out.println("Number of actions: " + actions);
        System.out.println("Number of expanded nodes: " + expandedNode);
        System.out.println("Series of actions: ");
        for(String s: moves){
            System.out.println(s);
        }
    }

    public String checkMove(Node start, Node end){
        // Hardcoding series of actions
        int startX = start.getX();
        int startY = start.getY();
        int startDir = start.getDir();
        double startF = start.getFCost();
        int endX = end.getX();
        int endY = end.getY();
        int endDir = end.getDir();
        double endF = end.getFCost();

        // Differences
        int difX = endX - startX;
        int difY = endY - startY;
        int difDir = endDir - startDir;
        int difF = (int) (endF - startF);
        int difFNoWeight = (int) (difF-end.getWeight());

        if(difDir == 0){
            // Same direction
            switch(startDir){
                case 0:
                case 2:
                    // North or South
                    if(Math.abs(difY) == 1){
                        String out = "Forward (" + difF + ")";
                        actions++;
                        return out;
                    }
                    if(Math.abs(difY) == 2){
                        String out = "Bash (3)" + "\nForward (" + (difF-3) + ")";
                        actions+=2;
                        return out;
                    }
                    break;
                case 1:
                case 3:
                    // East or West
                    if(Math.abs(difX) == 1){
                        String out = "Forward (" + difF + ")";
                        actions++;
                        return out;
                    }
                    if(Math.abs(difX) == 2){
                        String out = "Bash (3)" + "\nForward (" + (difF-3) + ")";
                        actions+=2;
                        return out;
                    }
                    break;
            }
        } else if(difDir == 1 || difDir == -3){
            // Turn Right
            String out = "Right (" + (int) end.getWeight() + ")"
                    + "\nForward (" + difFNoWeight + ")";
            actions+=2;
            return out;

        } else if(difDir == -1 || difDir == 3){
            // Turn Left
            String out = "Left (" + (int) end.getWeight() + ")"
                    + "\nForward (" + difFNoWeight  + ")";
            actions+=2;
            return out;
        } else{
            // Backward
            String out = "Rotate back (" + (int) end.getWeight() + ")"
                    + "\nForward (" + difFNoWeight  + ")";
            actions+=2;
            return out;
        }
        return "";
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
            double weight;

            if (n != null) {
                // Computes G value based on direction compared to direction robot is currently facing
                if (dir == i) {
                    // Forward direction
                    weight = 0;
                    if(i != 4){
                        n.setDir(i);
                    }
                } else if (i == 4){
                    // BASH
                    weight = 3;
                    n.setDir(dir);
                } else if (Math.abs(dir - i) == 1 || Math.abs(dir - i) == 3) {
                    // Left or right (adds cost of )
                    weight = Math.ceil(source.getComplex() / 2);
                    if((dir - i) == 1 || (dir - i) == -3){
                        // Turn Left
                        if(dir == 0){
                            n.setDir(3);
                        } else{
                            n.setDir(dir-1);
                        }
                    }
                    if((dir - i) == -1 || (dir - i) == 3){
                        // Turn Right
                        if(dir == 3){
                            n.setDir(0);
                        } else{
                            n.setDir(dir+1);
                        }
                    }
                } else {
                    // Backward direction (technically shouldn't occur at all except for at the start)
                    n.setDir(Math.abs(i));
                    weight = source.getComplex();
                }
                // Setup neighbor node
                n.setWeight(weight);
                neighbors.add(n);
            }
        }
        return neighbors;
    }

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
}
