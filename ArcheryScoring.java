import java.util.Scanner;

public class ArcheryScoring{

    //TODO
    /* fix average end score this round
	 * add total round score for each round and move total score to the same chunk
	 * 		as averange arrow and end score
	 * add more averages over all rounds like average arrow score, average end score
	 * add best and worst end over the whole tournament, not just each round
	 * add round score at end of each round
	 * add which end was the best and worst, not just score?
     */

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

	double averageArrowScoreEnd;
	double averageEndScoreRound;

    int round = 0;
    int end = 0;
    int arrow = 0;

    int ten = 0;
	int nine = 0;
	int eight = 0;
	
    public void tournamentSetup(){
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
				if(endArrows[j] == 10) {
		        	ten++;
					}
				else if(endArrows[j] == 9) {
		           	nine++;
				}
				else if(endArrows[j] == 8) {
		           	eight++;
				}
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

    private void printEndData(){
		if(arrowScoreError == false && numArrowError == false) {
			runningEndScore[round] += endScore;
			System.out.println("End Score: " + endScore + "\tTotal Score: " + runningEndScore[round]);
			System.out.print("Average arrow score this end: " + (double)endScore / (double)numArrowPerEnd + "\n");
		}else {
			System.out.print("Invalid input. Please reenter scores\n");
		}
    }

	private void roundData(){
		averageArrowScoreEnd = runningEndScore[arrow] / ((double)numEndsPerRound * (double)numArrowPerEnd);
		averageEndScoreRound = (double)runningEndScore[round] / numEndsPerRound;
		//runningEndScore[round] = 0;
	}

    private void printRoundData(){
		System.out.println("Average arrow score this round: " + averageArrowScoreEnd);
		System.out.println("Average end score this round: " + averageArrowScoreEnd + "\n");
    }

    private void printTournamentData(){
		if(round <= 1){
			System.out.println("Final score: " + (int)runningEndScore[round]);
		} else {
        System.out.println("Final Score: " + ((int)runningEndScore[round] + (int)runningEndScore[round + 1]));
		}
		System.out.println("Number of tens: " + ten);
		System.out.println("Number of nines: " + nine);
		System.out.println("Number of eights: " + eight);
    }

    /* private void tiebreakData(){
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
    } */

    private void findBestEnd(){
        	if(bestEndScore < endScore) {
				bestEndScore = endScore;
				bestEnd = "" + (end + 1);
			}else if(bestEndScore == endScore) {
				bestEnd = bestEnd + "," + (end + 1);
			}
        System.out.println("Best End: " + bestEndScore);
    }

    private void findWorstEnd(){
        if(worstEndScore > endScore) {
			worstEndScore = endScore;
			worstEnd = "" + (end + 1);
		}else if(worstEndScore == endScore) {
			worstEnd = worstEnd + "," + (end + 1);
		}
        System.out.println("Worst End: " + worstEndScore + "\n");
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
					findBestEnd();
					findWorstEnd();
					roundData();
				 } while (arrowScoreError == true || numArrowError == true);
			}
   
            printRoundData();
			}
	      
        printTournamentData();		
    }
}
