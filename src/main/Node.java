package main;

public class Node implements Comparable{
    private Node parent;
    private int x, y;
    // Cost so far
    private double g;
    // Heuristic
    private double h;
    private LinkedList<Node> adj;
    private int ID;

    public Node(Node parent, LinkedList<Node> adj, int ID) {
        this.parent = parent;
        this.adj = adj;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}