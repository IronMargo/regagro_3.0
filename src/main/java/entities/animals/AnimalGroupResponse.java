package entities.animals;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорирует неизвестные поля
public class AnimalGroupResponse {

    public int id;
    public String birth_date;
    public int kind_id;
    public int suit_id;
    public int breed_id;
    public int registration_ground_id;
    public int gender;
    public int keep_type_id;
    public int keep_purpose_id;
    public int keep_place_id;
    public int enterprise_id;
    public int count;
    public int user_id;
    public int product_direction_id;
    public String number;
    public int is_group;
    public String guid;
    public String first_marker_date;
    public int supervised_object_id;
    @JsonProperty("animal_count_range")
    Map<String, Integer> animalCountRange;
    public int count_male;
    public int count_female;
    @JsonProperty("supervised_object")
    Map<String, Object> supervisedObjectInfo;
    public int owner_id;

}
