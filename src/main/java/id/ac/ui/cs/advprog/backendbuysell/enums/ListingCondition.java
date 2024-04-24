package id.ac.ui.cs.advprog.backendbuysell.enums;

import lombok.Getter;

@Getter
public enum ListingCondition {
    NEW("NEW"),
    VERY_GOOD("VERY_GOOD"),
    GOOD("VERY_GOOD"),
    SATISFACTORY("SATISFACTORY");

    private final String value;

    ListingCondition(String value){
        this.value = value;
    }

    public static boolean contains(String param){
        for (ListingCondition condition: ListingCondition.values()){
            if(condition.name().equals(param)){
                return true;
            }
        }
        return false;
    }
}
