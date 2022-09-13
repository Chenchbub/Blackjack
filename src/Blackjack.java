import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Blackjack extends GraphicsProgram {

    // data about the game
    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;

    // labels to display info to the player
    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private GLabel Blackjack;

    // buttons for controls
    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton quitButton;

    // objects in the game
    private Deck deck;
    private GHand player;
    private GHand dealer;

    @Override
    public void init() {
        this.setBackground(Color.lightGray);

        deck = new Deck();

        // set up our buttons
        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");


        // add buttons to the screen
        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        // TODO: handle initial button visibility
        stayButton.setVisible(false);
        hitButton.setVisible(false);

        addActionListeners();

        // TODO: set up GLabels
        bankLabel = new GLabel("Bank: " + bank);
        balanceLabel = new GLabel("Balance: " + balance);
        wagerLabel = new GLabel("Wager: " + wager);
        add(bankLabel, 5, 15);
        add(balanceLabel, 5, 30);
        add(wagerLabel, 5, 425);


    }

    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Wager":
                wager();
                // TODO:
                break;
            case "Play":
                play();
                // TODO:
                break;
            case "Hit":
                // TODO:
                hit();
                break;
            case "Quit":
                System.exit(0);
                ;
                // TODO:
                break;
            case "Stay":
                // TODO:
                stay();
                break;
            default:
                System.out.println("I do not recognize that action command. Check your button text.");

        }

    }

    private void wager() {
        wager = Dialog.getInteger("Make Wager");
        if (wager > balance) {
            Dialog.showMessage("Insufficient Funds");
            wager = 0;
        }
        wagerLabel.setLabel("Wager: " + wager);
    }

    private void play() {
        if (wager == 0) {
            Dialog.showMessage("Please make a wager");
            return;
        }
        deck.shuffle();

        Hand playerHand = new Hand(deck, false);
        player = new GHand(playerHand);

        Hand dealerHand = new Hand(deck, true);
        dealer = new GHand(dealerHand);

        add(player, getWidth() / 2 - player.getWidth(), 250);
        add(dealer, getWidth() / 2 - dealer.getWidth(), 100);

        hitButton.setVisible(true);
        stayButton.setVisible(true);
    }

    private void hit() {
        player.hit();
        if (player.getTotal() > 21) {
            Dialog.showMessage("You Bust");
            return;
        }
    }

    private void stay() {
        dealer.flipCard(0);
        dealer.hit();
        while (dealer.getTotal() < 17) {
            dealer.hit();
        }
        checkWinner();
    }

    private void checkWinner() {
        if (dealer.getTotal() > 21) {
            Dialog.showMessage("Dealer Busts");
            ifWin();
        } else if (player.getTotal() > dealer.getTotal()) {
            Dialog.showMessage("You Win!");
            ifWin();
        } else if (player.getTotal() < dealer.getTotal()) {
            Dialog.showMessage("You Lose! Dumb Baby.");
            ifLose();
        } else if(player.getTotal() == dealer.getTotal()){
            Dialog.showMessage("You Tied!");
            ifTie();
        }
        gameResults();
    }
    private void gameResults(){
        if (bank <= 0){
            Dialog.showMessage("Dealer is out of money! You Win!");
            System.exit(0);
        } else if (balance <= 0){
            Dialog.showMessage("You ran out of money! You Lose!");
            System.exit(0);
        }
    }

    private void ifWin() {
        balance += wager;
        bank -= wager;
        balanceLabel.setLabel("Balance: " + balance);
        bankLabel.setLabel("Bank: " + bank);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        remove(player);
        remove(dealer);

    }

    private void ifLose() {
        balance -= wager;
        bank += wager;
        balanceLabel.setLabel("Balance: " + balance);
        bankLabel.setLabel("Bank: " + bank);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        remove(player);
        remove(dealer);

    }
    private void ifTie(){
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        remove(player);
        remove(dealer);

    }


    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}