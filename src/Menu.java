import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Menu {
    public int menuOptionFilter(){
        Scanner scanner = new Scanner(System.in);
        boolean verifier = true;
        int choice = 0;

        while (verifier) {
            System.out.println("Menú:");
            System.out.println("1. Black and white filter");
            System.out.println("2. Sepia filter");
            System.out.println("3. Colors inverted filter");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            if(choice != 1 && choice != 2 && choice != 3){
                System.out.println("Invalid option. Please choose a valid option.");
            }
            else{
                verifier = false;
            }

        }
        return choice;
    }

    public String pathImage(){
        Scanner scanner = new Scanner(System.in);
        boolean verifier = true;
        String path = null;
        BufferedImage image = null;

        while (verifier){
            System.out.print("Enter an image path: ");
            path = scanner.next();
            try {
                image = ImageIO.read(new File(path));
                System.out.println("The image has been found");
                verifier = false;
            } catch (IOException e) {
                System.out.println("The image could not be found. Check the path and format of the image.");
            }
        }
        return path;
    }

    public int cores(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of cores: ");
        int core = scanner.nextInt();
        return core;
    }
}
