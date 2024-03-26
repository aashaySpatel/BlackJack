

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;


public class JavaFXTemplate extends Application {
	int startingAmount = 1000; // Default starting amount
	int currentBalance = startingAmount; // To keep track of the player's current balance
	BlackjackGame blackjackGame;
	// Blackjack game instance

	private Stage primaryStage; // To allow access to the primaryStage in other methods
	private VBox playerHandBox, dealerHandBox; // UI elements for displaying hands

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {
//		this.primaryStage = primaryStage;
		blackjackGame = new BlackjackGame();
//		new BlackjackGame blackjackGame;
//		BlackjackGame blackjackGame = new BlackjackGame();


		VBox startScreen = new VBox(10);
		startScreen.setAlignment(Pos.CENTER);

		Text welcomeText = new Text("Welcome to Blackjack");
		welcomeText.setFont(new Font("Comic Sans MS", 20));

		TextField startingAmountField = new TextField(String.valueOf(startingAmount));
		startingAmountField.setPromptText("Enter Starting Amount");

		Button startGameButton = new Button("Start Game");
		startGameButton.setOnAction(e -> {
			try {
				int enteredAmount = Integer.parseInt(startingAmountField.getText());
				currentBalance = enteredAmount; // Set the current balance to the starting amount
				blackjackGame.totalWinnings = currentBalance;
				betScreen(primaryStage, blackjackGame); // Transition to the game screen
			} catch (NumberFormatException ex) {
				startingAmountField.setText("Invalid Amount");
			}
		});

		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> showEndScreen(primaryStage, "Thanks for playing!"));

		startScreen.getChildren().addAll(welcomeText, startingAmountField, startGameButton, quitButton);

