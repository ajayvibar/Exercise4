/**
*   Cell class
*       An object that contains a pair of Strings a and b
*       and the concatenation of both strings cellString
*       
*       
*/

public class Cell{
    private String key; //index 0 entry
    private String value; //index 1 entry
    private String cellString; //combination of index 0 and 1
    
    public Cell(String key, String value){
        this.key = key;
        this.value = value;
        this.cellString = key+value;
    }
    
    public String getKey(){
        return this.key;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public String getCellString(){
        return this.cellString;
    }
     
    public void setKey(String input){
        this.key = input;
        this.cellString = key+value;
    }
    
    public void setValue(String input){
        this.value = input;
        this.cellString = key+value;
    }
    
    @Override
    public String toString(){
        String str = "(" + this.key + " , " + this.value + ")";
        return str;
    }
        
}
