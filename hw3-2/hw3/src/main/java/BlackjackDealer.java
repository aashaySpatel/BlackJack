import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
public class BlackjackDealer{
    ArrayList<Card> deck;
    public BlackjackDealer() {
        deck = new ArrayList<Card>();
    }
    ArrayList<String> suitArray = new ArrayList<String>(Arrays.asList("Spades","Diamonds","Hearts","Clubs"));

    public void generateDeck(){
        for (String s : suitArray) {
            for (int cards = 0; cards < 13; cards++) {
                int values = cards + 1;
                if (values > 9) {
                    deck.add(cards, new Card(s, 10));
                }else{
                    deck.add(cards, new Card(s, values));
                }
            }
        }
    }
    //deal from top or random index?
    public ArrayList<Card> dealHand(){
        Random random = new Random();
        int randomIndex = random.nextInt(deck.size());
        Card card1 = new Card(deck.get(0).suit,deck.get(0).value);
        Card card2 = new Card(deck.get(1).suit,deck.get(1).value);
        ArrayList<Card> newHand = new ArrayList<Card>(Arrays.asList(card1,card2));
        deck.remove(card1);
        deck.remove(card2);
        return newHand;
    }

    public Card drawOne(){
        Card drawn = new Card(deck.get(0).suit,deck.get(0).value);
        deck.remove(0);
        return drawn;
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public int deckSize(){
        return deck.size();
    }
}