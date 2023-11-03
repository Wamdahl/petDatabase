import java.util.*;
public class mainPet {
    static Pet[] pets = new Pet[100];
    static int petCount = 0;
    static int rowCount = 0;
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            // rowCount restarts at 0 for each iteration and gets incremented upon printing a row
            // this way it will display the correct number of rows printed when searching for pets
            rowCount = 0;
            // get a choice from the menu
            int choice = getUserChoice();

            if (choice == 1){
                showAllPets();
            }
            if (choice == 2){
                addPets();
            }
            if (choice == 3){
                //updatePet();
            }
            if (choice == 4){
                //removePet();
            }
            if(choice == 5){
                //searchPetsByName();
            }
            if(choice == 6){
                //searchPetsByAge();
            }
            if(choice == 7){
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    // addPets method
    public static void addPets(){
        while(true){
            System.out.print("add pet (name, age): ");
            String petString = s.nextLine();
            // check to see if the user is done
            if (petString.equals("done")){
                System.out.println();
                break;
            }
            // otherwise split the line into a string name and a string age
            String[] petNameAndAge = petString.split(" ");
            String petName = petNameAndAge[0];
            int petAge = Integer.parseInt(petNameAndAge[1]);
            // create a default pet to set the name and age, then assign the tempPet to the proper index in pets[]
            Pet tempPet = new Pet();
            tempPet.setName(petName);
            tempPet.setAge(petAge);
            pets[petCount] = new Pet(tempPet.getName(), tempPet.getAge());
            petCount++;
        }
    }

    // getUserChoice method
    public static int getUserChoice(){
        // print the menu 
        System.out.println("What would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit program");	
        System.out.print("Your choice: ");
        // return choice
        int choice = s.nextInt();
        s.nextLine();
        return choice;
    }

    // showAllPets method
    public static void showAllPets(){
        int i = 0;
        printTableHeader();
        // for each pet
        while (i < petCount){
            printTableRow(i, pets[i].getName(), pets[i].getAge());
            i++;
        }
        printTableFooter();
    }

    // printTableHeader method
    public static void printTableHeader(){
        System.out.print("+-------------------------+");
        System.out.println();
        System.out.print("| ");
        System.out.printf("%-3s", "ID");
        System.out.print(" | ");
        System.out.printf("%-10s", "NAME");
        System.out.print(" | ");
        System.out.printf("%-4s", "AGE");
        System.out.print(" |");
        System.out.println();
        System.out.print("+-------------------------+");
        System.out.println();
    }

    // printTableFooter method
    public static void printTableFooter(){
        System.out.print("+-------------------------+");
        System.out.println();
        System.out.println(rowCount + " Rows in set.");
        System.out.println();
    }

    // printTableRow method
    public static void printTableRow(int id, String name, int age){
        System.out.print("| ");
        System.out.printf("%3d", id);
        System.out.print(" | ");
        System.out.printf("%-10s", name);
        System.out.print(" | ");
        System.out.printf("%4d", age);
        System.out.print(" |");
        System.out.println();
        rowCount++;
    }
}