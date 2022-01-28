package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Node> map = new ArrayList<Node>();

        try {
            File file = new File("maps/assignment 1, sample board.txt");
            Scanner scanner = new Scanner(file);
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int x = 0;
                for (int i = 0; i < line.length(); i++) {
                    char curr = line.charAt(i);
                    if (Character.isDigit(curr)) {
                        map.add(new Node(x, y, Integer.parseInt(String.valueOf(curr))));
                        x++;
                    }
                    if (curr == 'S') {x++;}
                    if (curr == 'G') {
                        map.add(new Node(x, y, 0));
                    }
                }
                y++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Node n : map) {
                System.out.println(n.x + ", " + n.y + ": " + n.cmplxty);
        }

    }
}
