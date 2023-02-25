package pokerPackage;

import java.util.Scanner;

public class PokerHands {
	//Parse input line to create array of two hand strings, assuming valid input
	public String[] parseInput(String inputHands) {
		String blackString =  inputHands.substring(7,21);
		String whiteString = inputHands.substring(30);
		String[] strArray = {blackString, whiteString};
		return strArray;
	}
			
	//Create a card from an input string
	public Card makeCard(String cardText) {
		//Split input text string into value character and suit character
		char valChar = cardText.charAt(0);
		char suitChar = cardText.charAt(1);
		int newVal;
		
		//Translate characters to poker values
		if (valChar == 'T') {
			newVal = 10;
		}
		else if (valChar == 'J') {
			newVal = 11;
		}
		else if (valChar == 'Q') {
			newVal = 12;
		}
		
		else if (valChar == 'K') {
			newVal = 13;
		}
		else if (valChar == 'A') {
			newVal = 14;
		}
	
		else {
			newVal = Character.getNumericValue(valChar);
		}
		//Create a card using our value and suit
		Card newCard = new Card(newVal, suitChar);
		return newCard;
		
	}
	
	
	//Parse hand string to create array of cards
	//Return hand later instead
	public Hand textToHand(String handText) {
		String[] cardTexts = handText.split(" ");
		Card[] cardArr = new Card[5];
		for (int i =0; i < 5; i++) {
			cardArr[i] = makeCard(cardTexts[i]);
		}
		Hand newHand = new Hand(cardArr, 0);
		return newHand;
		
	}
	
	//Check if the hand is a flush
	public boolean flushCheck(Hand h){
		Card[] cards = h.getCards();
		char suit = cards[0].getSuit();
		for (Card c : cards) {
			if (c.getSuit() != suit) {
				return false;
			}
		}
		return true;	
	}
	
	
	//Find counts of each value in the hand
	public int[] findCounts(Hand h){
		//Value ranges from 2 (index 0) to Ace (index 14)
		int[] valueCounts = new int[14];
		for (int i = 0; i < valueCounts.length; i++) {
			valueCounts[i] = 0;
	}
		for (Card c : h.getCards() ) {
			int newVal = c.getValue();
			//Subtract 2 from index to get value 2 = first index, then increment count by 1
			valueCounts[newVal - 2] += 1;
		}
		return valueCounts;
	}
	
	//Check valueCount array to see if 5 consecutive values are found
	public boolean straightCheck(Hand h) {
		int consecutive = 0;
		int[] valueCounts = findCounts(h);
		for (int i = 0; i < valueCounts.length; i++) {
			//Five consecutive values represents a straight, so we return true
			if (consecutive == 5) {
				return true;
			}
			//Otherwise, count consecutive values
			else if (valueCounts[i] == 0){
				consecutive = 0;
			}
			else {
				consecutive += 1;
			}
			
		}
		//If no straight is found, return false
		return false;
	}
	
	public boolean fullHouseCheck(Hand h) {
		int[] valueCounts = findCounts(h);
		boolean has2 = false;
		boolean has3 = false;
		
		for (int i : valueCounts) {
			if (i == 2) {
				has2 = true;
			}
			if (i == 3) {
				has3 = true;
			}
		}
		return (has2 && has3);
		
	}
	
	
	public boolean fourOfAKindCheck(Hand h) {
		int[] valueCounts = findCounts(h);	
		for (int i : valueCounts) {
			if (i == 4) {
				return true;
			}
		}
		return false;
		
	}
	
	public boolean threeOfAKindCheck(Hand h) {
		int[] valueCounts = findCounts(h);
		boolean has2 = false;
		boolean has3 = false;
		
		for (int i : valueCounts) {
			if (i == 2) {
				has2 = true;
			}
			if (i == 3) {
				has3 = true;
			}
		}
		return (has3 && !has2);
		
	}
	
	public boolean twoPairsCheck(Hand h) {
		int[] valueCounts = findCounts(h);
		int pairCount = 0;
		for (int i : valueCounts) {
			if (i == 2) {
				pairCount++;
			}
		}
		return pairCount == 2;
		
	}
	
	public int highCard(Hand h) {
		int[] valueCounts = findCounts(h);
		//Start with rightmost index of valueCounts to find highest card
		for (int i = valueCounts.length-1; i >= 0; i--) {
			if (valueCounts[i] == 1) {
				//Add 2 to value to represent card value
				return i+2;
			}
		}
		return 2;
	}
	
	
	public boolean pairCheck(Hand h) {
		int[] valueCounts = findCounts(h);
		int pairCount = 0;
		for (int i : valueCounts) {
			if (i == 2) {
				pairCount++;
			}
			if (i == 3) {
				return false;
			}
		}
		return pairCount == 1;
		
	}
	
