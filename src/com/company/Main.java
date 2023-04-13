package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final String START = "Cat and Mouse\n\nCat Mouse  Distance\n";
    static final String SEPARATOR = "-------------------";
    static String fileNameFrom = "C:\\Users\\Mary\\IdeaProjects\\CatAndMouse\\src\\1.ChaseData.txt";
    static String fileNameTo = "C:\\Users\\Mary\\IdeaProjects\\CatAndMouse\\src\\PursuitLog.txt";

    public static void main(String[] args) {
        try {
            File file = new File(fileNameFrom);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameTo));
            Scanner input = new Scanner(file);

            writer.write(START + SEPARATOR + "\n");
            int field = Integer.parseInt(input.nextLine().trim());
            final int from = 0, to = 10000;
            if (field <= from || field >= to) {
                writer.close();
                throw new IllegalArgumentException("Invalid data entry for field creation");
            }
            int ForMouse = 0;
            int ForCat = 0;
            int countM = 0;
            int countC = 0;
            String breakup = " ";
            int am = 0, ac = 0;

            loop:
            while (input.hasNextLine()) {
                String next_line = input.nextLine();
                boolean M = checkM(next_line);
                boolean C = checkC(next_line);
                boolean P = checkP(next_line);
                String for_bool = M ? "M" : (C ? "C" : (P ? "P" : "1"));
                switch (for_bool) {
                    case ("M"):
                        next_line = next_line.replace("M", "").trim();
                        int MoveOfMouse = Integer.parseInt(next_line);
                        countM++;
                        if (countM > 1){
                            ForMouse += Math.abs(MoveOfMouse);
                        }
                        am += MoveOfMouse;
                        am = checkFieldBoundaries(field, am);
                        break;
                    case ("C"):
                        next_line = next_line.replace("C", "").trim();
                        int MoveOfCat = Integer.parseInt(next_line);
                        countC++;
                        if (countC > 1){
                            ForCat += Math.abs(MoveOfCat);
                        }
                        ac += MoveOfCat;
                        ac = checkFieldBoundaries(field, ac);
                        break;
                    case ("P"):
                        if (am != ac) {
                            String question = "??";
                            if (am == 0) {
                                writer.write(String.format("%3d%6s\n", ac, question));
                                break;
                            } else if (ac == 0) {
                                writer.write(String.format("%3s%6d\n", question, am));
                                break;
                            } else {
                                writer.write(String.format("%3d%6d%10d\n", ac, am, Math.abs(ac - am)));
                                break;
                            }
                        } else if (ac == 0) {
                            break;
                        } else {
                            breakup = Integer.toString(am);
                            break loop;
                        }
                    case ("1"):
                        writer.close();
                        throw new IllegalArgumentException("String was not in a correct format");
                }
            }
            input.close();
            int distanceForMouse = ForMouse, distanceForCat = ForCat;
            writer.close();
            enfFile(distanceForMouse, distanceForCat, breakup);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean checkM(String m)
    {
        Pattern pattern2 = Pattern.compile("^M\s+[-]?[0-9]+");
        Matcher k = pattern2.matcher(m);
        return k.find();
    }

    static boolean checkC(String c)
    {
        Pattern pattern1 = Pattern.compile("^C\s+[-]?[0-9]+");
        Matcher l = pattern1.matcher(c);
        return l.find();
    }

    static boolean checkP(String P)
    {
        return P.equals("P");
    }

    static int checkFieldBoundaries(int field, int a)
    {
        if (a > field)
        {
            do
            {
                a = (a - field);
            }
            while (a > field);
        }
        if (a < 0)
        {
            do
            {
                a = field - (-a);
            }
            while (a < 0);
        }
        return a;
    }

    static void enfFile(int distanceForMouse, int distanceForCat, String breakup) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameTo, true));
        writer.write(SEPARATOR + "\n");
        if (breakup.equals(" ")) {
            writer.write(String.format("\n\nDistance traveled:   Mouse    Cat\n\t\t\t\t%10d%7d\n\nMouse evaded Cat", distanceForMouse, distanceForCat));
        } else {
            writer.write(String.format("\n\nDistance traveled:   Mouse    Cat\n\t\t\t\t%10d%7d\n\nMouse caught at: %2s ", distanceForMouse, distanceForCat, breakup));
        }
        writer.close();
    }
}
