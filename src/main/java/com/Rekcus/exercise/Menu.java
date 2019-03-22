import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.EnumUtils;

enum Menu {
    SEARCH("SEARCH"),
    EDIT("EDIT"),
    PRINT("PRINT"),
    RESET("RESET"),
    ADDROW("ADDROW"),
    ADDCELL("ADDCELL"),
    SORT("SORT"),
    EXIT("EXIT");
               
    private final String name;

    private Menu(String name) {
        this.name = name;
    }
               
    public boolean equals(String choice) {
        return choice.equals(name);
    }
}