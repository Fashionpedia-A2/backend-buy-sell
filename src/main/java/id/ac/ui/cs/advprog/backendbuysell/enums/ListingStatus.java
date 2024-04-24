package id.ac.ui.cs.advprog.backendbuysell.enums;

import lombok.Getter;

@Getter
public enum ListingStatus {
    PENDING("PENDING"),
    VERIFIED("VERIFIED"),
    REJECTED("REJECTED");

    private final String value;

    ListingStatus(String value){
        this.value = value;
    }

    public static boolean contains(String param){
        for (ListingStatus status: ListingStatus.values()){
            if(status.name().equals(param)){
                return true;
            }
        }
        return false;
    }
}
