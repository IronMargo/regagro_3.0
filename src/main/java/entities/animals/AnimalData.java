package entities.animals;

import lombok.Getter;

@Getter
public class AnimalData {
    private String kind;
    private String markerType;
    private String number;
    private String markerDate;
    public AnimalData(String kind, String markerType, String number, String markerDate) {
        this.kind = kind;
        this.markerType = markerType;
        this.number = number;
        this.markerDate = markerDate;
    }
}
