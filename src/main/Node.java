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
    private double complex;
    // Neighbors
    private List<Node> neighbors;

    enum Directions{
        NORTH,
        WEST,
        EAST,
        SOUTH
    }

    public Node(int x, int y, int complex) {
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<>();
        this.complex = complex;
    }

//    private void initNeighbor(){
//        switch(dir){
//            case NORTH:
//
//            case WEST:
//            case EAST:
//            case SOUTH:
//            default:
//                break;
//        }
//    }

//    public boolean inBound(int x, int y){
//        return x >= 0 && x <= xmax && y >= 0 && y <= ymax;
//    }

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
        return g + h;
    }

    public double getComplex() {return this.complex;}

    public List<Node> getNeighbors() {
        return neighbors;
    }
    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.getFCost(), n.getFCost());
    }
}