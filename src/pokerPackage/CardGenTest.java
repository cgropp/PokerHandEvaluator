package pokerPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardGenTest {
	PokerHands testPokerHands = new PokerHands();	
	
	@Test
	void testCard() {
		Card testCard = testPokerHands.makeCard("5H");
		int testVal =testCard.getValue();
		char testSuit = testCard.getSuit();
		assertEquals(testVal, 5);
		assertEquals(testSuit, 'H');
	}
		

	
	//@Test
	//void testInputHand() {
		//System.out.println(testPokerHands.resStatement("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH"));
	//}
	

	@Test
	void testFlushCheck() {
		Hand h1 = testPokerHands.textToHand("2H 3H 5H 9H KH");
		boolean flush = testPokerHands.flushCheck(h1);
		assertEquals(flush, true);
	}
	
	@Test
	void testFlushCheck2() {
		Hand h1 = testPokerHands.textToHand("2H 3S 5H 9H KH");
		boolean flush = testPokerHands.flushCheck(h1);
		assertEquals(flush, false);
	}
	
	
	@Test
	void testStraightCheck() {
		Hand h1 = testPokerHands.textToHand("2S 3H 4H 5H 6H");
		boolean straight = testPokerHands.straightCheck(h1);
		assertEquals(straight, true);
	}
	
	@Test
	void testStraightCheck2() {
		Hand h1 = testPokerHands.textToHand("2S 3H 4H 5H 7H");
		boolean straight = testPokerHands.straightCheck(h1);
		assertEquals(straight, false);
	}
	
	@Test
	void testCalculateScore() {
		Hand h1 = testPokerHands.textToHand("2S 3H 4H 5H 7H");
		int score = testPokerHands.calculateScore(h1);
		assertEquals(score, 1);

	}
	
	
	@Test
	void testMaxCards() {
		Hand h1 = testPokerHands.textToHand("2S 3H 4H 5H 7H");
		int score = testPokerHands.calculateScore(h1);
		int[] maxes = testPokerHands.maxCards(h1, score);
		int[] res = {7,5,4,3,2};
		assertArrayEquals(maxes, res);
	}
	
	@Test
	void testHandToString() {
		Hand h1 = testPokerHands.textToHand("2H 2S 2C 5H 5S");
		String retString = testPokerHands.handToString(h1);
		assertEquals(retString, "full house: 2 over 5");
	}
	
	@Test
	void testDetermineWinner() {
		Hand h1 = testPokerHands.textToHand("2H 2S 2C 5H 5S");
		Hand h2 = testPokerHands.textToHand("2H 2S 2C 2H 6S");
		String retString = testPokerHands.determineWinner(h1, h2);
		assertEquals(retString, "White wins. - with four of a kind: 2");
	}
	
	@Test
	void testPokerEvaluator() {
		String result = testPokerHands.pokerEvaluator("Black: 3H 3D 5S 9C KD  White: 2C 2H 4S 8C KH");
		assertEquals(result, "Black wins. - with pair of 3");
	}
	
	@Test
	void testPokerEvaluator2() {
		String result = testPokerHands.pokerEvaluator("Black: 2H 3D 4S 5C 6D  White: 3H 4S 5C 6H 7H");
		assertEquals(result, "White wins. - with straight: high of 7");
	}
	
	
}
