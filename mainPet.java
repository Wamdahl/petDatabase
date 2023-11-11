import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;
public class mainPet {
    static Pet[] pets = new Pet[100];
    static int capacity = 5;
    static int petCount = 0;
    static int rowCount = 0;
    static String filename = "pets.txt";
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        loadDatabase();
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
                removePet();
            }
            if (choice == 4){
                searchPetsByName();
            }
            if(choice == 5){
                searchPetsByAge();
            }
            if(choice == 6){
                saveDatabase();
                System.out.println("Goodbye!");
                break;
            }
            //if(choice == 7){
                
            //}
        }
    }

    // parseArgument method
    public static String[] parseArgument(String line) throws InvalidArgumentException {
        // split the line into a string name and a string age
        String[] petNameAndAge = line.split(" ");

        // if the line isnt valid
        if (petNameAndAge.length != 2){
            throw new InvalidArgumentException();
        }
        
        return petNameAndAge;
    }

    // addPet method to load pets from file
    public static void addPet(String name, int age) throws FullDatabaseException, InvalidAgeException{
        // throw exceptions that can happen when adding a pet
        if(petCount == capacity){
            throw new FullDatabaseException();
        }
        if(age < 1 || age > 20){
            throw new InvalidAgeException();
        }
        // create a default pet to set the name and age, then assign the tempPet to the proper index in pets[]
        Pet tempPet = new Pet();
        tempPet.setName(name);
        tempPet.setAge(age);
        pets[petCount] = new Pet(tempPet.getName(), tempPet.getAge());
        petCount++;
    }

    // loadDatabase method 
    public static void loadDatabase() throws Exception {
        // open the file to read
        FileInputStream fis = new FileInputStream(filename);
        Scanner read = new Scanner(fis);

        // as long as the file isnt empty
        if(fis != null){
            // while there is still a line
            while(read.hasNextLine()){
                String line = read.nextLine();

                String[] petNameAndAge = line.split(" ");
                String petName = petNameAndAge[0];
                int petAge = Integer.parseInt(petNameAndAge[1]);

                addPet(petName, petAge);
            }
            read.close();
        }
    }

    // saveDatabase method
    public static void saveDatabase() throws Exception{
        // open the file, this will override pets.txt each time it is saved
        PrintWriter w = new PrintWriter(filename);

        // print each pet
        int h = 0;
        while (h < petCount){
            w.println(pets[h].getName() +" "+ pets[h].getAge());
            h++;
        }
        w.close();
    }
    
    // removePet method
    public static void removePet() throws InvalidIdException{
        showAllPets();
        System.out.print("Enter the pet ID to remove: ");
        String removeString = s.nextLine();
        
        // try to remove a pet
        try{
            // try to parse removeString to an int, if it fails parseInt will throw NumberFormatException that i will make look like InvalidIdException
            int remove = Integer.parseInt(removeString);
            if(remove < 0 || remove >= petCount){
                throw new InvalidIdException();
            }
            // if exception is not thrown continue
            // my idea here is to assign each object after the index they want to remove to the index number before it
            int indexStart = remove;
            // while the index they want to remove is less than the petCount - 1 (so the program doesnt try to reassign a pet that doesnt exist when the user wants to remove the pet with the highest id), re-assign the object with the index right after the one they want to remove and so on
            while (indexStart < petCount - 1){
                pets[indexStart] = new Pet(pets[indexStart + 1].getName(), pets[indexStart + 1].getAge()); 
                indexStart++;
            }
            // de-increment the petCount because after this loop they have removed a pet
            petCount--;
            System.out.println();
        }
        // catch InvalidIdException and NumberFormatException if remove is not valid
        catch(InvalidIdException iie){
            System.out.println("InvalidIdException: ID " + removeString + " does not exist.");
        }
        catch(NumberFormatException nfe){
            System.out.println("InvalidIdException: ID " + removeString + " does not exist.");
        }
    }
    /* not needed for this assignment
    // updatePet method
    public static void updatePet(){
        showAllPets();
        System.out.print("Enter the pet ID you wish to update: ");
        int petId = s.nextInt();
        s.nextLine();
        System.out.print("Enter the new name and age: ");
        String petString = s.nextLine();
        // very similar idea to adding pets
        String[] petNameAndAge = petString.split(" ");
        String petName = petNameAndAge[0];
        int petAge = Integer.parseInt(petNameAndAge[1]);
        Pet tempPet = new Pet();
        tempPet.setName(petName);
        tempPet.setAge(petAge);
        // print the object before its updated
        System.out.print(pets[petId].getName() + " " + pets[petId].getAge() + " changed to ");
        // update the pet the user wants with the new name and age they entered
        pets[petId] = new Pet(tempPet.getName(), tempPet.getAge());
        // print the new pet in the same line as the print statement above
        System.out.println(pets[petId].getName() + " " + pets[petId].getAge());
        System.out.println();
    }
*/

    // searchPetsByName method
    public static void searchPetsByName(){
        System.out.print("Enter a name to search: ");
        String searchName = s.nextLine();
        int j = 0;
        printTableHeader();
        // for each pet
        while(j < petCount){
            // if the pet name equals the users seachName then print that row
            if(pets[j].getName().equals(searchName)){
                printTableRow(j, pets[j].getName(), pets[j].getAge());
            }
            j++;
        }
        printTableFooter();
    }

    // searchPetsByAge method
    public static void searchPetsByAge(){
        System.out.print("Enter an age to search: ");
        int searchAge = s.nextInt();
        s.nextLine();
        int j = 0;
        printTableHeader();
        // for each pet
        while(j < petCount){
            // if the pet age equals the users searchAge then print that row
            if(pets[j].getAge() == searchAge){
                printTableRow(j, pets[j].getName(), pets[j].getAge());
            }
            j++;
        }
        printTableFooter();
    }

    // addPets method
    public static void addPets() throws FullDatabaseException, InvalidAgeException, InvalidArgumentException{
        if(petCount == capacity){
            throw new FullDatabaseException();
        }
        while(true){
            System.out.print("add pet (name, age): ");
            String petString = s.nextLine();
            // check to see if the user is done
            if (petString.equals("done")){
                System.out.println();
                break;
            }

            try{
                // otherwise try to split the line, throw InvalidArgumentException if petNameAndAge != 2 (parseArgument does this)
                // also throw NumberFormatException (parseInt does this) if the age the user entered is not valid, but i will make that appear as a InvalidArgumentException
                String[] petNameAndAge = parseArgument(petString);
                String petName = petNameAndAge[0];
                int petAge = Integer.parseInt(petNameAndAge[1]);

                // now try to add the pet
                try{
                    addPet(petName, petAge);
                }
                // catch FullDatabaseException
                catch(FullDatabaseException fde){
                    System.out.println("FullDatabaseException: Database is full.");
                }
                // catch invalid age exception
                catch(InvalidAgeException iae){
                    System.out.println("InvalidPetAge: " + petAge + " is not a valid age.");
                }
            }
            // catch InvalidArgumentException
            catch(InvalidArgumentException eiae){
                System.out.println("InvalidArgumentException: "+ petString + " is not a valid input");
            }
            // this is my solution to keep the program going if the user doesnt enter an integer for the age, where i just show InvalidArgumentException
            catch(NumberFormatException nfe){
                System.out.println("InvalidArgumentException: "+ petString + " is not a valid input");
            }
        }
    }

    // getUserChoice method
    public static int getUserChoice(){
        // print the menu 
        System.out.println("What would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        //System.out.println("3) Update an existing pet");
        System.out.println("3) Remove an existing pet");
        System.out.println("4) Search pets by name");
        System.out.println("5) Search pets by age");
        System.out.println("6) Exit program");	
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
