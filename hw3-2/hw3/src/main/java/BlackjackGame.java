import java.util.ArrayList;

public class BlackjackGame {
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BlackjackDealer theDealer;
    BlackjackGameLogic gameLogic;
    double currentBet;
    double totalWinnings;

    public BlackjackGame() {
        // Assuming BlackjackDealer and BlackjackGameLogic have default constructors or appropriate constructors for initializing
        this.theDealer = new BlackjackDealer();
        this.gameLogic = new BlackjackGameLogic();
        this.playerHand = new ArrayList<>();
        this.bankerHand = new ArrayList<>();
        this.totalWinnings = 0;

        // Initialize the deck
        theDealer.generateDeck();
        theDealer.shuffleDeck();
    }

    public boolean isPlayerBusted(){
        return gameLogic.handTotal(playerHand)>21;
    }

    public void placeBet(double betAmount) {
        currentBet = betAmount;
    }

    public void dealInitialCards() {
        playerHand.addAll(theDealer.dealHand());
        bankerHand.addAll(theDealer.dealHand());
    }

    public double evaluateWinnings() {
        String result = gameLogic.whoWon(playerHand, bankerHand);
        switch (result) {
            case "player":
                if(gameLogic.handTotal(playerHand) == 21){
                    totalWinnings += (currentBet * 1.5);
                    System.out.println("You Won!\n");
                    break;
                }
                totalWinnings += currentBet; // Player wins the bet
                System.out.println("You Won!\n");
                break;
            case "dealer"://
                totalWinnings -= currentBet; // Player loses the bet
                System.out.println("You LOST!\n");
                break;
            case "push":
                // No change in total winnings, bet is returned
                break;
        }

        return totalWinnings;
    }

    public void playerHit() {
        System.out.println("Current Bet " + currentBet);
        System.out.println("Losing money: " + totalWinnings);
        System.out.println("Before draw");
        playerHand.add(theDealer.drawOne());

        System.out.println("After draw");
        if (gameLogic.handTotal(playerHand) > 21) {
            totalWinnings -= currentBet;
            System.out.println("You BUST!\n");
            //Sysmtem.out.println(totalWinnings);
        }
    }

    public int dealerHandTotal (){
        return gameLogic.handTotal(bankerHand);
    }

    public void dealerHit(){
        bankerHand.add(theDealer.drawOne());
    }

    public void bankerDraw() {
        //System.out.println("Inside bankerDraw");
        while (gameLogic.evaluateBankerDraw(bankerHand)) {
            bankerHand.add(theDealer.drawOne());
            if(gameLogic.handTotal(bankerHand) > 21){
                return;
            }
        }
    }

    // Add getters for playerHand, bankerHand, and totalWinnings for UI integration
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public double moneyLeft(){
        return totalWinnings;
    }

    public ArrayList<Card> getBankerHand() {
        return bankerHand;
    }

    public double getTotalWinnings() {
        return totalWinnings;
    }

    public void clearHands(){
        this.playerHand.clear();
        this.bankerHand.clear();
    }
}