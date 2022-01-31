package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Node> map = new ArrayList<Node>();

        try {
            /*File file = new File("maps/assignment 1, sample board.txt");
            Scanner scanner = new Scanner(file);
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int x = 0;
                for (int i = 0; i < line.length(); i++) {
                    char curr = line.charAt(i);
                    if (Character.isDigit(curr)) {
                        //map.add(new Node(x, y, Integer.parseInt(String.valueOf(curr))));
                        x++;
                    }
                    if (curr == 'S') {x++;}
                    if (curr == 'G') {
                        //map.add(new Node(x, y, 0));
                    }
                }
                y++;
            }
            scanner.close(); */
            
            String[][] board; 
            String temp = "";
            ArrayList<String> t = new ArrayList<String>();
            File file = new File("maps/assignment 1, sample board.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                temp = scanner.nextLine();
                t.add(temp);
            }
            scanner.close();
            int r_len = t.size();
            int c_len = (t.get(0).length() + 1) / 2;

            board = new String[r_len][c_len];
            int col;

            for(int q = 0; q < t.size(); q++)
            {
                temp = t.get(q);
                for (int s = 0; s < temp.length(); s++) {
                    if (!temp.charAt(s).equals("  ")) {
                        board[q][s / 2] = temp;
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Node n : map) {
                //System.out.println(n.x + ", " + n.y + ": " + n.cmplxty);
        }
        
        //code to generate a board
        int i = 3;
        int j = 4;
        String[][] b0 = generateBoard(i, j);

    }
    
    public String[][] generateBoard(int i, int j) //pass in desired row and column
    {
        String[][] b = new String[i][j];
        int[] i_pos = new int[i];
        int[] j_pos = new int[j];
        int temp;

        for(int u = 0; u < i; u++) //generate list of all possible col positions
        {
            i_pos.add(u);
        }
        for(int v = 0; v < j; v++) //generate list of all possible col positions
        {
            j_pos.add(v);
        }

        int g_r = i_pos.get((int) (Math.random()*(i_pos.length))); //give a row spot to the goal state
        int g_c = j_pos.get((int) (Math.random()*(j_pos.length))); //give a col spot to the goal state
        i_pos.remove(g_r);
        j_pos.remove(g_c);
        b[g_r][g_c] = "G";
        int s_r = i_pos.get((int) (Math.random()*(i_pos.length))); //repeating above with start spot
        int s_c = j_pos.get((int) (Math.random()*(j_pos.length)));
        b[s_r][s_c] = "S";


        for(int x = 0; x < b.length; x++)
        {
            for(int y = 0; y < b[0].length; y++)
            {
                if(b[x][y] == null)
                {
                    temp = (int)(Math.random()*(9 - 1) + 1);
                    b[x][y] = String.valueOf(temp);
                }
            }
        }

        return b;
    }
}
