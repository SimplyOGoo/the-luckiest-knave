//v1.0 Dev Tools inccluded
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class LuckyKnave {
    public static Scanner scanner = new Scanner(System.in);
    public static int tokens = 20;
    public static int bet = 0;
    public static int cardTotal = 0;
    public static int dealerTotal = 0;
    //we only store 7 player cards and 7 dealers cards as that is the max amount they could have before they can't hit anymore (Not including aces, and yes this should be a list instead)
    public static ArrayList<ArrayList<Object>> peakCards = new ArrayList<>();
    public static String card1 = "";
    public static String card2 = "";
    public static String card3 = "";
    public static String card4 = "";
    public static String card5 = "";
    public static String card6 = "";
    public static String card7 = "";
    public static String card8 = "";
    public static String card9 = "";
    public static String card10 = "";
    public static String card11 = "";
    public static String dealCard1 = "";
    public static String dealCard2 = "";
    public static String dealCard3 = "";
    public static String dealCard4 = "";
    public static String dealCard5 = "";
    public static String dealCard6 = "";
    public static String dealCard7 = "";
    public static boolean setAce = false;
    public static int ace = 1;
    public static int hitCount = 0;
    public static int dealHitCount = 0;
    public static boolean bustP = false;
    public static boolean bustD = false;
    public static boolean push = false;
    public static boolean TweOne = false;
    public static boolean turn = true;
    //upgrades below don't need to be reset
    public static int shopCounter = 0;
    public static ArrayList<String> upgrades = new ArrayList<>();
    public static int bankTokens = 0;
    public static boolean twoEasy = false;
    public static int makeDouble = 0;
    public static int insurance = 0;
    public static boolean roundInsurance = false;
    public static int lastBet = 0;
    public static int lastTokens = 0;
    public static boolean boxBoost = false;
    public static double steakMul = 1.0;
    public static int steakInts = 0;
    public static boolean steakActive = false;
    public static int winInts = 0;
    public static ArrayList<Integer> cost = new ArrayList<>();
    public static double costMul = 1.0;
    public static ArrayList<Integer> perishables = new ArrayList<>();
    public static ArrayList<String> playUpgrades = new ArrayList<>();

    public static void assignUpgrades(){
        //All shop upgrades after you reach 150 tokens will increase in value by 10% of your current tokens every 50 tokens the percentage goes down by 0.5% (Ex. 200 - 9.5%)
        LuckyKnave.upgrades.add("Bank"); //Let's the player store tokens and based on what is put in there they make passive token earnings (This will stop at some point so the Player can still and is more likely to lose than they would have to begin with) - Cost 100
        LuckyKnave.upgrades.add("Money Maker"); //wins gain 1.1x more - Cost 75
        LuckyKnave.upgrades.add("Box Cutter"); //second try to win if dealer wins (Not if you bust) - Cost 200 PERISHABLE
        LuckyKnave.upgrades.add("Deck Peak"); //You get to decide once you see your first 2 cards if you would like to redraw them both - Cost 60 PERISHABLE
        LuckyKnave.upgrades.add("What's yours is mine"); //Player may make the turn push at anytime while it is still their turn - Cost 80 PERISHABLE
        LuckyKnave.upgrades.add("Make it Double"); //If your first 2 cards are the same you will get 15% of your current tokens after the round ends (SO if you go all in you can still lose) - Cost 30 
        LuckyKnave.upgrades.add("Keep it Simple"); //If your starting hand is equal to 10 or 15 and uses at least one 5 then you automatically get Blackjack - Cost 30 PERISHABLE
        LuckyKnave.upgrades.add("Two Easy"); //You can make the next card your draw 2 - Cost 50 PERISHABLE
        LuckyKnave.upgrades.add("Insurance"); //When you activate insurance and you lose the round you get half of what you bet back, if you win the round with insurance on you lose it still - Cost 50 PERISHABLE
        LuckyKnave.upgrades.add("Filling the Box"); //if you lost over 90% of your token in the last round, if you win this round your bet is worth 1.5x more - Cost 80
        LuckyKnave.upgrades.add("Heavy Cards"); //All Face cards value at 11 (Aces are still 11 and 1), 2s and 3s no do not have any value - Cost 50 
        LuckyKnave.upgrades.add("Push me some more"); //When you get a push you win 0.5 of your bet - Cost 120
        LuckyKnave.upgrades.add("High Steaks"); //While owning this upgrade and your tokens are below 30 you can get bet multipliers when you win and only gain 0.3 of your bet rounded up, this multiplier may be used at anytime while below 30 token - Cost 100
        LuckyKnave.upgrades.add("Floor Stains"); //When on a win streak you continue to get 1.02% more for everywin (Win 3 rounds in row 1.06x), but you lose 1.02% more to the dealer if you lose - Cost 75
        LuckyKnave.upgrades.add("Knave's Kin"); //When you have a Jack and 2 you get 1.2x more winnings - Cost 50
        //Adds all items 
        LuckyKnave.perishables.add(3);
        LuckyKnave.perishables.add(4);
        LuckyKnave.perishables.add(5);
        LuckyKnave.perishables.add(7);
        LuckyKnave.perishables.add(8);
        LuckyKnave.perishables.add(9);
        //Assigns the cost to each item in chronological order
        LuckyKnave.cost.add(100);
        LuckyKnave.cost.add(75);
        LuckyKnave.cost.add(200);
        LuckyKnave.cost.add(60);
        LuckyKnave.cost.add(80);
        LuckyKnave.cost.add(30);
        LuckyKnave.cost.add(30);
        LuckyKnave.cost.add(50);
        LuckyKnave.cost.add(50);
        LuckyKnave.cost.add(80);
        LuckyKnave.cost.add(50);
        LuckyKnave.cost.add(120);
        LuckyKnave.cost.add(100);
        LuckyKnave.cost.add(75);
        LuckyKnave.cost.add(50);
    }

    public static String deal(){
        //deals cards to player and dealer
        Random rand = new Random();
        int ran = rand.nextInt(13) + 1;
        if (twoEasy(false) == true && LuckyKnave.hitCount >= 1){
            ran = 2;
            System.out.println("in if state:" + ran);
        }
        String deal = String.valueOf(ran);
        cardTotal(ran);
        if (LuckyKnave.hitCount == 0 && !LuckyKnave.card1.equals("")){
            ArrayList<Object> peakCard = new ArrayList<>();
            peakCard.add(ran);
            peakCard.add(translate(ran));
            LuckyKnave.peakCards.add(peakCard);
        } else if (LuckyKnave.hitCount == 0 && LuckyKnave.card2.equals("")){
            ArrayList<Object> peakCard = new ArrayList<>();
            peakCard.add(ran);
            peakCard.add(translate(ran));
            LuckyKnave.peakCards.add(peakCard);
        }
        if (ran == 11 || ran == 12 || ran == 13 || ran == 1){
            return translate(ran);
        } else {
            return deal;
        }
    }

    public static String translate(int card){
        //if one of the randomly geberated integers is 1, 11, 12, or 13, it becomes one of the face cards and is given the approriate String to represent it
        if(card == 1){
                return "a Ace";
            } else if (card == 11){
                return "a Jack";
            } else if (card == 12){
                return "a Queen";
            } else if (card == 13){
                return "a King";
            } else {
                return "";
            }
    }

    public static void cardTotal(int c){
        //where the card value total is added up adds whatever c is to the total and if it is 1, 11, 12, or 13 adds the appropriate value to the total
        if (LuckyKnave.turn == true){
        if (twoEasy(true) == true && LuckyKnave.hitCount >= 1){
            LuckyKnave.cardTotal = LuckyKnave.cardTotal + 2;
        } else if(c == 1){
            LuckyKnave.cardTotal = LuckyKnave.cardTotal + aceValue(translate(c));
        } else if (c == 11){
            if (heavyCards() == false){
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 10;
            } else {
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 11;
            }
        } else if (c == 12){
            if (heavyCards() == false){
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 10;
            } else {
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 11;
            }
        } else if (c == 13){
            if (heavyCards() == false){
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 10;
            } else {
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + 11;
            }
        } else {
            if (heavyCards() == false){
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + c;
            } else {
                LuckyKnave.cardTotal = LuckyKnave.cardTotal + c;
                if (c == 2){
                    LuckyKnave.cardTotal = LuckyKnave.cardTotal - 2;
                } else if (c == 3){
                    LuckyKnave.cardTotal = LuckyKnave.cardTotal - 3;
                }
            }
        }
        } else {
        if(c == 1){
            LuckyKnave.dealerTotal = LuckyKnave.dealerTotal + aceValue(translate(c));
        } else if (c == 11){
            LuckyKnave.dealerTotal = LuckyKnave.dealerTotal + 10;
        } else if (c == 12){
           LuckyKnave.dealerTotal = LuckyKnave.dealerTotal + 10;
        } else if (c == 13){
            LuckyKnave.dealerTotal = LuckyKnave.dealerTotal + 10;
        } else {
            LuckyKnave.dealerTotal = LuckyKnave.dealerTotal + c;
        }
        }
    }

    public static void startRound(){  
        //starts the round with your bet
        bet();
        fillBox();
        //deals your cards
        LuckyKnave.card1 = deal();
        LuckyKnave.card2 = deal();
        aceValueCheck();
        deckPeak();
        keepItSimple();
        makeItDouble();
        //if the player doesn't get Blackjack on first hand it plays the rest of the game
        if (LuckyKnave.cardTotal != 21 && TweOne == false){
        //will repeat while it is your turn, your turn ends when you stand or bust
        while (LuckyKnave.turn == true){
            //shows your hand
            displayHand();
            if (LuckyKnave.cardTotal != 21){
                System.out.println("Hit or Stand?");
                //player chooses to hit or stand
                String option = LuckyKnave.scanner.nextLine();
                //hit includes the stand options to make sure no other text is shown
                hit(option);
                bustCheck();
            } else {
                LuckyKnave.turn = false;
                System.out.println("You got 21?!?");
                System.out.println("The Box has to cook now!");
                LuckyKnave.TweOne = true;
            }
        }
        } else {
            //prints text if you did get Blackjack
            System.out.println("You got " + LuckyKnave.card1 + " and " + LuckyKnave.card2);
            System.out.println("You got Blackjack! That's the name of the Game!");
            System.out.println("Watch me match that!");
            LuckyKnave.TweOne = true;
        }
        //after the player turn ends the dealer's turn begins 
        LuckyKnave.turn = false;
        dealerTurn();
    }

    public static void displayHand(){
        //displays all cards in either the player's hand or dealer's hand based on whether it is the player's turn or not
        if (LuckyKnave.turn == true){
        System.out.print("\nYou got " + LuckyKnave.card1 + " and " + LuckyKnave.card2);
        if(LuckyKnave.hitCount >= 1){
            System.out.print(" and " + LuckyKnave.card3);
        }
        if (LuckyKnave.hitCount >= 2){
            System.out.print(" and " + LuckyKnave.card4);
        }
        if (LuckyKnave.hitCount >= 3){
            System.out.print(" and " + LuckyKnave.card5);
        }
        if (LuckyKnave.hitCount >= 4){
            System.out.print("\nand " + LuckyKnave.card6);
        }
        if (LuckyKnave.hitCount >= 5){
            System.out.print(" and " + LuckyKnave.card7);
        }
        if (LuckyKnave.hitCount >= 6){
            System.out.print(" and " + LuckyKnave.card8);
        }
        if (LuckyKnave.hitCount >= 7){
            System.out.print(" and " + LuckyKnave.card9);
        }
        if (LuckyKnave.hitCount >= 8){
            System.out.print(" and " + LuckyKnave.card10);
        }
        if (LuckyKnave.hitCount >= 9){
            System.out.print(" and " + LuckyKnave.card11);
        }
        System.out.println("\nTotal hand: " + LuckyKnave.cardTotal);
        } else {
        System.out.print("\nI got " + LuckyKnave.dealCard1 + " and " + LuckyKnave.dealCard2);
        if(LuckyKnave.dealHitCount >= 1){
            System.out.print(" and " + LuckyKnave.dealCard3);
        }
        if (LuckyKnave.dealHitCount >= 2){
            System.out.print(" and " + LuckyKnave.dealCard4);
        }
        if (LuckyKnave.dealHitCount >= 3){
            System.out.print(" and " + LuckyKnave.dealCard5);
        }
        if (LuckyKnave.dealHitCount >= 4){
            System.out.print("\nand " + LuckyKnave.dealCard6);
        }
        if (LuckyKnave.dealHitCount >= 5){
            System.out.print(" and " + LuckyKnave.dealCard7);
        }
        System.out.println("\nBox's Total hand: " + LuckyKnave.dealerTotal);
        }
    }

    public static void bet(){
        //System prints messages appropriate to what the player tries to bet
        boolean allIn = false;
        int instTime = 0;
        String text = "";
        System.out.println("What is your bet? (You have " + LuckyKnave.tokens + " tokens)");
        while (LuckyKnave.bet <= 0){
            if (LuckyKnave.scanner.hasNextInt()){
                LuckyKnave.bet = LuckyKnave.scanner.nextInt();
                LuckyKnave.scanner.nextLine();
            } else {
                text = LuckyKnave.scanner.next();
            }
            if (LuckyKnave.bet > 0){
            //compares bet to what you actually have
            if (LuckyKnave.bet > LuckyKnave.tokens){
                System.out.println("Hey! Who you think you are betting more than you have?");
                System.out.println("Don't you dare! Think you getting away with that");
            } else if (LuckyKnave.bet == LuckyKnave.tokens){
               System.out.println("Ah All-in I see");
               System.out.println("Your either smart or very generous");
               System.out.println("This is for me right?");
               System.out.println("Of course not! your here to win big!"); 
               allIn = true;
            } else if (LuckyKnave.bet <= 0){
                if (instTime == 0){
                    System.out.println("Who do you think you are?");
                    System.out.println("We can't take 0! Or negatives! We don't give you money!");
                    System.out.println("I am going to wait for you to put a real bet down...");
                } else if (instTime == 1){
                    System.out.println("Hey! I'm not kidding here!");
                    System.out.println("I don't have all day if your going to be playing this twice!");
                } else if (instTime == 2){
                    System.out.println("Oh so three time now? Your really asking for it");
                    System.out.println("Say you know third times the charm right?");
                    System.out.println("So it's a lucky number...");
                    System.out.print("\nSo your so lucky to win... \nA ONE WAY TRIP OUT OF HERE!");
                    System.exit(0);
                }
                instTime++;
            }
            } else if (text.equals("credits")){
                credits();
                System.out.println("\nBeautiful I know...");
                System.out.println("But, back to betting for you!");
                System.out.println("And no we don't take credits");
            } else if (text.equals("High Steaks") || text.equals("high steaks") || text.equals("High steaks") || text.equals("high Steaks") || text.equals("HS") || text.equals("hs")){
                LuckyKnave.steakActive = true;
                System.out.println("High Steak Multipliuer Activated");
            } else if (text.equals("upgrade")){
                addUpgrades();
            } else if (text.equals("more")){
                System.out.println("How many more tokens");
                int amount = LuckyKnave.scanner.nextInt();
                LuckyKnave.tokens += amount;
                System.out.println("Added "+ amount + " more tokens");
            } else {
                System.out.println("Numbers please, put 1 or something");
                LuckyKnave.bet = 0;
            }
        }
        if (allIn == false){
            System.out.println("Your bet is: " + bet);
        } else {
            System.out.println("Your bet is: ALL IN");
        }
    }

    public static void hit(String o){
        //record new card player is dealt if the player decides to hit
        if(o.equals("Hit") || o.equals("hit") || o.equals("1") || o.equals("h") || o.equals("H")){
            LuckyKnave.hitCount++;
            if(LuckyKnave.hitCount == 1){
                LuckyKnave.card3 = deal();
                System.out.println("hit count:" + LuckyKnave.hitCount);
            } else if (LuckyKnave.hitCount == 2){
                LuckyKnave.card4 = deal();
            } else if (LuckyKnave.hitCount == 3){
                LuckyKnave.card5 = deal();
            } else if (LuckyKnave.hitCount == 4){
                LuckyKnave.card6 = deal();
            } else if (LuckyKnave.hitCount == 5){
                LuckyKnave.card7 = deal();
            } else if (LuckyKnave.hitCount == 6){
                LuckyKnave.card8 = deal();
            } else if (LuckyKnave.hitCount == 7){
                LuckyKnave.card9 = deal();
            } else if (LuckyKnave.hitCount == 8){
                LuckyKnave.card10 = deal();
            } else if (LuckyKnave.hitCount == 9){
                LuckyKnave.card11 = deal();
            }
            cardCheck();
        } else {
            //if the player did not hit it goes to stand
            stand(o);
        }
    }

    public static void stand(String o){
        if (o.equals("Stand") || o.equals("stand") || o.equals("S") || o.equals("s") || o.equals("0")){
            //if the player did stand their turn is over
            LuckyKnave.turn = false;
        } else if (o.equals("insurance") || o.equals("Insurance") || o.equals("Insure") || o.equals("insure")){
            insurance();
            System.out.println("Insurance is in use");
        } else if (whatYourMine(o)){
            LuckyKnave.push = true;
            System.out.println("What's yours is mine used");
        } else if (o.equals("Two Easy") || o.equals("Two easy") || o.equals("two easy") || o.equals("two Easy") || o.equals("TE") || o.equals("te")){
            twoEasy(false);
            LuckyKnave.twoEasy = true;
            System.out.println("Two Easy Used");
        } else if (o.equals("win")){
            LuckyKnave.bustD = true;
        } else {
            //if the player did not input viable stand inputs or viable hit inputs (since this is called from the hit void) it tells you to reinput
            System.out.println("Well I'm not sure what your telling me now");
            System.out.println("Like actually tell me next time");
        }
    }

    public static void cardCheck(){
        //checks to see if player's or dealer's hand when called only has a max of 4 of 1 type of card depending on if it is the player's turn or not
        if (LuckyKnave.turn == true){
        ArrayList<String> dealtCards = new ArrayList<>();
        dealtCards.add(LuckyKnave.card1);
        dealtCards.add(LuckyKnave.card2);
        if(LuckyKnave.hitCount == 1){
                dealtCards.add(LuckyKnave.card3);
            } else if (LuckyKnave.hitCount == 2){
                dealtCards.add(LuckyKnave.card4);
            } else if (LuckyKnave.hitCount == 3){
                dealtCards.add(LuckyKnave.card5);
            } else if (LuckyKnave.hitCount == 4){
                dealtCards.add(LuckyKnave.card6);
            } else if (LuckyKnave.hitCount == 5){
                dealtCards.add(LuckyKnave.card7);
            } else if (LuckyKnave.hitCount == 6){
                dealtCards.add(LuckyKnave.card8);
            } else if (LuckyKnave.hitCount == 7){
                dealtCards.add(LuckyKnave.card9);
            } else if (LuckyKnave.hitCount == 8){
                dealtCards.add(LuckyKnave.card10);
            } else if (LuckyKnave.hitCount == 9){
                dealtCards.add(LuckyKnave.card11);
            }
            int instTwo = 0;
            int instThr = 0;
            int instFou = 0;
            int instFiv = 0;
            int instSix = 0;
            int instSev = 0;
            int instEig = 0;
            int instNin = 0;
            int instTen = 0;
            int instJac = 0;
            int instQue = 0;
            int instKin = 0;
            int instAce = 0;
            //records how many times the card appears
        for (int i = 0; i < dealtCards.size(); i++){
            if (dealtCards.get(i).equals("2")){
                instTwo++;
            } else if (dealtCards.get(i).equals("3")){
                instThr++;
            } else if (dealtCards.get(i).equals("4")){
                instFou++;
            } else if (dealtCards.get(i).equals("5")){
                instFiv++;
            } else if (dealtCards.get(i).equals("6")){
                instSix++;
            } else if (dealtCards.get(i).equals("7")){
                instSev++;
            } else if (dealtCards.get(i).equals("8")){
                instEig++;
            } else if (dealtCards.get(i).equals("9")){
                instNin++;
            } else if (dealtCards.get(i).equals("a Jack")){
                instJac++;
            } else if (dealtCards.get(i).equals("a Queen")){
                instQue++;
            } else if (dealtCards.get(i).equals("a King")){
                instKin++;
            } else if (dealtCards.get(i).equals("a Ace")){
                instAce++;
            } else if (dealtCards.get(i).equals("10")){
                instTen++;
            }
        }
        //if it appears more than 4 times the game redraws it
        if (instTwo > 4){
            deckRedraw();
        } else if (instThr > 4){
            deckRedraw();
        } else if (instFou > 4){
            deckRedraw();
        } else if (instFiv > 4){
            deckRedraw();
        } else if (instSix > 4){
            deckRedraw();
        } else if (instSev > 4){
            deckRedraw();
        } else if (instEig > 4){
            deckRedraw();
        } else if (instNin > 4){
            deckRedraw();
        } else if (instTen > 4){
            deckRedraw();
        } else if (instJac > 4){
            deckRedraw();
        } else if (instQue > 4){
            deckRedraw();
        } else if (instKin > 4){
            deckRedraw();
        } else if (instAce > 4){
            deckRedraw();
        }
        } else { //repeats player's hand check, but tuned to the dealer's hand
        ArrayList<String> dealtCards = new ArrayList<>();
        dealtCards.add(LuckyKnave.dealCard1);
        dealtCards.add(LuckyKnave.dealCard2);
        if(LuckyKnave.dealHitCount == 1){
                dealtCards.add(LuckyKnave.dealCard3);
            } else if (LuckyKnave.dealHitCount == 2){
                dealtCards.add(LuckyKnave.dealCard4);
            } else if (LuckyKnave.dealHitCount == 3){
                dealtCards.add(LuckyKnave.dealCard5);
            } else if (LuckyKnave.dealHitCount == 4){
                dealtCards.add(LuckyKnave.dealCard6);
            } else if (LuckyKnave.dealHitCount == 5){
                dealtCards.add(LuckyKnave.dealCard7);
            }
            int instTwo = 0;
            int instThr = 0;
            int instFou = 0;
            int instFiv = 0;
            int instSix = 0;
            int instSev = 0;
            int instEig = 0;
            int instNin = 0;
            int instTen = 0;
            int instJac = 0;
            int instQue = 0;
            int instKin = 0;
            int instAce = 0;
        for (int i = 0; i < dealtCards.size(); i++){
            if (dealtCards.get(i).equals("2")){
                instTwo++;
            } else if (dealtCards.get(i).equals("3")){
                instThr++;
            } else if (dealtCards.get(i).equals("4")){
                instFou++;
            } else if (dealtCards.get(i).equals("5")){
                instFiv++;
            } else if (dealtCards.get(i).equals("6")){
                instSix++;
            } else if (dealtCards.get(i).equals("7")){
                instSev++;
            } else if (dealtCards.get(i).equals("8")){
                instEig++;
            } else if (dealtCards.get(i).equals("9")){
                instNin++;
            } else if (dealtCards.get(i).equals("a Jack")){
                instJac++;
            } else if (dealtCards.get(i).equals("a Queen")){
                instQue++;
            } else if (dealtCards.get(i).equals("a King")){
                instKin++;
            } else if (dealtCards.get(i).equals("a Ace")){
                instAce++;
            } else if (dealtCards.get(i).equals("10")){
                instTen++;
            }
        }
        if (instTwo >= 4){
            deckRedraw();
        } else if (instThr >= 4){
            deckRedraw();
        } else if (instFou >= 4){
            deckRedraw();
        } else if (instFiv >= 4){
            deckRedraw();
        } else if (instSix >= 4){
            deckRedraw();
        } else if (instSev >= 4){
            deckRedraw();
        } else if (instEig >= 4){
            deckRedraw();
        } else if (instNin >= 4){
            deckRedraw();
        } else if (instTen >= 4){
            deckRedraw();
        } else if (instJac >= 4){
            deckRedraw();
        } else if (instQue >= 4){
            deckRedraw();
        } else if (instKin >= 4){
            deckRedraw();
        } else if (instAce >= 4){
            deckRedraw();
        }
        }
    }

    public static void deckRedraw(){
        //redraws the card that was check either by cardCheck() or dealerPlayerCheck() depending on if it is the player's turn or not and what card was just drawn if not said not to supposed to exit by the Checks
        if (LuckyKnave.turn == true){
        if (hitCount == 0){
            LuckyKnave.card1 = deal();
            LuckyKnave.card2 = deal();
            aceValueCheck();
        }
        if(LuckyKnave.hitCount == 1){
            LuckyKnave.card3 = deal();
        } else if (LuckyKnave.hitCount == 2){
            LuckyKnave.card4 = deal();
        } else if (LuckyKnave.hitCount == 3){
            LuckyKnave.card5 = deal();
        } else if (LuckyKnave.hitCount == 4){
            LuckyKnave.card6 = deal();
        } else if (LuckyKnave.hitCount == 5){
            LuckyKnave.card7 = deal();
        } else if (LuckyKnave.hitCount == 6){
            LuckyKnave.card8 = deal();
        } else if (LuckyKnave.hitCount == 7){
            LuckyKnave.card9 = deal();
        } else if (LuckyKnave.hitCount == 8){
            LuckyKnave.card10 = deal();
        } else if (LuckyKnave.hitCount == 9){
            LuckyKnave.card11 = deal();
        }
        } else { //dealers card redraw
        if(LuckyKnave.dealHitCount == 1){
            LuckyKnave.dealCard3 = deal();
        } else if (LuckyKnave.dealHitCount == 2){
            LuckyKnave.dealCard4 = deal();
        } else if (LuckyKnave.dealHitCount == 3){
            LuckyKnave.dealCard5 = deal();
        } else if (LuckyKnave.dealHitCount == 4){
            LuckyKnave.dealCard6 = deal();
        } else if (LuckyKnave.dealHitCount == 5){
            LuckyKnave.dealCard7 = deal();
        }
        }
    }

    public static void bustCheck(){
        //checks to see if the player or dealer busted based on if it is the player's turn or not
        if (LuckyKnave.turn == true){
            if(LuckyKnave.cardTotal > 21 && LuckyKnave.ace == 1){
                LuckyKnave.bustP = true;
                displayHand();
                System.out.println("You Busted");
                LuckyKnave.turn = false;
            } else if (LuckyKnave.cardTotal > 21 && LuckyKnave.ace == 11){
                LuckyKnave.bustP = false;
                aceReValue();
            }
        } else {
            if (LuckyKnave.dealerTotal > 21){
                LuckyKnave.bustD = true;
            }
        }
    }

    public static void pushCheck(){
        //will check to see if the dealer and player tie so player gets bet back and round restarts
        if (cardTotal == dealerTotal){
            LuckyKnave.push = true;
        }
    }

    public static int aceValue(String val){
        //will automatically make aces value 11 or 1 based on what is in your hand and what will make you bust
        //this feeds the value of the ace to card total where the value is added to the total
        if (LuckyKnave.turn == true){
            if (LuckyKnave.card1.equals("") && val.equals("a Ace")){
                assignAce(1);
                return aceNum();
            } else if (!LuckyKnave.card1.equals("") && val.equals("a Ace")){
                assignAce(2);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("")) && val.equals("a Ace")){
                assignAce(3);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("") && !LuckyKnave.card3.equals("")) && val.equals("a Ace")){
                assignAce(4);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("") && !LuckyKnave.card3.equals("") && !LuckyKnave.card4.equals("")) && val.equals("a Ace")){
                assignAce(5);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("") && !LuckyKnave.card3.equals("") && !LuckyKnave.card4.equals("") && !LuckyKnave.card5.equals("")) && val.equals("a Ace")){
                assignAce(6);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("") && !LuckyKnave.card3.equals("") && !LuckyKnave.card4.equals("") && !LuckyKnave.card5.equals("") && !LuckyKnave.card6.equals("")) && val.equals("a Ace")){
                assignAce(7);
                return aceNum();
            } else if ((!LuckyKnave.card1.equals("") && !LuckyKnave.card2.equals("") && !LuckyKnave.card3.equals("") && !LuckyKnave.card4.equals("") && !LuckyKnave.card5.equals("") && !LuckyKnave.card6.equals("") && !LuckyKnave.card7.equals("")) && val.equals("a Ace")){
                assignAce(8);
                return aceNum();
            }
        } else {
            if (LuckyKnave.dealCard1.equals("") && val.equals("a Ace")){
                return aceNum();
            } else if (!LuckyKnave.dealCard1.equals("") && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("")) && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("") && !LuckyKnave.dealCard3.equals("")) && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("") && !LuckyKnave.dealCard3.equals("") && !LuckyKnave.dealCard4.equals("")) && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("") && !LuckyKnave.dealCard3.equals("") && !LuckyKnave.dealCard4.equals("") && !LuckyKnave.dealCard5.equals("")) && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("") && !LuckyKnave.dealCard3.equals("") && !LuckyKnave.dealCard4.equals("") && !LuckyKnave.dealCard5.equals("") && !LuckyKnave.dealCard6.equals("")) && val.equals("a Ace")){
                return aceNum();
            } else if ((!LuckyKnave.dealCard1.equals("") && !LuckyKnave.dealCard2.equals("") && !LuckyKnave.dealCard3.equals("") && !LuckyKnave.dealCard4.equals("") && !LuckyKnave.dealCard5.equals("") && !LuckyKnave.dealCard6.equals("") && !LuckyKnave.dealCard7.equals("")) && val.equals("a Ace")){
                return aceNum();
            }
        }
        return 1;
    }

    public static void assignAce(int card){
        //assigns the value of the ace used in card total to the appropriate ace variable
        if (LuckyKnave.setAce == false){
        if (card == 1){
            LuckyKnave.ace = aceNum();
        } else if (card == 2){
            LuckyKnave.ace = aceNum();
        } else if (card == 3){
            LuckyKnave.ace = aceNum();
        } else if (card == 4){
            LuckyKnave.ace = aceNum();
        } else if (card == 5){
            LuckyKnave.ace = aceNum();
        } else if (card == 6){
            LuckyKnave.ace = aceNum();
        } else if (card == 7){
            LuckyKnave.ace = aceNum();
        } else if (card == 8){
            LuckyKnave.ace = aceNum();
        }
        LuckyKnave.setAce = true;
        }
    }

    public static int aceNum(){
        //this void acts as a way to save lines and decides whether the ace of what card in aceValue is a 11 or 1 based on player or dealer's current hand total
        if ((LuckyKnave.cardTotal < 11 && LuckyKnave.turn == true) || (LuckyKnave.dealerTotal < 11 && LuckyKnave.turn == false)){
            return 11;
        } else {
            return 1;
        }
    }

    public static void aceValueCheck(){
        if (LuckyKnave.ace == 1 && LuckyKnave.cardTotal < 11){
            if (setAce == true){
                if (LuckyKnave.ace == 11 && cardTotal < 11){
                    LuckyKnave.cardTotal += 10;
                }
            }
        }
    }

    public static void aceReValue(){
        //if the one ace that can be 11 is 11 and the cardTotal is above 11 the ace value changes to one and changes the cardTotal apporpriatly
            LuckyKnave.ace = 1;
            LuckyKnave.cardTotal = LuckyKnave.cardTotal - 10;
    }

    public static void dealerTurn(){
        //plays out the dealer's turn
        //If the players busts it skips the dealer's turn
        if (LuckyKnave.bustP == true || LuckyKnave.push == true || LuckyKnave.TweOne == true || LuckyKnave.bustD == true){
            endRound();
        } else {
        //After the player stands
        LuckyKnave.dealCard1 = deal();
        LuckyKnave.dealCard2 = deal();
        displayHand();
        //dealer's in blackjack can not hit over 17 so the loop runs until the dealer's total is over 17
        while (LuckyKnave.dealerTotal < 17){
            LuckyKnave.dealHitCount++;
            if (LuckyKnave.dealHitCount == 1){
                LuckyKnave.dealCard3 = deal();
            } else if (LuckyKnave.dealHitCount == 2){
                LuckyKnave.dealCard4 = deal();
            } else if (LuckyKnave.dealHitCount == 3){
                LuckyKnave.dealCard5 = deal();
            } else if (LuckyKnave.dealHitCount == 4){
                LuckyKnave.dealCard6 = deal();
            } else if (LuckyKnave.dealHitCount == 5){
                LuckyKnave.dealCard7 = deal();
            }
            //does all checks for dealer's hand ti be legal redrawiung if nessecarey
            cardCheck();
            dealerPlayerCheck();
            //displays hand and then checks to see if it is a bust or push
            displayHand();
            bustCheck();
            pushCheck();
        }
        endRound();
        }
    }

    public static void dealerPlayerCheck(){
        //will check to make sure there is not more than 4 of the same card out between the player and dealer
        ArrayList<String> dealtCards = new ArrayList<>();
        dealtCards.add(LuckyKnave.dealCard1);
        dealtCards.add(LuckyKnave.dealCard2);
        dealtCards.add(LuckyKnave.card1);
        dealtCards.add(LuckyKnave.card2);
        if(LuckyKnave.dealHitCount == 1){
                dealtCards.add(LuckyKnave.dealCard3);
            } else if (LuckyKnave.dealHitCount == 2){
                dealtCards.add(LuckyKnave.dealCard4);
            } else if (LuckyKnave.dealHitCount == 3){
                dealtCards.add(LuckyKnave.dealCard5);
            } else if (LuckyKnave.dealHitCount == 4){
                dealtCards.add(LuckyKnave.dealCard6);
            } else if (LuckyKnave.dealHitCount == 5){
                dealtCards.add(LuckyKnave.dealCard7);
            }
        if(LuckyKnave.hitCount == 1){
                dealtCards.add(LuckyKnave.card3);
            } else if (LuckyKnave.hitCount == 2){
                dealtCards.add(LuckyKnave.card4);
            } else if (LuckyKnave.hitCount == 3){
                dealtCards.add(LuckyKnave.card5);
            } else if (LuckyKnave.hitCount == 4){
                dealtCards.add(LuckyKnave.card6);
            } else if (LuckyKnave.hitCount == 5){
                dealtCards.add(LuckyKnave.card7);
            } else if (LuckyKnave.hitCount == 6){
                dealtCards.add(LuckyKnave.card8);
            } else if (LuckyKnave.hitCount == 7){
                dealtCards.add(LuckyKnave.card9);
            } else if (LuckyKnave.hitCount == 8){
                dealtCards.add(LuckyKnave.card10);
            } else if (LuckyKnave.hitCount == 9){
                dealtCards.add(LuckyKnave.card11);
            }
        int instTwo = 0;
        int instThr = 0;
        int instFou = 0;
        int instFiv = 0;
        int instSix = 0;
        int instSev = 0;
        int instEig = 0;
        int instNin = 0;
        int instTen = 0;
        int instJac = 0;
        int instQue = 0;
        int instKin = 0;
        int instAce = 0;
        for (int i = 0; i < dealtCards.size(); i++){
            if (dealtCards.get(i).equals("2")){
                instTwo++;
            } else if (dealtCards.get(i).equals("3")){
                instThr++;
            } else if (dealtCards.get(i).equals("4")){
                instFou++;
            } else if (dealtCards.get(i).equals("5")){
                instFiv++;
            } else if (dealtCards.get(i).equals("6")){
                instSix++;
            } else if (dealtCards.get(i).equals("7")){
                instSev++;
            } else if (dealtCards.get(i).equals("8")){
                instEig++;
            } else if (dealtCards.get(i).equals("9")){
                instNin++;
            } else if (dealtCards.get(i).equals("a Jack")){
                instJac++;
            } else if (dealtCards.get(i).equals("a Queen")){
                instQue++;
            } else if (dealtCards.get(i).equals("a King")){
                instKin++;
            } else if (dealtCards.get(i).equals("a Ace")){
                instAce++;
            } else if (dealtCards.get(i).equals("10")){
                instTen++;
            }
        }
        if (instTwo >= 4){
            deckRedraw();
        } else if (instThr >= 4){
            deckRedraw();
        } else if (instFou >= 4){
            deckRedraw();
        } else if (instFiv >= 4){
            deckRedraw();
        } else if (instSix >= 4){
            deckRedraw();
        } else if (instSev >= 4){
            deckRedraw();
        } else if (instEig >= 4){
            deckRedraw();
        } else if (instNin >= 4){
            deckRedraw();
        } else if (instTen >= 4){
            deckRedraw();
        } else if (instJac >= 4){
            deckRedraw();
        } else if (instQue >= 4){
            deckRedraw();
        } else if (instKin >= 4){
            deckRedraw();
        } else if (instAce >= 4){
            deckRedraw();
        }
    }

    public static void endRound(){
        LuckyKnave.lastBet = LuckyKnave.bet;
        LuckyKnave.lastTokens = LuckyKnave.tokens;
        //displays results of the round and rewards or penalizes by bet accordingly
        if (bustP == true){ //Player loses
            System.out.println("\nYou busted, so I'll take that bet");
            //takes tokens
            floorStains();
            if (LuckyKnave.roundInsurance == true){
                LuckyKnave.bet -= LuckyKnave.insurance;
            }
            LuckyKnave.tokens = LuckyKnave.tokens - LuckyKnave.bet;
            LuckyKnave.winInts = 0;
        } else if (bustD == true){ //Player wins
            System.out.println("\nOh dang it I busted");
            System.out.println("You win I guess");
            LuckyKnave.winInts++;
            //grants tokens
            floorStains();
            knavesKin();
            moneyMaker();
            highSteaks(LuckyKnave.steakActive);
            LuckyKnave.tokens = LuckyKnave.tokens + LuckyKnave.bet;
        } else if (push == true){ //Player ties/pushes
            //does nothing the bet will reset at the end of the conditional
            System.out.println("\nPush, you get your bet back.");
            knavesKin();
            pushMore();
        } else if (LuckyKnave.cardTotal > LuckyKnave.dealerTotal){ //Player wins
            System.out.println("\nOh so you won!");
            System.out.println("You just got lucky I'm sure");
            System.out.println("Besides 'The Box' always wins in the end!");
            LuckyKnave.winInts++;
            floorStains();
            moneyMaker();
            knavesKin();
            highSteaks(LuckyKnave.steakActive);
            LuckyKnave.tokens = LuckyKnave.tokens + LuckyKnave.bet;
        } else if ((LuckyKnave.cardTotal < LuckyKnave.dealerTotal) && boxCutter() == false){ //Player Loses
            System.out.println("\nThe Box always wins");
            System.out.println("Told you can't soften these edges!");
            floorStains();
            if (LuckyKnave.roundInsurance == true){
                LuckyKnave.bet -= LuckyKnave.insurance;
            }
            LuckyKnave.tokens = LuckyKnave.tokens - LuckyKnave.bet;
            LuckyKnave.winInts = 0;
        }
        //resets bet so it doesn't interfere with anything on chance it could
        if (LuckyKnave.tokens <= 0){
            System.out.println("\nMaybe going all in wasn't worth it");
            System.out.println("I mean it is the only way to lose ALL of your tokens");
            System.out.println("But, hey I appreciate the " + LuckyKnave.bet + " tokens");
            System.out.println("You know digital pets to feed");
            System.exit(0);
        } else if (boxCutter() == false){
            //resets variables for furture rounds and displays text before starting another round
            roundReset();
            System.out.println("\nSo would you like to play me again?");
            System.out.println("Of course it is yes, why else would you be here for a single game?");
            System.out.println("We are just gonna keep going!");
        } else if (boxCutter() == true){
            cutterRound();
            LuckyKnave.shopCounter--;
        }
        if (LuckyKnave.makeDouble > 0 && boxCutter() == false){
            LuckyKnave.tokens = LuckyKnave.tokens + LuckyKnave.makeDouble;
            LuckyKnave.makeDouble = 0;
        }
        LuckyKnave.shopCounter++;
        if(shopCounter == 5 && boxCutter() == false){
            LuckyKnave.shopCounter = 0;
            upgradeShop();
            inputBank();
        }
        bank();
    }

    public static void cutterRound(){
        roundReset();
        LuckyKnave.card1 = deal();
        LuckyKnave.card2 = deal();
        aceValueCheck();
        if (LuckyKnave.cardTotal != 21){
        while (LuckyKnave.turn == true){
            //shows your hand
            displayHand();
            if (LuckyKnave.cardTotal != 21){
                System.out.println("Hit or Stand?");
                //player chooses to hit or stand
                String option = LuckyKnave.scanner.next();
                //hit includes the stand options to make sure no other text is shown
                hit(option);
                bustCheck();
            } else {
                LuckyKnave.turn = false;
                System.out.println("You got 21?!?");
                System.out.println("The Box has to cook now!");
                LuckyKnave.TweOne = true;
            }
        }
        } else {
            //prints text if you did get Blackjack
            System.out.println("You got Blackjack! That's the name of the Game!");
            System.out.println("Watch me match that!");
            LuckyKnave.TweOne = true;
        }
        //after the player turn ends the dealer's turn begins 
        LuckyKnave.turn = false;
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(2))){
                LuckyKnave.playUpgrades.remove(i);
            }
        }
        dealerTurn();
    }

    public static void moneyMaker(){ //works
        //used to maker sure other winning multipliers don't stack too much and become overpowered way faster than anticipated
        //The multipliers being Money Maker, Blackjack, and Filling the Box
        //Money maker is a passive 1.1 boost yo your bet when you win or 10%
        boolean moneyMaker = false;
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(1)) && moneyMaker == false){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.1));
                moneyMaker = true;
            } else if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(1)) && LuckyKnave.TweOne == true && moneyMaker == false){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.4));
                moneyMaker = true;
                LuckyKnave.TweOne = false;
            } else if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(1)) && boxBoost == true && moneyMaker == false && LuckyKnave.TweOne == false){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.6));
                moneyMaker = true;
            } else if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(1)) && boxBoost == true && moneyMaker == false && LuckyKnave.TweOne == true){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 2));
                moneyMaker = true;
            }
            if (moneyMaker == false && LuckyKnave.TweOne == true){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.3));
                moneyMaker = true;
            }
            if (moneyMaker == false && LuckyKnave.TweOne == false && boxBoost == true){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.5));
                moneyMaker = true;
            }
            if (moneyMaker == false && LuckyKnave.TweOne == true && boxBoost == true){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.8));
                moneyMaker = true;
            }
        }
    }

    public static void keepItSimple(){ //works
        //if the first cards you draw are 10 or 5 you automatically get blackjack and win
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (((LuckyKnave.card1.equals("5") && LuckyKnave.card2.equals("10")) || (LuckyKnave.card1.equals("5") && LuckyKnave.card2.equals("5")) || (LuckyKnave.card1.equals("10") && LuckyKnave.card2.equals("5"))) && (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(6)))){
                LuckyKnave.TweOne = true;
                LuckyKnave.turn = false;
            }
        }
    }

    public static void makeItDouble(){ //works
        //if the first 2 cards are the same your bet is 1.15 more or 15%
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if ((LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(5))) && (LuckyKnave.card1.equals(LuckyKnave.card2))){
                LuckyKnave.makeDouble = Math.toIntExact(Math.round(LuckyKnave.tokens * 1.15));
            }
        }
    }

    public static boolean boxCutter(){ //works
        //if the player loses the round they get another atempt to win before losing their tokens
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(2))){
                return true;
            }
        }
        return false;
    }

    public static void deckPeak(){ //works
        //Lets you redraw first hand
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(3))){
                boolean validInput = false;
                boolean firCardredrawn = false;
                boolean secCardreDrawn = false;
                while (validInput == false){
                    System.out.println("Your cards are " + LuckyKnave.card1 + " and " + LuckyKnave.card2);
                    System.out.println("Card Total: " + LuckyKnave.cardTotal);
                    System.out.println("Would you like to use Deak Peak? (Y/N)");
                    String YN = LuckyKnave.scanner.next();
                    if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                        LuckyKnave.playUpgrades.remove(i);
                        if (firCardredrawn == false){
                            if (((int) LuckyKnave.peakCards.get(0).get(0) != 13 && (int) LuckyKnave.peakCards.get(0).get(0) != 12 && (int) LuckyKnave.peakCards.get(0).get(0) != 11 && (int) LuckyKnave.peakCards.get(0).get(0) != 1)){
                            LuckyKnave.cardTotal = LuckyKnave.cardTotal - (int) LuckyKnave.peakCards.get(0).get(0);
                            //Make it so all of the cards are redrawn properly
                            LuckyKnave.card1 = deal();
                            aceValueCheck();
                            validInput = true;
                            firCardredrawn = true;
                            }
                            if ((LuckyKnave.peakCards.get(0).get(1).equals("a Jack") || LuckyKnave.peakCards.get(0).get(1).equals("a Queen") || LuckyKnave.peakCards.get(0).get(1).equals("a King"))){
                                LuckyKnave.cardTotal = LuckyKnave.cardTotal - 10;
                                //Make it so all of the cards are redrawn properly
                                LuckyKnave.card1 = deal();
                                aceValueCheck();
                                validInput = true;
                            }
                        }
                        if (secCardreDrawn == false){
                            if ((LuckyKnave.peakCards.get(1).get(1).equals("a Jack") || LuckyKnave.peakCards.get(1).get(1).equals("a Queen") || LuckyKnave.peakCards.get(1).get(1).equals("a King"))){
                                LuckyKnave.cardTotal = LuckyKnave.cardTotal - 10;
                                //Make it so all of the cards are redrawn properly
                                LuckyKnave.card2 = deal();
                                aceValueCheck();
                                validInput = true;
                                secCardreDrawn = true;
                            }
                            if ((((int) LuckyKnave.peakCards.get(1).get(0) != 13 && (int) LuckyKnave.peakCards.get(1).get(0) != 12 && (int) LuckyKnave.peakCards.get(1).get(0) != 11 && (int) LuckyKnave.peakCards.get(1).get(0) != 1))){
                                LuckyKnave.cardTotal = LuckyKnave.cardTotal - (int) LuckyKnave.peakCards.get(1).get(0);
                                //Make it so all of the cards are redrawn properly
                                LuckyKnave.card2 = deal();
                                aceValueCheck();
                                validInput = true;
                                secCardreDrawn = true;
                            }
                        }
                        if (LuckyKnave.peakCards.get(0).get(1).equals("a Ace") && LuckyKnave.peakCards.get(1).get(1).equals("a Ace")){
                            LuckyKnave.cardTotal -= 12;
                            LuckyKnave.card1 = deal();
                            LuckyKnave.card2 = deal();
                            aceValueCheck();
                            validInput = true;
                            firCardredrawn = true;
                        } else {
                            if (LuckyKnave.peakCards.get(0).get(1).equals("a Ace") && firCardredrawn == false){
                                if (ace == 11){
                                    LuckyKnave.cardTotal -= 11;
                                } else {
                                    LuckyKnave.cardTotal -= 1;
                                }
                                LuckyKnave.card1 = deal();
                                aceValueCheck();
                                validInput = true;
                                firCardredrawn = true;
                            } else if (LuckyKnave.peakCards.get(1).get(1).equals("a Ace") && secCardreDrawn == false){
                                if (ace == 11){
                                    LuckyKnave.cardTotal -= 11;
                                } else {
                                    LuckyKnave.cardTotal -= 1;
                                }
                                LuckyKnave.card2 = deal();
                                aceValueCheck();
                                validInput = true;
                                secCardreDrawn = true;
                            }
                        }
                    } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                        System.out.println("Well maybe next time!");
                        validInput = true;
                    } else {
                        System.out.println("Can not understand message. Try Again");
                        validInput = false;
                    }
                }
            }
        }
    }

    public static boolean whatYourMine(String w){ //works
        //When activated and the player stands the result will always be a push
        boolean choseOpt = false;
        boolean hasWYIM = false;
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(4))){
                String opt1 = "What's yours is mine";
                String opt2 = "WYIM";
                String opt3 = "Whats yours is mine";
                String optcomp = w.toLowerCase();
                if (optcomp.contains(opt1.toLowerCase())){
                    choseOpt = true;
                } else if (optcomp.contains(opt2.toLowerCase())){
                    choseOpt = true;
                } else if (optcomp.contains(opt3.toLowerCase())){
                    choseOpt = true;
                }
                LuckyKnave.playUpgrades.remove(i);
                hasWYIM = true;
            }
        }
        if (hasWYIM == false){
            String opt1 = "What's yours is mine";
            String opt2 = "WYIM";
            String opt3 = "Whats yours is mine";
            String optcomp = w.toLowerCase();
            if (optcomp.contains(opt1.toLowerCase())){
                System.out.println("You don't have 'What's yours is mine'");
            } else if (optcomp.contains(opt2.toLowerCase())){
                System.out.println("You don't have 'What's yours is mine'");
            } else if (optcomp.contains(opt3.toLowerCase())){
                System.out.println("You don't have 'What's yours is mine'");
            }
        }
        return choseOpt;
    }

    public static boolean twoEasy(boolean remove){ //works
        //makes the next card you draw a 2 when activated
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(7))){
                if (remove == true && LuckyKnave.twoEasy == true){
                    LuckyKnave.playUpgrades.remove(i);
                    LuckyKnave.twoEasy = false;
                }
                return true;
            }
        }
        return false;
    }

    public static void insurance(){ //works
        //when actiavted for the round if you lose you only lose half you bet over what was intended
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(8))){
                LuckyKnave.playUpgrades.remove(i);
                LuckyKnave.insurance = Math.toIntExact(Math.round(LuckyKnave.bet * 0.5));
                LuckyKnave.roundInsurance = true;
            }
        }
    }

    public static void fillBox(){ //works
        //after losing 90% of your tokens in the last round you gain a money multiplier if you win the next round after the 90% loss
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(9))){
                if (lastBet >= Math.toIntExact(Math.round(LuckyKnave.lastTokens * 0.9))){
                    LuckyKnave.boxBoost = true;
                }
            }
        }
    }

    public static boolean heavyCards(){ //works
        //kings, queens, and jacks become a value of 11 and 2s and 3s value is now 0
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(10))){
                return true;
            }
        }
        return false;
    }

    public static void pushMore(){ //works
        //When the result of a round is a push you actually gain 0.5 or 50% of your bet back
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(11))){
                if (LuckyKnave.push == true){
                    LuckyKnave.tokens = LuckyKnave.tokens + Math.toIntExact(Math.round(LuckyKnave.bet * 0.5));
                }
            }
        }
    }

    public static void highSteaks(boolean activate){ //works enough (The numbers don't exactly match up but like more is more)
        //When under 30 tokens the player gets only 0.3 earns on their bet but gets a active multipler on their bet when activated under 30 tokens
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(12))){
                if (LuckyKnave.tokens <= 30){
                    LuckyKnave.steakMul = 1.0;
                    LuckyKnave.steakInts++;
                    for (int s = 0; s < steakInts; s++){
                        LuckyKnave.steakMul = LuckyKnave.steakMul + 0.02;
                        LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.lastBet * 0.3));
                    }
                    if (activate == true){
                        LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * LuckyKnave.steakMul));
                        LuckyKnave.steakActive = false;
                    }
                } else {
                    LuckyKnave.steakInts = 0;
                }
            }
        }
    }

    public static void floorStains(){ //works, may need buff
		/*Every win the player gets they get a win streak bonus
        Specifically everytime the player wins their bet is increased by 0.02 or 2%*/
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(13))){
                double Multi = 1.0;
                for (int w = 0; w < winInts; w++){
                    Multi = Multi + 0.02;
                }
                if (LuckyKnave.bustP == true){
                    LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.02));
                } else if (LuckyKnave.cardTotal < LuckyKnave.dealerTotal){
                    LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.02));
                } else {
                    LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * Multi));
                }
            }
        }
    }

    public static void knavesKin(){ //untested, should work hand wide
        //When the player gets a 2 or Jack it multiplies their winnings by 1.5x in the round, does not apply on loss and you can miss the chance to get this if you lose
		//Does work if you have Push More and you get a Push
		for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (((LuckyKnave.card1.equals("a Jack") && LuckyKnave.card2.equals("2")) || (LuckyKnave.card1.equals("2") && LuckyKnave.card2.equals("a Jack"))) && (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(14)))){
                LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.5));
            } else {
                ArrayList<String> playCards = new ArrayList<>();
                if(LuckyKnave.hitCount == 1){
                    playCards.add(LuckyKnave.card3);
                } else if (LuckyKnave.hitCount == 2){
                    playCards.add(LuckyKnave.card4);
                } else if (LuckyKnave.hitCount == 3){
                    playCards.add(LuckyKnave.card5);
                } else if (LuckyKnave.hitCount == 4){
                    playCards.add(LuckyKnave.card6);
                } else if (LuckyKnave.hitCount == 5){
                    playCards.add(LuckyKnave.card7);
                } else if (LuckyKnave.hitCount == 6){
                    playCards.add(LuckyKnave.card8);
                } else if (LuckyKnave.hitCount == 7){
                    playCards.add(LuckyKnave.card9);
                } else if (LuckyKnave.hitCount == 8){
                    playCards.add(LuckyKnave.card10);
                } else if (LuckyKnave.hitCount == 9){
                    playCards.add(LuckyKnave.card11);
                }
                boolean hasTwo = false;
                boolean hasJack = false; 
                for (int c = 0; c < playCards.size(); c++){
                    if (playCards.get(c) == "a Jack"){
                        hasJack = true;
                    } else if (playCards.get(c) == "2"){
                        hasTwo = true;
                    }
                }
                if (hasJack && hasTwo){
                    LuckyKnave.bet = Math.toIntExact(Math.round(LuckyKnave.bet * 1.2));
                }
            }
        }
    }

    public static void inputBank(){ //works
		/*Asks the player to input tokens in the bank
		the player can not input more than 20% of their current tokens per 5 rounds
		they may take out as much as they want if it is present in the bank
		players do not get the tokens in the bank if they lose all of them within the 5 rounds inbetween the 5 rounnds*/
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(0))){
                boolean validInput = false;
                int inputBankTokens = 0;
                while (validInput == false){
                    System.out.println("\nHow many tokens would you like to input? (Current tokens in bank: " + LuckyKnave.bankTokens + ")");
                    if (LuckyKnave.scanner.hasNextInt()){
                        inputBankTokens = LuckyKnave.scanner.nextInt();
                    } else {
                        String text = LuckyKnave.scanner.next();
                        text = text + text;
                    }
                    if (inputBankTokens > Math.toIntExact(Math.round(LuckyKnave.tokens * 0.2))){
                        validInput = false;
                        System.out.println("Can not put in that much, make amount lower");
                    } else if (inputBankTokens > 0 && inputBankTokens < Math.toIntExact(Math.round(LuckyKnave.tokens * 0.2))){
                        validInput = true;
                        System.out.println("Is this the amount you would like to put int the bank? (Y/N)");
                        String YN = LuckyKnave.scanner.next();
                        if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                            System.out.println("Tokens put into bank");
                            LuckyKnave.tokens = LuckyKnave.tokens - inputBankTokens;
                            LuckyKnave.bankTokens = LuckyKnave.bankTokens + inputBankTokens;
                        } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                            System.out.println("Well tell me again");
                            validInput = false;
                        } else {
                            System.out.println("Can not understand message. Try Again");
                            validInput = false;
                        }
                    }
                }
                boolean doneOutput = false;
                int outputBankTokens = 0;
                while (doneOutput == false){
                System.out.println("\nWould you like to take out any tokens? (Y/N)");
                String YN = LuckyKnave.scanner.next();
                    if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                        System.out.println("How many tokens?");
                        if (LuckyKnave.scanner.hasNextInt()){
                            outputBankTokens = LuckyKnave.scanner.nextInt();
                            if (outputBankTokens > LuckyKnave.bankTokens){
                                System.out.println("Don't kid with me, can't take out more than what is in here");
                            } else {
                                //takestokens from bank and gives to player
                                LuckyKnave.tokens = LuckyKnave.tokens + outputBankTokens;
                                LuckyKnave.bankTokens = LuckyKnave.bankTokens - outputBankTokens;
                                doneOutput = true;
                                System.out.println("Thank you for your service");
                            }
                        } else {
                            String text = LuckyKnave.scanner.next();
                            text = text + text;
                            System.out.println("Can not interpret try again");
                            doneOutput = false;
                        }
                    } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                        doneOutput = true;
                        System.out.println("Thank you for your service");
                    } else {
                        System.out.println("Can not read input, must mean you don't want any");
                        doneOutput = true;
                    }
                }
            }
        }
    }

    public static void bank(){ //works
		//Gives the player 5% intrest on what is in the bank each call
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(0))){
                LuckyKnave.bankTokens = Math.toIntExact(Math.round(LuckyKnave.bankTokens * 1.05));
            }
        }
    }

    public static int costMultiplier(int cost){
		//Increases the price on the shop items, subject to be changed
        if (LuckyKnave.tokens >= 150 && LuckyKnave.tokens < 200){
            LuckyKnave.costMul = 1.1;
        }
        if (LuckyKnave.tokens >= 200 && LuckyKnave.tokens < 250){
            LuckyKnave.costMul = 1.095;
        }
        if (LuckyKnave.tokens >= 250 && LuckyKnave.tokens < 300){
            LuckyKnave.costMul = 1.09;
        }
        if (LuckyKnave.tokens >= 300 && LuckyKnave.tokens < 350){
            LuckyKnave.costMul = 1.085;
        }
        if (LuckyKnave.tokens >= 350 && LuckyKnave.tokens < 400){
            LuckyKnave.costMul = 1.08;
        }
        if (LuckyKnave.tokens >= 400 && LuckyKnave.tokens < 450){
            LuckyKnave.costMul = 1.075;
        }
        if (LuckyKnave.tokens >= 450 && LuckyKnave.tokens < 500){
            LuckyKnave.costMul = 1.07;
        }
        if (LuckyKnave.tokens >= 500 && LuckyKnave.tokens < 550){
            LuckyKnave.costMul = 1.065;
        }
        if (LuckyKnave.tokens >= 550 && LuckyKnave.tokens < 600){
            LuckyKnave.costMul = 1.06;
        }
        if (LuckyKnave.tokens >= 600 && LuckyKnave.tokens < 650){
            LuckyKnave.costMul = 1.055;
        }
        if (LuckyKnave.tokens >= 650 && LuckyKnave.tokens < 700){
            LuckyKnave.costMul = 1.05;
        }
        if (LuckyKnave.tokens >= 700 && LuckyKnave.tokens < 750){
            LuckyKnave.costMul = 1.045;
        }
        if (LuckyKnave.tokens >= 750 && LuckyKnave.tokens < 800){
            LuckyKnave.costMul = 1.04;
        }
        if (LuckyKnave.tokens >= 800 && LuckyKnave.tokens < 850){
            LuckyKnave.costMul = 1.035;
        }
        if (LuckyKnave.tokens >= 850 && LuckyKnave.tokens < 900){
            LuckyKnave.costMul = 1.03;
        }
        if (LuckyKnave.tokens >= 900 && LuckyKnave.tokens < 950){
            LuckyKnave.costMul = 1.025;
        }
        if (LuckyKnave.tokens >= 950 && LuckyKnave.tokens < 1000){
            LuckyKnave.costMul = 1.02;
        }
        if (LuckyKnave.tokens >= 1000 && LuckyKnave.tokens < 1050){
            LuckyKnave.costMul = 1.015;
        }
        if (LuckyKnave.tokens >= 1050 && LuckyKnave.tokens < 1100){
            LuckyKnave.costMul = 1.01;
        }
        if (LuckyKnave.tokens >= 1100 && LuckyKnave.tokens < 1150){
            LuckyKnave.costMul = 1.005;
        }
        if (LuckyKnave.tokens >= 1150 && LuckyKnave.tokens < 1200){
            LuckyKnave.costMul = 1.0;
        }
        return Math.toIntExact(Math.round(cost * LuckyKnave.costMul));
    }

    public static void upgradeShop(){
		/*after 5 rounds it runs the shop
		the shop gives the player a chance to buy upgrades with their earned tokens
		it makes sure the randomly selected item from the upgrades list is not already bought
		or repeated twice over in the shop*/
        Random rand = new Random();
        boolean endShop = false;
        int item1 = rand.nextInt(14);
        int item2 = rand.nextInt(14);
        int item3 = rand.nextInt(14);
        while (endShop == false){
        System.out.println("Your Tokens: " + LuckyKnave.tokens);
        System.out.println("Shop is: ");
        System.out.println("\n1: " + LuckyKnave.upgrades.get(noShopRepeat(item1)) + " - Cost: " + costMultiplier(LuckyKnave.cost.get(noShopRepeat(item1))));
        System.out.println("2: " + LuckyKnave.upgrades.get(noShopRepeat(item2)) + " - Cost: " + costMultiplier(LuckyKnave.cost.get(noShopRepeat(item2))));
        System.out.println("3: " + LuckyKnave.upgrades.get(noShopRepeat(item3)) + " - Cost: " + costMultiplier(LuckyKnave.cost.get(noShopRepeat(item3))));
        System.out.println("4: Exit");
        System.out.println("\n To buy please enter number of item");
        String opt = LuckyKnave.scanner.next();
        if (opt.equals("1") || opt.equals("one") || opt.equals("One")){
            System.out.println("\nYou want " + LuckyKnave.upgrades.get(item1) + " for " + costMultiplier(LuckyKnave.cost.get(item1)) + "? (Y/N)");
            String YN = LuckyKnave.scanner.next();
            if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                LuckyKnave.playUpgrades.add(LuckyKnave.upgrades.get(item1));
                System.out.println("You bought " + LuckyKnave.upgrades.get(item1) + "!");
                LuckyKnave.tokens = LuckyKnave.tokens - costMultiplier(LuckyKnave.cost.get(item1));
                endShop = true;
            } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                System.out.println("Choose a item to buy, if not exit");
            } else {
                System.out.println("Can not understand. Sending back to shop");
            }
        } else if (opt.equals("2") || opt.equals("Two") || opt.equals("two")){
            System.out.println("\nYou want " + LuckyKnave.upgrades.get(item2) + " for " + costMultiplier(LuckyKnave.cost.get(item2)) + "? (Y/N)");
            String YN = LuckyKnave.scanner.next();
            if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                LuckyKnave.playUpgrades.add(LuckyKnave.upgrades.get(item2));
                System.out.println("You bought " + LuckyKnave.upgrades.get(item2) + "!");
                LuckyKnave.tokens = LuckyKnave.tokens - costMultiplier(LuckyKnave.cost.get(item2));
                endShop = true;
            } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                System.out.println("Choose a item to buy, if not exit");
            } else {
                System.out.println("Can not understand. Sending back to shop");
            }
        } else if (opt.equals("3") || opt.equals("Three") || opt.equals("three")){
            System.out.println("You want " + LuckyKnave.upgrades.get(item3) + " for " + costMultiplier(LuckyKnave.cost.get(item3)) + "? (Y/N)");
            String YN = LuckyKnave.scanner.next();
            if (YN.contains("Yes") || YN.equals("yes") || YN.equals("y") || YN.equals("Y")){
                LuckyKnave.playUpgrades.add(LuckyKnave.upgrades.get(item3));
                System.out.println("\nYou bought " + LuckyKnave.upgrades.get(item3) + "!");
                LuckyKnave.tokens = LuckyKnave.tokens - costMultiplier(LuckyKnave.cost.get(item3));
                endShop = true;
            } else if (YN.equals("No") || YN.equals("no") || YN.equals("n") || YN.equals("N")){
                System.out.println("Choose a item to buy, if not exit");
            } else {
                System.out.println("Can not understand. Sending back to shop");
            }
        } else if (opt.equals("4") || opt.equals("Four") || opt.equals("Four")){
            endShop = true;
        } else {
            System.out.println("We can not understand your input please select a option in the shop");
        }
        }
        System.out.println("Alright back to the rounds!");
    }

    public static int noShopRepeat(int item){
		//used to make sure items in the shop don't repeat
        for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
            if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(item))){
                for (int p = 0; p < LuckyKnave.perishables.size(); p++){
                    if (!LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(LuckyKnave.perishables.get(p)))){
                        item++;
                        if (item > 14){
                            item = 1;
                        }
                    }
                } 
            } 
        }
        return item;
    }

    public static void roundReset(){
        //resets all variables in code
        LuckyKnave.bet = 0;
        LuckyKnave.turn = true;
        LuckyKnave.push = false;
        LuckyKnave.bustD = false; 
        LuckyKnave.bustP = false;
        LuckyKnave.cardTotal = 0;
        LuckyKnave.dealerTotal = 0;
        LuckyKnave.hitCount = 0;
        LuckyKnave.dealHitCount = 0;
        LuckyKnave.card1 = "";
        LuckyKnave.card2 = "";
        LuckyKnave.card3 = "";
        LuckyKnave.card4 = "";
        LuckyKnave.card5 = "";
        LuckyKnave.card6 = "";
        LuckyKnave.card7 = "";
        LuckyKnave.card8 = "";
        LuckyKnave.card9 = "";
        LuckyKnave.card10 = "";
        LuckyKnave.card11 = "";
        LuckyKnave.dealCard1 = "";
        LuckyKnave.dealCard2 = "";
        LuckyKnave.dealCard3 = "";
        LuckyKnave.dealCard4 = "";
        LuckyKnave.dealCard5 = "";
        LuckyKnave.dealCard6 = "";
        LuckyKnave.dealCard7 = "";
        LuckyKnave.ace = 1;
        LuckyKnave.setAce = false;
        LuckyKnave.TweOne = false; 
        LuckyKnave.peakCards.clear();
    }

    //Could make split check, but not nessecary, splits are not part of game

    public static void credits(){
		//Credits to the people who contributed to this as well the like 3 months I spent making this project
        System.out.println("\n----The Luckiest Knave----");
        System.out.println("|  A game made by Owen G  |");
        System.out.println("| Idea assistence: Claude |");
        System.out.println("|Code assistence: Gitpilot|");
        System.out.println("|Playtesting: Richard+Lea |");
        System.out.println("|Gluon SceneBuilder Guides|");
        System.out.println("|Luke+Zach for optization |");
        System.out.println("|Michael for app shoutout |");
        System.out.println("|Me for making this: Owen |");
        System.out.println("| We support those at war |");
        System.out.println("---------Thank you---------");
    }

    public static void story(){
		//More text that is printed, tells some of the plot of the story (Story is not fully devolped)
        System.out.println("\nHmm? What's this? You don't know why your here?");
        System.out.println("Or why you ran this?");
        System.out.println("Seriously you people keep forgetting day by day");
        System.out.println("Well I don't know, how much you have on you?");
        System.out.println("Nothing? So you have no money, good thing I hooked you up with some 'Tokens'");
        System.out.println("Oh and your unemployed. This is making more since now");
        System.out.println("You need money can't get it and got to gamble for it");
        System.out.println("And you need a lot?");
        System.out.println("Thought so. Yeah your going to need a LOT more than 20");
        System.out.println("And no I am not giving you more I've been told not to risk it for Knaves");
        System.out.println("Also you gotta visit that shop over there every 5 rounds. Just visit, and get normal things you Knave");
        System.out.println("People keep telling me Knaves are the only winners here");
        System.out.println("I doubt so");
        System.out.println("Besides Knaves just get lucky");
        System.out.println("The guy who won it all after all just has to be");
        System.out.println("The Luckiest Knave");
        System.out.println("Now...");
    }

    public static void main(String[] args){
        assignUpgrades();
        //prints text at running of code
        System.out.println("Would you like to play a Game of Blackjack?");
        System.out.println("Well you already knew this when you ran the script");
        System.out.println("Anyways we have 'Tokens' round here, I'll start you off with 20");
        System.out.println("So what is your first bet? And we don't take no jibberjabber about nothing but the number");
        System.out.println("Oh but I forgot... You may not know how to play, then again you might be a seasoned player at this...");
        System.out.println("How about you just tell me can you play? (Y/N)");
        //scans for a N or n to determine whether to explain how to play blackjack or not
        String YN = LuckyKnave.scanner.next();
        if (YN.equals("N") || YN.equals("n")){
            //explanation of blackjack with personality
            System.out.println("Oh so you don't know! Let me hook you up with the knowledge");
            System.out.println("So before anything happens you place a bet");
            System.out.println("You can go safe or risky with your bets low or high");
            System.out.println("As a new person I would play safe, but as I am the house you should go risky, bet higher");
            System.out.println("Consider it a tip");
            System.out.println("Anyways the game actually starts after placing your bet");
            System.out.println("You are dealt 2 cards by me");
            System.out.println("Your cards have number value that being the number on a card");
            System.out.println("But if it is a face card, you know a Jack, Queen, or King it counts as 10 flat");
            System.out.println("But the Ace is special it can be 11 or 1, luckily I make your life easy and just make it whatever is convient for you on the spot");
            System.out.println("Oh yeah I also total up your card count anyways so no real need to know the values, but it is in my contract!");
            System.out.println("So once we have are cards we are trying to get their total value to 21");
            System.out.println("21 is a lucky number you know, also a nice age to be at, very crisp");
            System.out.println("And consider yourself lucky if you get a Ace and a 10 or you-know-what-not");
            System.out.println("Everytime you win against the house who is also playing to get as close to 21 you double your bet and get your tokens back");
            System.out.println("If you do get Blackjack you know or 21 you also get another third more than your original bet before we double it");
            System.out.println("Also don't go over 21, you lose if you do, we call that a 'bust'");
            System.out.println("And don't tie with me either, called a 'push' you get your money back");
            System.out.println("Almost forgot but you have 2 actions you can do when it is your turn");
            System.out.println("You can Hit or stand");
            System.out.println("So you can hit something or stand up out of your chair");
            System.out.println("Haha you fell for it! Yeah sye nah cuh, hitting gives you another random card, and standing says your good with the card value you have");
            System.out.println("Also 1 last thing before I absolutly luck you out is that while most people call me the house I am 'The Box' round these parts");
            System.out.println("Can't round out my corners");
            System.out.println("Anyways good luck!");
            System.out.println("\nAlso side note. No splits if you want to know");
            story();
	        startRound();
        } else {
            //if you said yes or anything but no to the question above it starts the round
            story();
            startRound();
        }
        while (LuckyKnave.tokens > 0){
            //while you can still bet and you have tokens it starts the round again
            startRound();
        }
    }

    public static void addUpgrades(){
		//Dev Tool that adds a upgrade when requested by it's integer value in upgrades
        System.out.println("What upgrade will you like to add/remove? (Int only of upgrade, 0-14 to add that upgrade, other num to get rid of one, -1 = skip this, -2 to list active ones)");
        int upg = LuckyKnave.scanner.nextInt();
        if (upg < 15 && upg > -1){
        LuckyKnave.playUpgrades.add(LuckyKnave.upgrades.get(upg));
        System.out.println("Added " + LuckyKnave.upgrades.get(upg));
        } else if (upg == -1){
            System.out.println("Continuing program as is");
        } else if (upg == -2){
            System.out.println("Current Upgrades: \n" + LuckyKnave.playUpgrades);
        } else {
            System.out.println("Remove what upgrade? (Int Only of upgrade)");
            int upg2 = LuckyKnave.scanner.nextInt();
            if (upg2 < 15 && upg2 > -1){
                for (int i = 0; i < LuckyKnave.playUpgrades.size(); i++){
                    if (LuckyKnave.playUpgrades.get(i).equals(LuckyKnave.upgrades.get(upg2))){
                        LuckyKnave.playUpgrades.remove(i);
                        System.out.println("Sucessfully removed upgrade");
                    } else {
                        System.out.println("Requested upgrade not found yet and/or not removed");
                    }
                }
            } else {
                System.out.println("Upgrade does not exist going back to program");
            }
        }
    }
}