package twister;

import twister.models.Board;
import twister.models.Robot;

public class tests {

public static void main(String[] args) {
	
	Board board = new Board();
	
	int[][] cartoTest= {
			{4,4,4,5,2},
			{4,5,5,5,4},
			{4,3,3,5,4},
			{4,5,1,5,4},
			{4,5,4,3,4},
			{1,5,5,3,4},
			{4,4,4,4,4}
	};
	
	board.setBoard(cartoTest);	
	Robot r = new Robot(0,4,1,board);
	int[][] gne = board.casesDeLaCouleur(1);
	for (int j = 0; j < 7; j++) {
			System.out.println(gne[j][0]);
			System.out.println(gne[j][1]);
		}
	int [] ouch = board.caseLaPlusProche(r, 1); 
	System.out.println(ouch[0] + "," + ouch[1]);		
			

}
}
