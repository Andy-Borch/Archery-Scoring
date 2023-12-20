import java.util.Scanner;

public class ArcheryScoring{
    public ArcheryScoring(){

    }
	
	private static int numArrowPerEnd = 0;
	private static int numEndsPerRound = 0;
	private static int numRoundsPerTournament = 0;
    private static int numArrowsPerTournament = 0;

	static int[][][] storeArrowScores;
	static int[] runningEndScore;
	static int[] endArrows;
	static int[] ends;

	boolean arrowScoreError;
	boolean numArrowError;
	int endScore;
	int bestEndScore;
	String bestEnd;
	int worstEndScore;
	String worstEnd;

    int round = 0;
    int end = 0;
    int arrow = 0;
    int ten = 0;
	int nine = 0;
	int eight = 0;
	
    public static void tournamentSetup(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rounds in the tournament");
        numRoundsPerTournament = scanner.nextInt();

        System.out.println("\nEnter the number of ends in each round");
        numEndsPerRound = scanner.nextInt();

        System.out.println("\nEnter the arrows in each end");
        numArrowPerEnd = scanner.nextInt();

        numArrowsPerTournament = numArrowPerEnd * numEndsPerRound * numRoundsPerTournament;

        storeArrowScores = new int[numRoundsPerTournament][numEndsPerRound][numArrowPerEnd];
	    runningEndScore = new int[numRoundsPerTournament];
	    endArrows = new int[numArrowPerEnd];
	    ends = new int[numEndsPerRound];
    }

    private void input(){
        Scanner userScores = new Scanner(System.in);
		String arrowScores = userScores.next();
        String[] scores = arrowScores.split("\\,");
					
		arrowScoreError = false;
		numArrowError = false;
		if(scores.length != numArrowPerEnd) {
			numArrowError = true;
		}
					
		//Doesn't run program if number of arrows is incorrect
		if(numArrowError == false) {
		    for(int j = 0; j < numArrowPerEnd; j++) {	
		        String temporaryString = scores[j];
		        endArrows[j] = Integer.parseInt(temporaryString);
		    }
						
		    endScore = 0;
		    for(int arrow = 0; arrow < endArrows.length; arrow++) {
				endScore += endArrows[arrow];
				if(endArrows[arrow] > 10 || endArrows[arrow] < 0) {	
					arrowScoreError = true;
				}
							
				storeArrowScores[round][end][arrow] = endArrows[arrow];
			}   
        }
					
        
    }

    public void printEndData(){
		if(arrowScoreError == false && numArrowError == false) {
			runningEndScore[round] += endScore;
			System.out.println("End Score: " + endScore + "\tTotal Score: " + runningEndScore[round]);
			System.out.print("Average arrow score per end: " + (double)endScore / (double)numArrowPerEnd);
			System.out.println("\nAverage arrow score per round: " + runningEndScore[round] / ((double)(end + 1) * (double)numArrowPerEnd) + "\n");
		}else {
			System.out.print("Invalid input. Please reenter scores\n");
		}
    }

    public void printRoundData(){
        System.out.println("Average score per end: " + (double)runningEndScore[round] / numEndsPerRound);
		System.out.println("Best End: " + bestEnd + " " + "(" + bestEndScore + ")");
		System.out.println("Worst End: " + worstEnd + " " + "(" + worstEndScore + ")" + "\n");
    }

    public void printTournamentData(){
        tiebreakData();
        System.out.println("Final Score: " + ((int)runningEndScore[0] + (int)runningEndScore[1]));
		System.out.println("Number of tens: " + ten);
		System.out.println("Number of nines: " + nine);
		System.out.println("Number of eights: " + eight);
    }

    private void tiebreakData(){
		for(round = 0; round < numRoundsPerTournament; round++) {
			for(end = 0; end < numEndsPerRound; end++) {
				for(arrow = 0; arrow < numArrowPerEnd; arrow++) { 
					if(storeArrowScores[round][end][arrow] == 10) {
		               	ten++;
					}
					else if(storeArrowScores[round][end][arrow] == 9) {
		               	nine++;
					}
					else if(storeArrowScores[round][end][arrow] == 8) {
		               	eight++;
					}      
				}
			}
		}
    }

    private void findBestEnd(){
        if(bestEndScore < endScore) {
			bestEndScore = endScore;
			bestEnd = "" + (end + 1);
		}else if(bestEndScore == endScore) {
			bestEnd = bestEnd + "," + (end + 1);
		}
        System.out.println(bestEnd);
    }

    private void findWorstEnd(){
        if(worstEndScore > endScore) {
			worstEndScore = endScore;
			worstEnd = "" + (end + 1);
		}else if(worstEndScore == endScore) {
			worstEnd = worstEnd + "," + (end + 1);
		}
        System.out.println(worstEnd);
    }

    public void printHighLowEndScore(){
        findBestEnd();
        findWorstEnd();
    }
	
	public void run(){
	    System.out.println("Enter " + numArrowPerEnd + " individual arrow scores per end and seperate them with commas" + "\n");
		
		//Round starts here
		for(int round = 0; round < runningEndScore.length; round++) {
			runningEndScore[round] = 0;
			System.out.println("Round " + (round + 1));
			bestEndScore = 0;
			worstEndScore = 30;
		
			// End starts here
			for(int end = 0; end < numEndsPerRound; end++) {
				System.out.print("End: " + (end + 1) + "\n");
				do {
					input();
                    printEndData();
				 } while (arrowScoreError == true || numArrowError == true);
			}
   
            printRoundData();
			printHighLowEndScore();
			}
	      
        printTournamentData();		
    }
    //TODO
    /*
     * Fix Average arrow score per round
     * fix best and worst end printing null
     * fix the weird pringting of 1 1
     * fix the counting of number of 8's, 9's, 10's
     */
}