		Scene scene = new Scene(startScreen, 400, 300);
		primaryStage.setTitle("Blackjack Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void betScreen (Stage primaryStage, BlackjackGame blackjackGame){
		VBox gameLayout = new VBox(10);
		gameLayout.setAlignment(Pos.CENTER);
		TextField betAmountField = new TextField("10");
		HBox betBox = new HBox(5, new Label("Bet Amount:"), betAmountField);
		betBox.setAlignment(Pos.CENTER);


		Button start = new Button("Confirm");
		start.setOnAction(e -> {
			int curBet = Integer.parseInt(betAmountField.getText());
			if (curBet > blackjackGame.getTotalWinnings()) {
				// Clear the text field and show an error message if bet exceeds total winnings
				betAmountField.clear();
				Text errorMessage = new Text("Insufficient funds");
				if (!gameLayout.getChildren().contains(errorMessage)) {
					gameLayout.getChildren().add(errorMessage);
				}
			} else {
				// Place the bet and proceed to the game screen if the bet is valid
				blackjackGame.placeBet(curBet);
				showGameScreen(primaryStage, blackjackGame);
			}
		});

		gameLayout.getChildren().addAll(betBox, start);
		Scene scene = new Scene(gameLayout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void showGameScreen(Stage primaryStage , BlackjackGame blackjackGame) {
		VBox gameLayout = new VBox(10);
		gameLayout.setAlignment(Pos.CENTER);
		blackjackGame.dealerHit();
		blackjackGame.dealerHit();

//		blackjackGame.dealInitialCards();
//		updateHandDisplay();




		Text balanceText = new Text("Balance: $" + blackjackGame.moneyLeft());

		balanceText.setFont(new Font("Comic Sans MS", 16));

		playerHandBox = new VBox(new Text("Player's Hand:"));
		dealerHandBox = new VBox(new Text("Dealer's Hand:"));




		Button hitButton = new Button("Hit");
		Button stayButton = new Button("Stay");
		Button quitGameButton = new Button("Quit Game");



		hitButton.setOnAction(e -> playerHit2(primaryStage));
		stayButton.setOnAction(e -> playerStay(primaryStage));
		quitGameButton.setOnAction(e -> Platform.exit());//showEndScreen(primaryStage,"Thanks for playing!"/*));

		HBox buttonBox = new HBox(10, hitButton, stayButton, quitGameButton);
		buttonBox.setAlignment(Pos.CENTER);

		gameLayout.getChildren().addAll(balanceText, dealerHandBox, playerHandBox, buttonBox);

		Scene gameScene = new Scene(gameLayout, 600, 400);
		primaryStage.setScene(gameScene);
	}


	private void updateHandDisplay() {
		// Clear current hand displays

		playerHandBox.getChildren().clear();
		dealerHandBox.getChildren().clear();


		// Update player's hand display
		Text playerHandTitle = new Text("Player's Hand:");
		playerHandBox.getChildren().add(playerHandTitle);
		for (Card card : blackjackGame.getPlayerHand()) {
			Text cardText = new Text(card.toString());
			playerHandBox.getChildren().add(cardText);
		}

		// Update dealer's hand display, you might choose to hide the first card or show all based on game state
		Text dealerHandTitle = new Text("Dealer's Hand:");
		dealerHandBox.getChildren().add(dealerHandTitle);
		boolean firstCard = true;
		/*for (Card card : blackjackGame.getBankerHand()) {
			// Optionally hide the dealer's first card ('hole card') until the stay action is taken
			Text cardText = new Text((firstCard ? "Hidden" : card.toString()));
			dealerHandBox.getChildren().add(cardText);
			firstCard = false; // Only the first card is hidden, comment this line if you want to show all cards immediately
		}*/
	}



	private void playerHit2(Stage primaryStage) {
		System.out.println("Player hit");
		blackjackGame.playerHit(); // Player draws a card
		System.out.println("after hit");
		updateHandDisplay(); // Update UI to show new card
		System.out.println("display");
		if (blackjackGame.isPlayerBusted()) {
			showEndScreen(primaryStage,"Busted!");
		}
		System.out.println("\n");
	}

	private void playerStay(Stage primaryStage) {
		System.out.println("You STAYED!\n");
		blackjackGame.bankerDraw();

		Double result = blackjackGame.evaluateWinnings();

		if (blackjackGame.dealerHandTotal() > 21){
			showEndScreen(primaryStage,"Dealer BUSTED!" );
		}
		else {
			showEndScreen(primaryStage,"Game Over! Result: " + result);
		}

	}

	private void showEndScreen(Stage primaryStage, String message) {
		VBox endScreen = new VBox(20);
		endScreen.setAlignment(Pos.CENTER);



		Text messageText = new Text(message);
		messageText.setFont(new Font("Comic Sans MS", 20));

		Text winningsText = new Text("Total Winnings: $" + blackjackGame.moneyLeft());
		winningsText.setFont(new Font("Comic Sans MS", 20));

		// Add play again button
		Button playAgainButton = new Button("Play Again");
		playAgainButton.setOnAction(e -> {
			if(blackjackGame.getTotalWinnings() <= 0.0){
				showEndScreen(primaryStage, "No money left :(");
			}else {
				blackjackGame.clearHands();
				betScreen(primaryStage, blackjackGame);
			}// Show the game screen again
		});




		Text playerHandText = new Text("Player's Hand: " + cardsToString(blackjackGame.getPlayerHand()));

		//playerHandText.setFont(new Font("Comic Sans MS", 16));

		Text dealerHandText = new Text("Dealer's Hand: " + cardsToString(blackjackGame.getBankerHand()));
		//dealerHandText.setFont(new Font("Comic Sans MS", 16));

		endScreen.getChildren().addAll(messageText, playerHandText, dealerHandText, winningsText, playAgainButton);


		Scene endScene = new Scene(endScreen, 700, 500);
		primaryStage.setScene(endScene);
	}

	private String cardsToString(List<Card> cards) {
		return cards.stream()
				.map(Card::toString)
				.collect(Collectors.joining(", "));
	}
}



