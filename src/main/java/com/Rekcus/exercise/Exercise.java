import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

/**
*   Exercise 2
*   - Add row
*   - Add cell
*   - Added Sort
*   - Added File persistence
*
*   java version: 1.8.0
*   author: Aron Vibar
*   date: 02/12/2019
*/

public class Exercise implements MenuOptions{   
    private static int CELL_LENGTH = 3;
    private static char FIELD_SEPARATOR = (char)31;
    private static char CELL_SEPARATOR = (char)30;

    private List<List<Cell>> matrix;    

    public Exercise() {
        this.matrix = new ArrayList<List<Cell>>();  
    }

    private Cell generateCell() {
        Cell cell;
        String str1; 
        String str2;

        do{
            str1 = ExerciseUtil.generateString(CELL_LENGTH);
        }while(!(ExerciseUtil.isUnique(this.matrix,str1)));

        str2 = ExerciseUtil.generateString(CELL_LENGTH);
        cell = new Cell(str1,str2);
        return cell;
    }

    public void generateMatrix(File file) throws IOException {
        List<Cell> cellRow;
        int rows = ExerciseUtil.getInteger("Enter number of rows: ", false);
        int cols = ExerciseUtil.getInteger("Enter number of columns: ", false);
        this.matrix = new ArrayList<List<Cell>>();

        for (int i=0;i<rows;i++) {
            cellRow = new ArrayList<Cell>();
            for (int j=0;j<cols;j++) {
                cellRow.add(generateCell());
            }
            this.matrix.add(cellRow);
        }

        System.out.println("Matrix generated.");
        saveMatrix(file);
    }

    @Override
    public void search() {
        String substring = ExerciseUtil.getStringInput("Search String: ");
        int count = 0;
        boolean found = false;

        for (List<Cell> row : this.matrix) {
            for (Cell cell : row) {
                count = ExerciseUtil.findSubstring(substring,cell.getKey());
                ExerciseUtil.printSearchResult(substring,this.matrix.indexOf(row)
                                               ,this.matrix.get(this.matrix.indexOf(row)).indexOf(cell)
                                               ,0,count);
                if(count > 0) {
                    found = true;
                }

                count = ExerciseUtil.findSubstring(substring,cell.getValue());
                ExerciseUtil.printSearchResult(substring,this.matrix.indexOf(row)
                                               ,this.matrix.get(this.matrix.indexOf(row)).indexOf(cell)
                                               ,1,count);
                if(count > 0) {
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println(substring + " was not found.");
        }

    }

    @Override
    public void edit(File file) throws IOException{
        int index;
        int row = ExerciseUtil.getInteger("Enter row: ", true);
        int col = ExerciseUtil.getInteger("Enter column: ", true);
        
        do{
            index = ExerciseUtil.getInteger("Enter either 0 or 1 or 2: ", true);
            
            if(index == 0 || index == 1 || index == 2){
                if(index == 0){
                    this.matrix.get(row).get(col).setKey(
                        ExerciseUtil.getStringInput("Replacement Key: ",this.matrix,true));
                }else if (index == 1){
                    this.matrix.get(row).get(col).setValue(
                        ExerciseUtil.getStringInput("Replacement Value: "));
                }else{
                    this.matrix.get(row).get(col).setKey(
                        ExerciseUtil.getStringInput("Replacement Key: ",this.matrix,true));
                    this.matrix.get(row).get(col).setValue(
                        ExerciseUtil.getStringInput("Replacement Value: "));
                }
                break;
            }
        }while(true);

        saveMatrix(file);
    }

    @Override
    public void print() {
        System.out.println();
        for (List<Cell> row : this.matrix) {
            for (Cell t : row){
                System.out.print(t.toString());
                System.out.print("|");
            }
            
            System.out.println();
        }
    }

    @Override
    public void reset(File file) throws IOException{
        generateMatrix(file);
        saveMatrix(file);
    }

    @Override
    public void addRow(File file) throws IOException{
        List<Cell> cellRow;
        cellRow = new ArrayList<Cell>();
        cellRow.add(generateCell());
        this.matrix.add(cellRow);
        System.out.println("New row added.");
        saveMatrix(file);
    }

    @Override
    public void addCell(File file) throws IOException{
        Cell cell;
        int row = ExerciseUtil.getInteger("Enter row: ", this.matrix.size() ,true);
        String str = ExerciseUtil.getStringInput("Enter key: ", this.matrix, true);
        String str2 = ExerciseUtil.getStringInput("Enter Value: ");

        this.matrix.get(row).add(new Cell(str,str2));

        System.out.println("Cell added to row " + row);
        saveMatrix(file);
    }

    @Override
    public void sort(File file) throws IOException{
        for (List<Cell> row : this.matrix) {
            Collections.sort(row, new Comparator<Cell>() {
                public int compare(Cell a, Cell b){
                    return a.getCellString().compareTo(b.getCellString());
                }
            });
        }
        
        System.out.println("Matrix sorted.");
        saveMatrix(file);
    }


    public void saveMatrix(File file) throws IOException {
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file)
                                                ,StandardCharsets.UTF_8);

        BufferedWriter out = new BufferedWriter(fw);
        for (List<Cell> row : this.matrix) {
            for (Cell cell : row){
                String str = cell.getKey() + FIELD_SEPARATOR + cell.getValue() 
                                 + CELL_SEPARATOR;
                out.write(str);
                out.flush();
            }
            out.write("\n");
            out.flush();
       }
       out.close();
    }

    public void loadMatrix(File file) throws IOException {
        String line;
        String[] row;
        String[] cellEntry;
        Cell cell;
        List<Cell> cellRow;
        Map<String,String> keys = new HashMap<>();
            
        try{
           InputStreamReader fr = new InputStreamReader(new FileInputStream(file),
                                                        StandardCharsets.UTF_8);
           BufferedReader br = new BufferedReader(fr);
                
           while((line = br.readLine())!=null) {
               row = line.split(String.valueOf(CELL_SEPARATOR));
               cellRow = new ArrayList<Cell>();
               for(String temp : row){     
                    cellEntry = temp.split(String.valueOf(FIELD_SEPARATOR));
                      
                    if(keys.containsKey(cellEntry[0])) {
                        System.out.println("Error: " + cellEntry[0] + " already exists!");
                        System.exit(0);
                    }

                    keys.put(cellEntry[0],cellEntry[1]);

                    cell = new Cell(cellEntry[0], cellEntry[1]);
                    cellRow.add(cell);
               }
               this.matrix.add(cellRow);
            }
            
            fr.close();
                
        }catch(Exception e){}
        
        System.out.println("Matrix loaded.");
    }

}