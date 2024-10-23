package entities.animals;

import lombok.Getter;

@Getter
public class AnimalIdentificationResponse {
    public int id;
    public int animal_id;
    public int user_id;
    public int marker_type_id;

    public String number;
    public Object twin_number;
    public int is_active;
    public int marker_place_id;
    public String marker_date;
}