	//Applies a score to a hand ranging from high card (1) to straight flush (9)
	public int calculateScore(Hand h) {
		//Straight flush
		if (straightCheck(h) && flushCheck(h)) {
			return 9;
		}
		//Four of a kind
		else if (fourOfAKindCheck(h)) {
			return 8;
		}
		//Full house
		else if (fullHouseCheck(h)) {
			return 7;
		}
		//Flush
		else if (flushCheck(h)) {
			return 6;
		}
		//Straight
		else if (straightCheck(h)) {
			return 5;
		}
		//Three of a kind
		else if (threeOfAKindCheck(h)) {
			return 4;
		}
		//Two Pairs
		else if (twoPairsCheck(h)) {
			return 3;
		}
		//Pair
		else if(pairCheck(h)) {
			return 2;
		}
		//High card
		else {
			return 1;
		}
		
	}
	
	//Find the relevant highest card (index 0), or cards in the case of a tie (index 1-4)
	//For use in print statement, and for picking the winner in the case of same score
	public int[] maxCards(Hand h, int score) {
		int[] valueCounts = findCounts(h);
		int[] highCards = new int[5];
		int maxCardInd = 0;
		
		//Straight flush, high is highest card in the hand
		if (score == 9) {
			highCards[0] = highCard(h);
		}
		
		//Four of a kind, high is value of the 4 cards
		else if (score == 8) {
			for (int i = 0; i < valueCounts.length; i++) {
				if (valueCounts[i] == 4) {
					maxCardInd = i;
				}
			}
			highCards[0] = maxCardInd + 2;
		}
		
		
		
		//Full house, high is value of the three of a kind, 2nd high is value of the pair
		else if (score == 7) {
			for (int i = 0; i < valueCounts.length; i++) {
				if (valueCounts[i] == 3) {
					highCards[0] = i+2;
				}
				else if (valueCounts[i] == 2) {
					highCards[1] = i+2;
				}
				
			}
		}
		
		//Flush, high is highest card in the hand
		else if (score == 6) {
			highCards[0] = highCard(h);
		}
		
		
		//Straight, high is highest card in the hand
		else if (score == 5) {
			highCards[0] = highCard(h);
		}
		
		
		
		//Three of a kind, high is the value of the three cards, then the 2 remaining cards
		else if (score == 4) {
			for (int i = 0; i < valueCounts.length; i++) {
				if (valueCounts[i] == 3) {
					maxCardInd = i;
				}
			}
			highCards[0] = maxCardInd + 2;
			
			maxCardInd = 0;
			//Find the descending order of the remaining 2 cards
				for (int i = valueCounts.length-1; i >= 0; i--) {
					if (valueCounts[i] == 1) {
						maxCardInd = i;
					}
				}
				highCards[1] = maxCardInd + 2;
			
			
			//Set index 1 to highest card from remaining 2 cards
			highCards[1] = maxCardInd + 2;
			
			//Find last remaining card, set index 2 to that card
			for (int i = maxCardInd-1; i >= 0; i--) {
				if (valueCounts[i] == 1) {
					//Add 2 to value to represent card value
					maxCardInd = i;
					break;
				}
			}
			highCards[2] = maxCardInd + 2;
		}
		
		
		
		
		//Two pairs, high is the value of the higher pair, then lesser pair, then remaining card
		else if (score == 3) {
			//Keep track of how many cards we've put into our return array
			int numCards = 0;
			//loop backwards through valueCounts, recording the pairs in descending order
			for (int i = valueCounts.length-1; i >= 0; i--) {
				if (valueCounts[i] == 2) {
					highCards[numCards] = i + 2;
					numCards++;
				}
				//Record the remaining card
				else if (valueCounts[i] == 1) {
					highCards[2] = i+2;
				}
			}
		}
		
		
		
		
		//Pair, high is the value of the pair, then remaining cards
		else if (score == 2) {
			for (int i = 0; i < valueCounts.length; i++) {
				if (valueCounts[i] == 2) {
					maxCardInd = i;
				}
			}
			highCards[0] = maxCardInd + 2;
			
			
			//Find remaining cards in descending order
			for (int i = valueCounts.length-1; i >= 0; i--) {
				if (valueCounts[i] == 1) {
					maxCardInd = i;
				}
			}
			highCards[1] = maxCardInd + 2;
		
		
			//Set index 1 to highest card from remaining 3 cards
			highCards[1] = maxCardInd + 2;
			
			//Find 2nd highest remaining card, set index 2 to that card
			for (int i = maxCardInd-1; i >= 0; i--) {
				if (valueCounts[i] == 1) {
					//Add 2 to value to represent card value
					maxCardInd = i;
					break;
				}
			}
			highCards[2] = maxCardInd + 2;
			//Last remaining card
			for (int i = maxCardInd-1; i >= 0; i--) {
				if (valueCounts[i] == 1) {
					//Add 2 to value to represent card value
					maxCardInd = i;
					break;
				}
			}
			highCards[3] = maxCardInd + 2;
			}
		
		
		
		
		//High card case
		//High card is highest value, then 2nd highest, and so on
		else{
			//Keep track of how many cards we've put into our return array
			int numCards = 0;
			//loop backwards through valueCounts, recording the results in descending order
			for (int i = valueCounts.length-1; i >= 0; i--) {
				if (valueCounts[i] == 1) {
					highCards[numCards] = i + 2;
					numCards++;
				}
			}
		}
		return highCards;
	}
	
	
	
	
	//Convert a winning hand into a string to be printed
	public String handToString(Hand h1) {
		//Create print statement for each case, using maximums
		int handScore = calculateScore(h1);
		int[] highCards = maxCards(h1, handScore);
		String retString = "";
		int cardVal = 0;
		int cardVal2 = 0;
		
		//Straight flush case
		if (handScore == 9) {
			retString = "straight flush: high of ";
			cardVal = highCards[0];
		}
		
		

		//Four of a kind case
		if (handScore == 8) {
			retString = "four of a kind: ";
			cardVal = highCards[0];
		}
		

		//Full House case, need special print statement
		if (handScore == 7) {
			retString = "full house: ";
			cardVal = highCards[0];
			cardVal2 = highCards[1];
		}
		
		//Flush case
		if (handScore == 6) {
			retString = "flush: high of ";
			cardVal = highCards[0];
		}

		//Straight case
		if (handScore == 5) {
			retString = "straight: high of ";
			cardVal = highCards[0];
		}

		//3 of a kind case
		if (handScore == 4) {
			retString = "three of a kind of ";
			cardVal = highCards[0];
		}
		

		//2 pairs case
		if (handScore == 3) {
			retString = "two pairs: high of ";
			cardVal = highCards[0];
		}

		//Pair case
		if (handScore == 2) {
			retString = "pair of ";
			cardVal = highCards[0];
		}
		

		//High card case
		if (handScore == 1) {
			retString = "high card: ";
			cardVal = highCards[0];
		}
		
		//Convert card value to card name
		String cardString = "";
		if (cardVal >=2 && cardVal <=10) {
			cardString = String.valueOf(cardVal);
		}
		else if (cardVal == 11) {
			cardString = "Jack";
		}
		else if (cardVal == 12) {
			cardString = "Queen";
		}
		else if (cardVal == 13) {
			cardString = "King";
		}
		else if (cardVal == 14) {
			cardString = "Ace";
		}
		
		//Convert 2nd high card to card name
		String cardString2 = "";
		if (cardVal2 >=2 && cardVal <=10) {
			cardString2 = String.valueOf(cardVal2);
		}
		else if (cardVal2 == 11) {
			cardString2 = "Jack";
		}
		else if (cardVal2 == 12) {
			cardString2 = "Queen";
		}
		else if (cardVal2 == 13) {
			cardString2 = "King";
		}
		else if (cardVal2 == 14) {
			cardString2 = "Ace";
		}
		
		
		
		//Special case for full house, using "over"
		if (handScore == 7) {
			return retString + cardString + " over " + cardString2;
		}
		
		else {
			return retString + cardString;
		}
		
		
		
	}
	
	
	//Compare the high-cards of two different hands, print the winner
	public String highCompare(Hand black, Hand white) {
		int blackScore = calculateScore(black);
		int whiteScore = calculateScore(white);
		int[] blackHighs = maxCards(black, blackScore);
		int[] whiteHighs = maxCards(white, whiteScore);
		
		for (int i = 0; i < blackHighs.length; i++) {
			if (blackHighs[i] > whiteHighs[i]) {
				return "Black";
			}
			else if (blackHighs[i] < whiteHighs[i]) {
				return "White";
			}
		}
		return "Tie";
	}
	
	
	//Determine the winner between two hands, return correct string
	public String determineWinner(Hand h1, Hand h2) {
		int blackScore = calculateScore(h1);
		int whiteScore = calculateScore(h2);
		String winner = "";
		String retString = " wins. - with ";
		String highCardString = "";
		
		if (blackScore > whiteScore) {
			winner = "Black";
			highCardString = handToString(h1);
		}
		else if (blackScore < whiteScore) {
			winner = "White";
			highCardString = handToString(h2);
		}
		//In case of a tie, use high card array to find winner
		else {
			winner = highCompare(h1, h2);
			if (winner == "Black") {
					highCardString = handToString(h1);
			}
			else if (winner == "White") {
				highCardString = handToString(h2);
			
					}
			else if (winner == "Tie"){
					return "Tie.";
			}
		}
		//Final print statement declaring winner and their hand
		return winner + retString + highCardString;
	}
	
	//Takes input format, returns winning text
	public String pokerEvaluator(String inputHands) {
		String[] handTexts = parseInput(inputHands);
		Hand blackHand = textToHand(handTexts[0]);
		Hand whiteHand = textToHand(handTexts[1]);
		return determineWinner(blackHand, whiteHand);
		
	}
	
	 public static void main (String[] args) {
		 PokerHands pokerHandEvaluator = new PokerHands();	
		 Scanner scan = new Scanner(System.in);
		 
		 //Read input of poker hands
		 System.out.println("Enter poker hands: \n"); 
		 System.out.println();
		 while (scan.hasNextLine()) {
			String input = scan.nextLine();
			//Evaluate and print winner
			String outputLine = pokerHandEvaluator.pokerEvaluator(input);
			System.out.println("\n" + outputLine);
		 }
		 scan.close();
		 

	}
}
