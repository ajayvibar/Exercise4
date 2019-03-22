import java.util.Random;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.commons.math3.random.RandomDataGenerator;

/**
*   Exercise Utility
*       - class that contains all utility methods
*         for the exercise
*/

public final class ExerciseUtil {
    
    private static Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    public static int getInteger(String message, boolean allowZero) {
        int num;
        System.out.print(message);
        
        while(!scanner.hasNextInt()) {
            System.out.println("You did not enter an integer");
            System.out.print(message);
            scanner.next();
        }

        num = scanner.nextInt();

        if((!allowZero && num <= 0) || (num < 0)) {
            System.out.println("0 and lower is not allowed.");
            num = getInteger(message,false);
        }

        return num;
    }

    public static int getInteger(String message, int max, boolean allowZero) {
        int num;
        
        do{
            num = getInteger(message,allowZero);
            if(num >= max){
                System.out.println("Invalid input.");
            }
        }while(num >= max);

        return num;
    }

    public static String getStringInput(String message) {
        System.out.print(message);
        return scanner.next();
    }

    public static boolean isUnique(List<List<Cell>> matrix, String entry) {
        boolean found = true;
        for(List<Cell> row : matrix) {
            for(Cell cell : row){
                if(cell.getKey().equals(entry)){
                    return false;
                }
            }
        }
        return found;
    }

    public static String getStringInput(String message, List<List<Cell>> matrix, boolean isUnique) {
        String str;
        boolean unique = false;

        if(!isUnique){
            return getStringInput(message);
        }

        do{
            str = getStringInput(message);
            unique = isUnique(matrix,str);
            if(!unique){
                System.out.println("Key " + str + " already exists.");
            }
        }while(!unique);
        
        return str;
    }

    public static int findSubstring(String substring, String superstring) {
        int count = 0;
        int lastIndex = 0;
        Pattern pattern = Pattern.compile(substring);
        Matcher matcher = pattern.matcher(superstring);
        
        while(matcher.find(lastIndex)) {
            count++;
            lastIndex = matcher.start() + 1;
        }

        return count;
    }

    public static void printSearchResult(String substring, int row, int col, int index, int count) {
        if(count == 0) {
            return;
        }

        System.out.println(substring +" was found in ("
                            + row + ", " + col +") at " + index + " " 
                            + count + " time(s).");
    }

    public static String generateString(int length) {
        StringBuilder str = new StringBuilder();
        RandomDataGenerator rand = new RandomDataGenerator();

        for(int i=0;i<length;i++) {
            str.append(Character.toChars(rand.nextInt(32,126)));
        }

        return str.toString();
    }
}