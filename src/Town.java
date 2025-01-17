/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all the things a Hunter can do in town.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class Town {
    // instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private String printMessage;
    private boolean toughTown;
    private boolean easyTown;
    private boolean scaredTown;

    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     *
     * @param shop The town's shoppe.
     * @param toughness The surrounding terrain.
     */
    public Town(Shop shop, double toughness) {
        this.shop = shop;
        this.terrain = getNewTerrain();

        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        hunter = null;
        printMessage = "";

        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
        easyTown = (Math.random() < toughness);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public String getLatestNews() {
        return printMessage;
    }

    /**
     * Assigns an object to the Hunter in town.
     *
     * @param hunter The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter) {

        String mode = TreasureHunter.mode();
        System.out.println(mode);
        if (mode.equals("e")) {
            easyTown = true;
        } if (mode.equals("h")) {
            toughTown = true;
        }
        if (mode.equals("s")) {
            scaredTown= true;

        }
        this.hunter = hunter;
        printMessage = "Welcome to town, " + hunter.getHunterName() + ".";
        if (toughTown) {
            printMessage += "\nIt's pretty rough around here, so watch yourself.";
        } else {
            printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
        }
    }

    /**
     * Handles the action of the Hunter leaving the town.
     *
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown() {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);
        if (canLeaveTown) {
            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + Colors.CYAN + terrain.getTerrainName() +  Colors.RESET + ".";
            if (!easyTown) {
                if (checkItemBreak()) {
                    hunter.removeItemFromKit(item);
                    if (item.equals("water"))
                        System.out.println("\\nUnfortunately, your " + item + " ran out.");
                } else if (item.equals("rope")) {
                    System.out.println("\nUnfortunately, your " + item + " ripped apart.");
                } else if (item.equals("machete")) {
                    System.out.println("\nUnfortunately, your " + item + " was shattered.");
                } else if (item.equals("horse")) {
                    System.out.println("\nUnfortunately, your " + item + " is exhausted.");
                } else if (item.equals("boat")) {
                    System.out.println("\nUnfortunately, your " + item + " sank.");
                } else if (item.equals("boots")) {
                    System.out.println("\nUnfortunately, your " + item + " wore down.");
                } else if (item.equals("shovel")) {
                    System.out.println("\nUnfortunately, your " + item + " was used up.");
                }
            }
            return true;
        }

        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    /**
     * Handles calling the enter method on shop whenever the user wants to access the shop.
     *
     * @param choice If the user wants to buy or sell items at the shop.
     */
    public void enterShop(String choice) {
        printMessage = shop.enter(hunter, choice);
    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble() {
        double noTroubleChance;
        if (toughTown) {
            noTroubleChance = 0.66;
        } else if (easyTown) {
            noTroubleChance = .1;
        }
        else {
            noTroubleChance = 0.33;
        }
        if (Math.random() > noTroubleChance) {
            printMessage = "You couldn't find any trouble";
        } else {
            printMessage = Colors.RED + "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n" +  Colors.RESET;
            int goldDiff = (int) (Math.random() * 10) + 1;
            if (hunter.hasItemInKit("sword")) {
                printMessage += "the brawler, seeing your sword, realizes he picked a losing fight and gives you his gold";
                printMessage += "\nYou won the brawl and receive " + goldDiff + Colors.YELLOW + " gold." +  Colors.RESET;
                hunter.changeGold(goldDiff);
            }
             else if (Math.random() > noTroubleChance) {
                printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                printMessage += "\nYou won the brawl and receive " + goldDiff + Colors.YELLOW + " gold." +  Colors.RESET;
                hunter.changeGold(goldDiff);
            } else {
                printMessage += "That'll teach you to go lookin' fer trouble in MY town! Now pay up!";
                printMessage += "\nYou lost the brawl and pay " + goldDiff + " gold.";
                hunter.changeGold(-goldDiff);
            }
        }
    }

    public String infoString() {
        return "This nice little town is surrounded by " + Colors.CYAN + terrain.getTerrainName() +  Colors.RESET + ".";
    }

    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    private Terrain getNewTerrain() {
        double rnd = (int) (Math.random()*6)+1;
        if (rnd == 1) {
            return new Terrain("Mountains", "Rope");
        } else if (rnd < 2) {
            return new Terrain("Ocean", "Boat");
        } else if (rnd < 3) {
            return new Terrain("Plains", "Horse");
        } else if (rnd < 4) {
            return new Terrain("Desert", "Water");
        } else if (rnd < 5){
            return new Terrain("Jungle", "Machete");
        } else {
            return new Terrain("Marsh","Boots");
        }
    }

    /**
     * Determines whether a used item has broken.
     *
     * @return true if the item broke.
     */
    private boolean checkItemBreak() {
        System.out.println(easyTown);
        if (easyTown) {
        return false;
        } else {
            double rand = Math.random();
            return (rand < 0.5);
        }
    }
    public double checkSuccess() {
        double checkSuccess = Math.random();
        return checkSuccess;
    }

    public void digForGold () {

        int goldFromDigging = (int) (Math.random() * 20) + 1;
        if (hunter.hasItemInKit("shovel")) {

            if (checkSuccess() > 0.5) {
                hunter.changeGold(goldFromDigging);
                System.out.println("You dug up, and got " + goldFromDigging + " gold.");

            }
            else if (checkSuccess() == 0.5) {
                hunter.changeGold(goldFromDigging);
                System.out.println("You dug up, and got " + goldFromDigging + " gold.");

            }
            else if (checkSuccess() < 0.5) {
                System.out.println("You dug for hours, and found... dirt.");

            }
        } else {
            System.out.println("you need a shovel");
        }
    }

}