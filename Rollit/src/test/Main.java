package test;

//import game.HumanPlayer;

import game.HumanPlayer;
import game.Game;
import game.Player;
import board.Board;
import board.Mark;
//import board.Mark;
//import game.Game;
//import game.Player;

public class Main {
    public static void main(String[] args) {
        /*if (args.length == 2) {
            Player s1 = new HumanPlayer(args[0], Mark.values()[0]);
            Player s2 = new HumanPlayer(args[1], Mark.values()[1]);
            Game spel = new Game(s1, s2);
            spel.start();
        }else if (args.length == 3) {
            Player s1 = new HumanPlayer(args[0], Mark.values()[0]);
            Player s2 = new HumanPlayer(args[1], Mark.values()[1]);
            Player s3 = new HumanPlayer(args[2], Mark.values()[2]);
            Game spel = new Game(s1, s2, s3);
            spel.start();
        }else if (args.length == 4) {
            Player s1 = new HumanPlayer(args[0], Mark.values()[0]);
            Player s2 = new HumanPlayer(args[1], Mark.values()[1]);
            Player s3 = new HumanPlayer(args[2], Mark.values()[2]);
            Player s3 = new HumanPlayer(args[3], Mark.values()[3]);
            Game spel = new Game(s1, s2, s3, s4);
            spel.start();
        } else {
            System.out.println("usage: TicTacToe <name1> <nam2>");
        }*/
        System.out.println("Rolit");
        System.out.println("--------------------------");
        /*Board board = new Board();
        for(int i = 0; i < 36; i++){
        	System.out.println("Veld "+i+" = ("+board.rowcol(i)[0]+","+board.rowcol(i)[1]+ ")");
        }*/
        Player s1 = new HumanPlayer("P1", Mark._RED__);
        Player s2 = new HumanPlayer("P2", Mark._BLUE_);
        Game spel = new Game(s1, s2);
        spel.start();
    	/*Board board = new Board();
    	System.out.println("\ncurrent game situation: \n\n"+board.toString()+"\n");    	
    	System.out.println("what is field 0?"+board.getField(0));
    	System.out.println("what is field 27?"+board.getField(27));
    	System.out.println("what is field 28?"+board.getField(28));
    	System.out.println("what is field 35?"+board.getField(35));
    	System.out.println("what is field 36?"+board.getField(36));
    	System.out.println("is 27 a empty field?"+board.isEmptyField(27));
    	System.out.println("is board full?"+board.isFull());
    	System.out.println("is 0 a correct field to place?"+board.isCorrectField(0));
    	System.out.println("is 9 a correct field to place?"+board.isCorrectField(9));
    	System.out.println("is 18 a correct field to place?"+board.isCorrectField(18));
    	System.out.println("is 27 a correct field to place?"+board.isCorrectField(27));
    	System.out.println("is 36 a correct field to place?"+board.isCorrectField(36));
    	System.out.println("is 45 a correct field to place?"+board.isCorrectField(45));
    	System.out.println("is 54 a correct field to place?"+board.isCorrectField(54));
    	System.out.println("is 63 a correct field to place?"+board.isCorrectField(63));
    	System.out.println("is 19 a correct field to place?"+board.isCorrectField(19));
    	System.out.println("get free fields"+board.getAllFields(Mark.EMPTY_));
    	System.out.println("get fields from 'RED'"+board.getAllFields(Mark._RED__));
    	System.out.println("get fields from 'BLUE'"+board.getAllFields(Mark._BLUE_));
    	System.out.println("get all beatfields from 'BLUE'"+board.checkBeatingFields(Mark._BLUE_));
    	System.out.println("get all correct Fields"+board.getAllCorrectFields());*/
    	/*for(int i = 0; i < 64; i++){
    	System.out.println("get all beatenfields from 'BLUE' on "+i+board.checkBeat(i, Mark._BLUE_));
    	}*/
    	/*System.out.println("get all beatenfields from 'BLUE' on 20 "+board.checkBeat(20, Mark._BLUE_));
    	board.setField(34, Mark._BLUE_);
    	System.out.println("get all beatenfields from 'BLUE' on 20 "+board.checkBeat(20, Mark._BLUE_));
    	System.out.println("get all beatfields from 'BLUE'"+board.checkBeatingFields(Mark._BLUE_));
    	
    	System.out.println("\ncurrent game situation: \n\n"+board.toString()+"\n");*/
    	
    	
    	
    }
}
