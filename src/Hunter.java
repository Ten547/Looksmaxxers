/**
 * Hunter Class<br /><br />
 * This class represents the treasure hunter character (the player) in the Treasure Hunt game.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
import java.util.ArrayList;
public class Hunter {
    //instance variables
    private String hunterName;
    private String[] kit;
    private int gold;
    private ArrayList<String> inventory;
    private String[] treasure = new String[3];
    private String treasures;
    private String[] kit2;

    /**
     * The base constructor of a Hunter assigns the name to the hunter and an empty kit.
     *
     * @param hunterName The hunter's name.
     * @param startingGold The gold the hunter starts with.
     */
    public Hunter(String hunterName, int startingGold) {

        this.hunterName = hunterName;
        kit = new String[7]; // only 7 possible items can be stored in kit
        gold = startingGold;
        this.inventory = new ArrayList<>();
        if (startingGold==100) {
            kit = new String[]{"water", "rope", "machete", "horse", "boat", "boots", "shovel"};
        }
        TreasureHunter addSword = new TreasureHunter();
        if (addSword.isSamuraiMode()) {
            kit2 = new String [8];
            kit2 = new String[]{"water", "rope", "machete", "horse", "boat", "boots", "shovel, sword"};
        }
    }

    public String getTreasure() {
        String end = "";
        int count = 0;
        for (int x  = 0; x < 3; x++) {
            if (treasure[x]!=null) {
                end += " a " + treasure[x] ;
                count++;
            }
        }
        if (count==0) {
            return "You found no treasure";
        } else if (count==3) {
            return "You found a " + treasures + "You have found all the treasures COGRATTTS!!!" ;

        }
        return "treasures found: " + end;
    }

    //Accessors
    public String getHunterName() {
        return hunterName;
    }

    /**
     * Updates the amount of gold the hunter has.
     *
     * @param modifier Amount to modify gold by.
     */
    public void changeGold(int modifier) {
        gold += modifier;
        if (gold < 0) {
            TreasureHunter game = new TreasureHunter();
            game.endGame();
        }
    }

    public int getGold () {
        return gold;
    }


    /**
     * Buys an item from a shop.
     *
     * @param item The item the hunter is buying.
     * @param costOfItem The cost of the item.
     * @return true if the item is successfully bought.
     */
    public boolean buyItem(String item, int costOfItem) {
        if (costOfItem == 0 || gold < costOfItem || hasItemInKit(item)) {
            return false;
        }
        gold -= costOfItem;
        addItem(item);
        return true;
    }

    /**
     * The Hunter is selling an item to a shop for gold.<p>
     * This method checks to make sure that the seller has the item and that the seller is getting more than 0 gold.
     *
     * @param item The item being sold.
     * @param buyBackPrice the amount of gold earned from selling the item
     * @return true if the item was successfully sold.
     */
    public boolean sellItem(String item, int buyBackPrice) {
        if (buyBackPrice <= 0 || !hasItemInKit(item)) {
            return false;
        }
        gold += buyBackPrice;
        removeItemFromKit(item);
        return true;
    }

    /**
     * Removes an item from the kit by setting the index of the item to null.
     *
     * @param item The item to be removed.
     */
    public void removeItemFromKit(String item) {
        int itmIdx = findItemInKit(item);

        // if item is found
        if (itmIdx >= 0) {
            kit[itmIdx] = null;
        }
    }

    /**
     * Checks to make sure that the item is not already in the kit.
     * If not, it assigns the item to an index in the kit with a null value ("empty" position).
     *
     * @param item The item to be added to the kit.
     * @return true if the item is not in the kit and has been added.
     */
    public boolean addItem(String item) {
        if (!hasItemInKit(item)) {
            int idx = emptyPositionInKit();
            kit[idx] = item;
            return true;
        }
        return false;
    }

    /**
     * Checks if the kit Array has the specified item.
     *
     * @param item The search item
     * @return true if the item is found.
     */
    public  boolean hasItemInKit(String item) {
        for (String tmpItem : kit) {
            if (item.equals(tmpItem)) {
                // early return
                return true;
            }
        }
        return false;
    }

     /**
     * Returns a printable representation of the inventory, which
     * is a list of the items in kit, with a space between each item.
     *
     * @return The printable String representation of the inventory.
     */
     public String getInventory() {
         String printableKit = "";
         String space = " ";
         for (String item : kit) {
             if (item != null) {
                 printableKit += item + space;
             }
         }
         return Colors.PURPLE + printableKit;
     }
    /**
     * @return A string representation of the hunter.
     */
    public String infoString() {
        String str = hunterName + " has " +  gold + Colors.YELLOW + " gold" +  Colors.RESET;
        if (!kitIsEmpty()) {
            str += " and " + Colors.PURPLE + getInventory() + Colors.RESET ;
        }
        return str;
    }

    /**
     * Searches kit Array for the index of the specified value.
     *
     * @param item String to look for.
     * @return The index of the item, or -1 if not found.
     */
    private int findItemInKit(String item) {
        for (int i = 0; i < kit.length; i++) {
            String tmpItem = kit[i];

            if (item.equals(tmpItem)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if the kit is empty - meaning all elements are null.
     *
     * @return true if kit is completely empty.
     */
    private boolean kitIsEmpty() {
        for (String string : kit) {
            if (string != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the first index where there is a null value.
     *
     * @return index of empty index, or -1 if not found.
     */
    private int emptyPositionInKit() {
        for (int i = 0; i < kit.length; i++) {
            if (kit[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void huntForTreasure (String y) {
        TreasureHunter a = new TreasureHunter();
        String current = y;

        String one = treasure[0];
        String two = treasure[1];
        String three = treasure[2];
        if (one==(null)) {
            one = "";
        }
        if (two==(null)) {
            two = "";
        }
        if (three==(null)) {
            three = "";
        }
        if (current.equals("dust")) {
            System.out.println("You found dust womp womp");
        } else {
            if (current.equals("gem")) {
                if (!one.equals("gem") && !two.equals("gem") && !three.equals("gem")) {
                    int count = 0;
                    for (int x = 0; x < treasure.length; x++) {
                        if (treasure[x] != "null" && treasure[x] != "trophy" && treasure[x] != "crown" && count == 0) {
                            treasure[x] = "gem";
                            count++;
                        }
                    }

                    System.out.println("You found a gem!");
                } else {
                    System.out.println("You already have a gem womp womp!");
                }
            }
            if (current.equals("trophy")) {
                if (!one.equals("trophy") && !two.equals("trophy") && !three.equals("trophy")) {
                    int count = 0;
                    for (int x = 0; x < treasure.length; x++) {
                        if (treasure[x] != "null" && treasure[x] != "crown" && treasure[x] != "gem" && count == 0) {
                            treasure[x] = "trophy";
                            count++;
                        }
                    }

                    System.out.println("You found a trophy!");
                } else {
                    System.out.println("You already have a trophy womp womp!");
                }

            }
            if (current.equals("crown")) {
                if (!one.equals("crown") && !two.equals("crown") && !three.equals("crown")) {
                    int count = 0;
                    for (int x = 0; x < treasure.length; x++) {
                        if (treasure[x] != "null" && treasure[x] != "trophy" && treasure[x] != "gem" && count == 0) {
                            treasure[x] = "crown";
                            count++;
                        }
                    }
                    System.out.println("You found a crown");
                } else {
                    System.out.println("You already have a crown and you found it again womp womp!");
                }
            }
        }
    }

    }
