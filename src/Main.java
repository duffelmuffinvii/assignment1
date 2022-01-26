import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Node> map = new ArrayList<Node>();

        try {
            File file = new File("maps/assignment 1, sample board.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
