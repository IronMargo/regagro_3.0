package entities.objects_enterprises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.restassured.RestAssured.given;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressFull {
    private int id;
    private int object_id;
    private String object_guid;
    private int change_id;
    private String name;
    private String type_name;
    private int level;
    private int oper_type_id;
    private int prev_id;
    private int next_id;
    private String update_date;
    private String start_date;
    private String end_date;
    private int is_actual;
    private int is_active;
}