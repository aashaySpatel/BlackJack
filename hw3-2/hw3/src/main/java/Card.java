public class Card {
    String suit;

    int value;


    public Card(String theSuit, int theValue){
        this.suit = theSuit;
        this.value = theValue;
    }
    public Card() {
        suit = "";
        value = 0;
    }

    @Override
    public String toString() {
        String valueStr;
        switch (value) {
            case 1: valueStr = "Ace"; break;
            case 11: valueStr = "Jack"; break;
            case 12: valueStr = "Queen"; break;
            case 13: valueStr = "King"; break;
            default: valueStr = String.valueOf(value); break;
        }
        return valueStr + " of " + suit;
    }
}
