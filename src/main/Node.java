package main;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    private int ID;
    private int x, y;
    private Node parent = null;
    // Cost so far
    private double g;
    // Heuristic
    private double h;
    private double f;
    // Extra cost (turning) prior to move
    private double weight;
    // Direction
    private int dir;
    // Neighbors
    private List<Node> neighbors;

    enum Directions{
        NORTH,
        WEST,
        EAST,
        SOUTH
    }

    public Node(int x, int y, int g) {
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<>();
        this.g = g;
        this.weight = 0;
    }

    public int getID() {
        return ID;
    }

    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
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
    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }
    public void setH(double h) {
        this.h = h;
    }

    public double getFCost() {
       return f = g + h;
    }
    public void setFCost(double f){this.f = f;}

    public List<Node> getNeighbors() {
        return neighbors;
    }
    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }

    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;}

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.f, n.f);
    }

    @Override
    public String toString(){
        return "Node at ["+ x + ", "+ y + "] with cost of: " + g;
    }
}