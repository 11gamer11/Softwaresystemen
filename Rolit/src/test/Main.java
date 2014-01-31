package test;

//import game.HumanPlayer;

import strategy.NaiveStrategy;
import strategy.SmartStrategy;
import game.ComputerPlayer;
import game.HumanPlayer;
import game.Player;
import board.Mark;

public class Main {
    private static Player createPlayer(String s, Mark m) {
        if (s.equals("-N")) {
            return new ComputerPlayer(m, new NaiveStrategy());
        } else if (s.equals("-S")) {
            return new ComputerPlayer(m, new SmartStrategy());
        } else {
            return new HumanPlayer(s, m);
        }
    }
	
    public static void main(String[] args) {
        System.out.println("Rolit");
        System.out.println("--------------------------");
        /*Board board = new Board();
        for(int i = 0; i < 36; i++){
        	System.out.println("Veld "+i+" = ("+board.rowcol(i)[0]+","+board.rowcol(i)[1]+ ")");
        }*/
        
        Player s1 = createPlayer(args[0], Mark._RED__);
        Player s2 = createPlayer(args[1], Mark.GREEN_);
        Player s3 = createPlayer(args[2], Mark.YELLOW);
        Player s4 = createPlayer(args[3], Mark._BLUE_);
        System.out.println("Speler 1: " + s1.getName() + ", " + s1.getMark());
        System.out.println("Speler 2: " + s2.getName() + ", " + s2.getMark());
        System.out.println("Speler 3: " + s3.getName() + ", " + s3.getMark());
        System.out.println("Speler 4: " + s4.getName() + ", " + s4.getMark());
        //Game spel = new Game(s1, s2, s3, s4);
        //spel.start();
    	/*Board board = new Board();*/
        
    }
}
