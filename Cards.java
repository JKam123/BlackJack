import java.util.*;
import java.util.Scanner;

public class Cards{

    static int count=52; //the count represents the number of cards remaining in the deck

    public static int rand(int high){
        return (int) (high*Math.random()+1);
    }//returns a random integer with max of high

    public static void shuffle(String[] the_deck, int switches){//Shuffles a string deck for switches times
        String temp;
        int a; int b;
        for(int i=0; i<switches; i++){//For the number of switches change one card with another
            a = rand(52);//Card 1 to change
            b = rand(52);//Card 2 to change
            temp = the_deck[a-1];//Set a value to the value of a card so we can override it
            the_deck[a-1] = the_deck[b-1];//change the cards
            the_deck[b-1] = temp;
        }
    }

    public static String deal(String[] the_deck){//Deals the top card of a deck
        count=count-1;
        return the_deck[count];}

    public static int aces(String the_card){//Cheks if the card is an ace, if yes return 1
        if(the_card.charAt(0)=='A'){
            return 1;}
        else{
            return 0;}
    }

    public static int aces(String[] the_hand){//Gets the count of aces in a hand
        int sum=0;
        for(int i=0; i<the_hand.length;i++){
            sum = sum + aces(the_hand[i]);
        }
        return sum;
    }

    public static int aces(ArrayList the_hand){//The same as above only with an ArrayList
        int sum=0;
        for(int i=0; i<the_hand.size();i++){//For every element in the hand
            sum = sum + aces(the_hand.get(i).toString());//Check if its an ace
        }
        return sum;
    }

    public static int value(String the_card){//Gets the value of a single card
        char first = the_card.charAt(0);
        if (first=='1'|first=='J'|first=='Q'|first=='K'){//Is it a special card?
            return 10;
        }
        else if(first=='A'){//Is it an Ace
            return 11;}
        else{//Return the normal number
            return Character.getNumericValue(first);
        }
    }

    public static int value(String[] the_hand){//Returns the value of the hand as an Array
        int sum=0;
        for(int i=0; i<the_hand.length;i++){
            sum = sum + value(the_hand[i]);
        }
        return sum;
    }

    public static int value(ArrayList the_hand){//Returns the value of the hand as an ArrayList
        int sum=0;
        int num_aces=aces(the_hand);//Get how many aces are in the hand
        for(int i=0; i<the_hand.size();i++){
            sum = sum + value(the_hand.get(i).toString());//Add the cards together
        }
        while(num_aces>0 && sum>21){//If our score is greater than 21 make an ace to 1
            sum=sum-10;
            num_aces=num_aces-1;
        }
        return sum;
    }



    public static void main(String[] args){//Main stuff

        Scanner scan = new Scanner(System.in);//Register the scanner

        String[] deck = new String[52];//Init the deck
        String[] suit = new String[4];//Init the 4 colors
        int[] card = new int[13];//Every deck has 13 cards

        for (int i=0; i<card.length; i++){//Setting cards to their numbers
            card[i]=i+1;}
        String cardName;//sets Color of card
        suit[0] = "Clubs";
        suit[1] = "Diamonds";
        suit[2] = "Hearts" ;
        suit[3] = "Spades";


        for(int i=0; i<4; i++){//For each color
            for(int j=0; j<13; j++){//Add a card of the color of every value ir with a name
                if(j==0){cardName="Ace";}//Add Ace
                else if(j==10){cardName="Jack";}//Add jacks
                else if(j==11){cardName="Queen";}//Add queens
                else if(j==12){cardName="King";}//Add Kings
                else {cardName=Integer.toString(card[j]);}//Else the card is just a number
                deck[ 13*i+j ]= cardName + "_" +suit[i];//Add the card to the deck
            }
        }


        shuffle(deck, 1000);//Shuffle the deck for 1000 times

        String say;
        boolean state=true;
        // Starting up the game...
        ArrayList hand = new ArrayList();//The players hand
        ArrayList dealer_hand = new ArrayList();//The dealers hand
        dealer_hand.add( deal(deck) );//Give the dealer and us some cards
        dealer_hand.add( deal(deck) );
        hand.add( deal(deck) );

        while(state){//Run this until we lost or we stop getting new cards

            hand.add( deal(deck) );

            System.out.println("Dealer showing: " + dealer_hand.get(1));//Whats shows the dealer?
            System.out.println("Contents of hand: " + hand);//Our hand
            System.out.println("Your score is: " + value(hand));//And our score

            if(value(hand)>21){//If our score is more than 21 we are BUST
                System.out.println("BUST!!!!");
                break;
            }

            System.out.println( "hit[H] or stand[S]?");//If not bust do we want to hit or stand?
            say=scan.nextLine();
            if(say.equals("H")){state=true;}
            else{state=false;}
        }

        while( value(dealer_hand)<17 ){//If the dealers hand is less than 17 he takes a card
            dealer_hand.add( deal(deck) );
        }


        System.out.println("Dealer has: " + dealer_hand);//The dealers cards
        System.out.println("Dealer score is: " + value(dealer_hand));

        if( (value(hand)>value(dealer_hand) && value(hand)<22) | (value(dealer_hand) > 21) ){// We have a greater score so we WIN
            System.out.println( "YOU WIN !!!!");
        }
        else{System.out.println( "YOU LOSE. BOO !!!!");}//Or we have less so we LOSE


    }
}