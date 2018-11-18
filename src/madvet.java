// ------------------------------------------
// madvet.java
// Jeremy Campbell
// Problem 26 in Applied Algorithms, practice
// using a Breadth First Search
// ------------------------------------------

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class madvet {

    private static class ConversionMachine {
        private int first;
        private int second;
        private int third;

        ConversionMachine(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    private static class Laboratory {
        private int labNum;
        private int numQuestions;
        private List<ConversionMachine> machines = new ArrayList<>(3);

        Laboratory(int labNum, int numQuestions) {
            this.labNum = labNum;
            this.numQuestions = numQuestions;
        }

        void initMachines(Scanner fin) {
            for (ConversionMachine m : machines) {
                // Read in the three animals that can be converted to
                // in "forward" mode
                m = new ConversionMachine(fin.nextInt(), fin.nextInt(), fin.nextInt());
            }
        }
    }

    public static void main (String[] args) throws IOException {
        Scanner fin = new Scanner(new File("madvet.in"));
        PrintWriter fout = new PrintWriter("madvet.out");

        int numDataSets = fin.nextInt();
        for (int i = 0; i < numDataSets; i++) {
            Laboratory lab = new Laboratory(fin.nextInt(), fin.nextInt());

        }
    }
}
