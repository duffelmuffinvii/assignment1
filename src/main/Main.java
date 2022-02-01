package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //take in file name (of the board) and number of which heuristic to use
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter File Name (for example, Board.txt)");
        String file_name = scan.nextLine();

        System.out.println("Enter Heuristic Number (1 through 6):");
        int heuristic = Integer.parseInt(scan.nextLine());

        String temp = "";
        ArrayList<String[]> t = new ArrayList<>();
        int inputHeuristic = 1;
        try{
            File file = new File(args[0]);
            inputHeuristic = Integer.parseInt((args[1]));
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                temp = scanner.nextLine();
                String[] line = temp.split("\t");
                t.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int r_len = t.size();
        int c_len = t.get(0).length;
        int[][] board = new int[r_len][c_len];
        Node start;
        Node end;

            for(int i = 0; i < t.size(); i++)
            {
                for (int j = 0; j < t.get(0).length; j++) {
                    System.out.println(t.get(i)[j]);
                    if (t.get(i)[j].equals("S")) {
                        start = new Node(i, j, 1);
                        //System.out.println("s");
                    } else if (t.get(i)[j].equals("G")) {
                        end = new Node(i, j, 1);
                        //System.out.println("g");
                    }
                }
            }
            printBoard(board);


        
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
