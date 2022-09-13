import acm.graphics.GCompound;

public class GHand extends GCompound {

    // the hand DATA to display
    private Hand hand;

    // the GCards you  will use to display them
    private GCard[] Gcards;


    public GHand(Hand hand){
        this.hand = hand;

        Gcards = new GCard[7];

        for(int i = 0; i < hand.getCount(); i++){
            Card card = hand.getCard(i);
            GCard gcard = new GCard(card);

            add(gcard, i * (gcard.getWidth() + (gcard.getWidth()/4)), 0);

            Gcards[i] = gcard;
        }
    }

    // get the total value of the hand
    public int getTotal(){
        return hand.getTotal();
    }
    // flip the first card over (dealer only)
    public void flipCard(int index){
        Gcards[index].flip();
    }

    // draw a card from the deck (this is called a 'hit' in Blackjack)
    public void hit(){
        hand.hit();
        // make a new GCard using the card our hand just drew
        Card temp = hand.getCard(hand.getCount() - 1);
        GCard gcard = new GCard(temp);
        // put the GCard in Gcards
        System.out.println("Hand count is now: " + hand.getCount());
        Gcards[hand.getCount() - 1] = gcard;

        //add the new gcard to the compound
        add(gcard, (hand.getCount() - 1) * (gcard.getWidth() * 1.25), 0 );

    }
}
