import java.util.ArrayList;

public class BlackjackGameLogic {


    ArrayList<Card> hand;
    public BlackjackGameLogic() {

    }
    public BlackjackGameLogic(ArrayList<Card>newHand) {
        this.hand = newHand;
    }

    public static String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand1) {
        int playerHand = handTotal(playerHand1);
        int dealerHand = handTotal(dealerHand1);
        //System.out.println("Your hand total " + playerHand);
        //System.out.println("Dealer hand total " + dealerHand);
        if((playerHand > dealerHand) || dealerHand > 21){
            return "player";
        } else if (playerHand < dealerHand) {
            return "dealer";
        }else{
            return "push";
        }
    }
    public static int handTotal(ArrayList<Card> hand) {
        int sum = 0;
        for (Card i : hand) {
            sum += i.value;
        }
        return sum;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand){
        int dealerHand = handTotal(hand);
        return (dealerHand <= 16 ) ;
    }

}

