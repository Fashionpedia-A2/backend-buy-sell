package id.ac.ui.cs.advprog.backendbuysell.enums;

import lombok.Getter;

@Getter
public enum ListingCondition {
    NEW("NEW"),
    VERY_GOOD("VERY_GOOD"),
    GOOD("GOOD"),
    SATISFACTORY("SATISFACTORY"),
    NOT_SPECIFIED("NOT_SPECIFIED");

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

    public static String getString() {
        StringBuilder result = new StringBuilder("[");
        boolean isFirst = true;
        for (ListingCondition condition : ListingCondition.values()) {
            if (!isFirst) {
                result.append(", ");
            }
            result.append(condition.getValue());
            isFirst = false;
        }
        result.append("]");
        return result.toString();
    }
}
