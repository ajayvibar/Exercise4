import java.io.File;
import java.io.IOException;
/*
*   Menu Interface   
*   Interface for menus for the exercise
*
*/

interface MenuOptions {
    public void search();
    public void edit(File file) throws IOException;
    public void print();
    public void reset(File file) throws IOException;
    public void addRow(File file) throws IOException;
    public void addCell(File file) throws IOException;
    public void sort(File file) throws IOException;
}