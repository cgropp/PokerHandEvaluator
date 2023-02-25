package pokerPackage;

public class Hand {
	public Card[] cards;
	public int score;
	
	public Hand(Card[] cards, int score) {
		this.cards = cards;
		this.score = score;
	}
	
	
	public Card[] getCards() {
		return cards;
	}
	
	public void setCards(Card[] newCards) {
		cards = newCards;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int newScore) {
		score = newScore;
	}
}
