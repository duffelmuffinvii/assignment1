package main;

import java.util.LinkedList;

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

    public Node getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public LinkedList<Node> getAdj() {
        return adj;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public double getFCost() {
        return g + h;
    }
}