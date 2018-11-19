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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class madvet {

    private static int MAX_ANIMALS = 3;

    private static class AnimalList {
        // Animals are represented as numbers
        public int[] animals;
        public int numSteps;

        AnimalList(int[] animals, int numSteps) {
            this.animals = animals;
            this.numSteps = numSteps;
        }
    }

    private static class Laboratory {
        private int labNum;
        private int numQuestions;
        private int[] desiredAnimals = new int[MAX_ANIMALS];
        private int[][] conversions = new int[3][];

        Laboratory(int labNum, int numQuestions) {
            this.labNum = labNum;
            this.numQuestions = numQuestions;
        }

        public void inputConversions(Scanner fin) {
            conversions[0] = new int[]{fin.nextInt(), fin.nextInt(), fin.nextInt()};
            conversions[1] = new int[]{fin.nextInt(), fin.nextInt(), fin.nextInt()};
            conversions[2] = new int[]{fin.nextInt(), fin.nextInt(), fin.nextInt()};
        }

        // Forward conversions change one animal to one or more animals
        // Takes an AnimalList and returns a new AnimalList

        private int[] forward(int[] animals, int which) {
            // If there is at least one "which" animal to convert from
            int[] newAnimals = animals;
            if (newAnimals[which] >= 1) {
                newAnimals[which]--;
                for (int i = 0; i < MAX_ANIMALS; i++) {
                    newAnimals[i] += conversions[which][i];
                }
            }
            return newAnimals;
        }

        // Reverse conversions change one or more animals to one animal
        // There will be three of them
        private int[] reverse(int[] animals, int toWhich) {
            int[] newAnimals = animals;

            // If there is at least one of each animal to convert from
            // in animals
            boolean wasTrue = true;
            for (int i = 0; i < MAX_ANIMALS; i++) {
                if (animals[i] < conversions[toWhich][i]) {
                    wasTrue = false;
                }
            }

            // Convert
            if (wasTrue) {
                for (int i = 0; i < MAX_ANIMALS; i++) {
                    newAnimals[i] -= conversions[toWhich][i];
                }
                newAnimals[toWhich]++;
            }

            return newAnimals;
        }
    }

    public static void main (String[] args) throws IOException {
        Scanner fin = new Scanner(new File("madvet.in"));
        PrintWriter fout = new PrintWriter("madvet.out");

        int numDataSets = fin.nextInt();
        for (int i = 0; i < numDataSets; i++) {
            Laboratory lab = new Laboratory(fin.nextInt(), fin.nextInt());
            lab.inputConversions(fin);
        }
    }
}
