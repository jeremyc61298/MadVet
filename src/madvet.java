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
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class madvet {

    private static int MAX_ANIMALS = 3;
    private static int MAX_STEPS = 30;

    private static class AnimalList {
        // Animals are represented as numbers
        public ArrayList<Integer> animals = new ArrayList(MAX_ANIMALS);
        public int numSteps;

        AnimalList() {}

        AnimalList(ArrayList<Integer> animals, int numSteps) {
            this.animals = animals;
            this.numSteps = numSteps;
        }
    }

    private static class Question {
        public int answer;
        public AnimalList initialAnimals;
        public AnimalList finalAnimals;

        Question() {
            initialAnimals = new AnimalList();
            finalAnimals = new AnimalList();
        }
    }

    private static class Laboratory {
        private int labNum;
        private int numQuestions;
        private ArrayList<ArrayList<Integer>> conversions = new ArrayList<>(MAX_ANIMALS);
        private ArrayList<Question> questions;

        Laboratory(int labNum, int numQuestions) {
            this.labNum = labNum;
            this.numQuestions = numQuestions;
            this.questions = new ArrayList<>(numQuestions);
        }

        public void inputConversions(Scanner fin) {
            for (int i = 0; i < MAX_ANIMALS; i++)
                conversions.add(new ArrayList<>(Arrays.asList(fin.nextInt(), fin.nextInt(), fin.nextInt())));
        }

        public void inputQuestions(Scanner fin) {
            for (int i = 1; i <= numQuestions; i++) {
                int num = fin.nextInt();
                questions.add(num - 1, new Question());
                for (int j = 0; j < MAX_ANIMALS; j++) {
                    questions.get(num - 1).initialAnimals.animals.add(j, fin.nextInt());
                }

                for (int j = 0; j < MAX_ANIMALS; j++) {
                    questions.get(num - 1).finalAnimals.animals.add(j,fin.nextInt());
                }
            }
        }

        // Implements a depth first search
        public void solve() {
            for (int i = 0; i < numQuestions; i++) {
                boolean solution = false;
                LinkedList<AnimalList> aQueue = new LinkedList<>();
                Set<ArrayList<Integer>> aSet = new HashSet<>();
                questions.get(i).answer = 0;

                // Mark and insert the initial animalList into the queue
                aSet.add(questions.get(i).initialAnimals.animals);
                aQueue.add(new AnimalList(questions.get(i).initialAnimals.animals, questions.get(i).answer));

                while (!aQueue.isEmpty() && aQueue.peek().numSteps < MAX_STEPS) {
                    AnimalList currentAnimalList = aQueue.peek(); aQueue.remove();
                    if (!currentAnimalList.animals.equals(questions.get(i).finalAnimals.animals)) {
                        // Find new solutions and push them on the queue if they are not marked
                        for (int j = 0; j < MAX_ANIMALS; j++) {
                            ArrayList<Integer> newAnimalsForward = forward(currentAnimalList.animals, j);
                            ArrayList<Integer> newAnimalsReverse = reverse(currentAnimalList.animals, j);

                            if (!aSet.contains(newAnimalsForward)) {
                                aQueue.add(new AnimalList(newAnimalsForward, currentAnimalList.numSteps + 1));
                                aSet.add(newAnimalsForward);
                            }

                            if (!aSet.contains(newAnimalsReverse)) {
                                aQueue.add(new AnimalList(newAnimalsReverse, currentAnimalList.numSteps + 1));
                                aSet.add(newAnimalsReverse);
                            }
                        }
                    } else {
                        // Forces the algorithm to quit
                        aQueue.clear();
                        questions.get(i).answer = currentAnimalList.numSteps;
                        solution = true;
                    }
                }
                if (!solution)
                    questions.get(i).answer = -1;
            }
        }

        public void printAnswers(PrintWriter fout, int numDataSets) {
            fout.println(labNum + " " + numQuestions);
            for (int i = 0; i < numQuestions; i++) {
                int num = i + 1;
                if (questions.get(i).answer > -1)
                    fout.print(num + " " + questions.get(i).answer);
                else
                    fout.print(num + " NO SOLUTION");

                // Don't print the very last line ending
                if(labNum != numDataSets || i != numQuestions - 1)
                    fout.println();
            }
        }

        private ArrayList<Integer> forward(ArrayList<Integer> animals, int which) {
            // If there is at least one "which" animal to convert from
            ArrayList<Integer> newAnimals = new ArrayList<>(animals);
            if (newAnimals.get(which) >= 1) {
                newAnimals.set(which, newAnimals.get(which) - 1);
                for (int i = 0; i < MAX_ANIMALS; i++) {
                    newAnimals.set(i, newAnimals.get(i) + conversions.get(which).get(i));
                }
            }
            return newAnimals;
        }

        private ArrayList<Integer> reverse(ArrayList<Integer> animals, int toWhich) {
            ArrayList<Integer> newAnimals = new ArrayList<>(animals);

            // If there is at least one of each animal to convert from
            // in animals
            boolean wasTrue = true;
            for (int i = 0; i < MAX_ANIMALS; i++) {
                if (animals.get(i) < conversions.get(toWhich).get(i)) {
                    wasTrue = false;
                }
            }

            // Convert
            if (wasTrue) {
                for (int i = 0; i < MAX_ANIMALS; i++) {
                    newAnimals.set(i, newAnimals.get(i) - conversions.get(toWhich).get(i));
                }
                newAnimals.set(toWhich, newAnimals.get(toWhich) + 1);
            }

            return newAnimals;
        }
    }

    public static void main (String[] args) throws IOException {
        Scanner fin = new Scanner(new File("madvet.in"));
        PrintWriter fout = new PrintWriter(new File("madvet.out"));

        int numDataSets = fin.nextInt();
        for (int i = 1; i <= numDataSets; i++) {
            Laboratory lab = new Laboratory(fin.nextInt(), fin.nextInt());
            lab.inputConversions(fin);
            lab.inputQuestions(fin);
            lab.solve();
            lab.printAnswers(fout, numDataSets);
        }

        fin.close();
        fout.close();
    }
}
