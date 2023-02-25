package pokerPackage;

public class Card {
	char suit;
	int value;
	
	public Card(int value, char suit) {
		this.suit = suit;
		this.value = value;
	}
	
	public char getSuit() {
		return suit;
	}
	
	public void setSuit(char newSuit) {
		suit = newSuit;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue;
	}
	
}
