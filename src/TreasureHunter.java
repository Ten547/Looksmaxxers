import javax.swing.text.DefaultEditorKit;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is responsible for controlling the Treasure Hunter game.<p>
 * It handles the display of the menu and the processing of the player's choices.<p>
 * It handles all the display based on the messages it receives from the Town object. <p>
 *
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class TreasureHunter {
    // static variables
    private static final Scanner SCANNER = new Scanner(System.in);

    // instance variables
    private Town currentTown;
    private Hunter hunter;
    private static boolean hardMode = false;
    private int startingGold;
    private static boolean testMode = false;
    private String name;
    private static boolean easyMode = false;
    private int count = 0;
    private int treasurecount = 0;
    private String currentTreasure;
    private int digCount = 0;
    private static boolean samuraiMode = false;
    /**
     * Constructs the Treasure Hunter game.
     */
    public TreasureHunter() {
        // these will be initialized in the play method
        currentTown = null;
        hunter = null;
        startingGold = 0;
        name = null;
        samuraiMode = false;
    }

    /**
     * Starts the game; this is the only public method
     */
    public void play() {
        welcomePlayer();
        enterTown();
        showMenu();
    }
    public static String mode() {

        if (easyMode) {
            return "e";
        }
        else if (testMode) {
            return "t";
        }
        else if (hardMode) {
            return "h";
        } else if (samuraiMode) {
            return "s";
        }else {
            return "n";
        }

    }
    /**
     * Creates a hunter object at the beginning of the game and populates the class member variable with it.
     */
    public void welcomePlayer() {
        System.out.println("Welcome to TREASURE HUNTER!");
        System.out.println("Going hunting for the big treasure, eh?");
        System.out.print("What's your name, Hunter? ");
        name = SCANNER.nextLine().toLowerCase();

        // set hunter instance variable


        System.out.print("Hard mode? (e/n/h/test/???): ");
        String hard = SCANNER.nextLine().toLowerCase();
        if (hard.equals("h")) {
            hardMode = true;
            hunter = new Hunter(name, 20);
        }
        if (hard.equals("test")) {
            testMode = true;
            hunter = new Hunter(name, 100);
        }
        if (hard.equals("e")) {

            hunter = new Hunter(name, 40);
            easyMode = true;
        }
        if (hard.equals("s")) {
            samuraiMode = true;
            hunter = new Hunter(name, 1);
        }

    }

    public boolean isSamuraiMode () {
        return samuraiMode;
    }

    /**
     * Creates a new town and adds the Hunter to it.
     */
    private void enterTown() {
        count++;
        int rand = (int) (Math.random()*4)+1;
        if (rand==1) {
            currentTreasure = "crown";
        } else if (rand==2) {
            currentTreasure= "trophy";
        } else if (rand==3) {
            currentTreasure = "gem";
        } else {
            currentTreasure = "dust";
        }
        treasurecount = count-1;
        digCount = count-1;
        double markdown = 0.25;
        double toughness = 0.4;
        if (hardMode) {
            // in hard mode, you get less money back when you sell items
            markdown = 0.5;

            // and the town is "tougher"
            toughness = 0.75;
        }
         else if (easyMode) {
            markdown = 1;
            toughness = .25;
        }

        // note that we don't need to access the Shop object
        // outside of this method, so it isn't necessary to store it as an instance
        // variable; we can leave it as a local variable
        Shop shop = new Shop(markdown);

        // creating the new Town -- which we need to store as an instance
        // variable in this class, since we need to access the Town
        // object in other methods of this class
        currentTown = new Town(shop, toughness);

        // calling the hunterArrives method, which takes the Hunter
        // as a parameter; note this also could have been done in the
        // constructor for Town, but this illustrates another way to associate
        // an object with an object of a different class
        currentTown.hunterArrives(hunter);
    }

    /**
     * Displays the menu and receives the choice from the user.<p>
     * The choice is sent to the processChoice() method for parsing.<p>
     * This method will loop until the user chooses to exit.
     */
    private void showMenu() {
        String choice = "";
        while (!choice.equals("x")) {
            System.out.println();
            System.out.println(currentTown.getLatestNews());
            System.out.println("***");
            System.out.println(hunter.infoString());
            System.out.println(currentTown.infoString());
            System.out.println((hunter.getTreasure()));
            if (hunter.getTreasure().equals("You have found all the treasures CONGRATTTS!!!")) {
                System.exit(0);
            }
            System.out.println("(H)unt for treasure.");
            System.out.println("(B)uy something at the shop.");
            System.out.println("(S)ell something at the shop.");
            System.out.println("(E)xplore surrounding terrain.");
            System.out.println("(M)ove on to a different town.");
            System.out.println("(L)ook for trouble!");
            System.out.println("(D)ig for gold.");
            System.out.println("Give up the hunt and e(X)it.");
            System.out.println();
            System.out.print("What's your next move? ");
            choice = SCANNER.nextLine().toLowerCase();
            processChoice(choice);
        }
    }

    /**
     * Takes the choice received from the menu and calls the appropriate method to carry out the instructions.
     * @param choice The action to process.
     */
    private void processChoice(String choice) {
        if (choice.equals("b") || choice.equals("s")) {
            currentTown.enterShop(choice);
        } else if (choice.equals("e")) {
            System.out.println(currentTown.getTerrain().infoString());
        } else if (choice.equals("m")) {
            if (currentTown.leaveTown()) {
                // This town is going away so print its news ahead of time.
                System.out.println(currentTown.getLatestNews());
                enterTown();
            }
        } else if (choice.equals("l")) {
            currentTown.lookForTrouble();
        } else if (choice.equals("x")) {
            System.out.println("Fare thee well, " + hunter.getHunterName() + "!");
        } else if (choice.equals("d")) {
            if (digCount<count) {
                currentTown.digForGold();
                digCount++;
            } else {
                System.out.println("You have already dug here go somewhere else!");
            }
        } else if (choice.equals("h")){
            if (treasurecount<count) {
                hunter.huntForTreasure(currentTreasure);
                treasurecount++;
            } else {
                System.out.println("you already searched for treasure here");
            }
        }
        else {
            System.out.println("Yikes! That's an invalid option! Try again.");
        }
    }
    public void endGame () {
        System.out.println("Huh? You don't have enough money to pay us, stranger? You're dead meat, kid.");
        System.out.println();
        System.out.println("Sorry, game over!");
        System.exit(0);

    }

}