package projects.tetration;

import osbjava.objects.*;

public class ContestantColorPrint {

    public static void main(String[] args) {

        for (int i = 0; i < Contestant.length(); i++) {
            Contestant contestant = Contestant.toEnum(i);
            OsbColor color = contestant.color();
            System.out.printf("%-15s%-4d%-4d%-4d%n", contestant, (int) color.getR(), (int) color.getG(), (int) color.getB());
        }

    }
}
