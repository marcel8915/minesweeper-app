package com.minesweeper.app;

import java.util.Scanner;

import com.minesweeper.utils.CommonUtils;

public class App {
    private static int grid;
    private static int mines;
    private static int[][] data;
    private static int remainingSteps;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        grid = 0;
        mines = 0;

        System.out.println("Welcome to Minesweeper!");

        while (true) {
            // set the grids
            System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            grid = getGridSize(scanner);

            // set the total mines
            System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            mines = getTotalMines(scanner);

            // play the game
            play(scanner);

            System.out.println("Press any key to play again, or type 'exit' to close the game.");
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                scanner.close();
                System.exit(0);
            }
        }
    }

    private static int getGridSize(Scanner scanner) {
        while(true) {
            int inputGrid = CommonUtils.getInt(scanner.nextLine());

            if (inputGrid >= 3 && inputGrid <= 10) {
                return inputGrid;
            } else {
                System.out.print("Please input valid number between 3 and 10: ");
            }
        }
    }
    
    private static int getTotalMines(Scanner scanner) {
        while(true) {
            int inputMines = CommonUtils.getInt(scanner.nextLine());
            int maxMines = (int) Math.floor(grid * grid * 0.35);

            if (inputMines > 0 && inputMines <= maxMines) {
                return inputMines;
            } else {
                System.out.print("Please input valid number of mines (maximum is 35% of the total squares): ");
            }
        }
    }

    private static void play(Scanner scanner) {
        data = new int[grid][grid];
        remainingSteps = grid * grid - mines;
        setMines();

        while(true) {
            System.out.println();
            System.out.println("Here is your minefield:");
            renderGrid();

            if (remainingSteps <= 0) {
                System.out.println("Congratulations, you have won the game!");
                break;
            }

            System.out.print("Select a square to reveal (e.g. A1): ");
            String input = scanner.nextLine();
            
            if (input.length() >= 2 && input.length() <= 3) {
                int i = CommonUtils.alphabetToNumber(Character.toUpperCase(input.charAt(0))) - 1;
                int j = CommonUtils.getInt(input.substring(1)) - 1;

                if (i >= 0 && i <= grid && j >= 0 && j <= grid) {
                    int value = data[i][j];
                    if (value == 99) {
                        System.out.println("Oh no, you detonated a mine! Game over.");
                        break;
                    } else {
                        remainingSteps -= 1;
                        int noOfMines = findMinesSurrounding(i, j);
                        System.out.printf("This square contains %d adjacent mines.", noOfMines);
                        System.out.println();
                    }
                }
            }
        }
    }

    private static void setMines() {
        int flag = mines;
        while(flag > 0) {
            // randomize the mines index location
            int i = (int) (Math.random() * grid);
            int j = (int) (Math.random() * grid);

            if (data[i][j] == 0) {
                data[i][j] = 99; // flag 99 as a mines
                flag--;
            }
        }
    }

    private static void renderGrid() {
        System.out.println();
        for (int i=0; i<=grid; i++) {
            for (int j=0; j<=grid; j++) {
                if (j == 0) { // print the first column
                    System.out.print(i == 0 ? " " : CommonUtils.numberToAlphabet(i));
                } else if (i == 0) { // print first row
                    System.out.print(" " + j);
                } else { // print the grid data
                    int no = data[i-1][j-1];
                    switch (no) {
                        case 0 -> System.out.print(" _");
                        case 99 -> System.out.print(" _"); // change the symbol to see where is the mines
                        case 100 -> System.out.print(" 0");
                        default -> System.out.print(" " + no); // print how many mines surrounding
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int findMinesSurrounding(int i, int j) {
        int noOfMines = 0;

        // looping to find the mines at 8 grid surrounding
        for (int x=1; x>=-1; x--) {
            for (int y=1; y>=-1; y--) {
                int row = i - x;
                int col = j - y;

                if (row >= 0 && col >= 0 && row < grid && col < grid) {
                    if (data[row][col] == 99) {
                        noOfMines += 1; // count how many mines
                    }
                }
            }
        }

        data[i][j] = noOfMines > 0 ? noOfMines : 100;
        return noOfMines;
    }
}
