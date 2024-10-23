package builders;

import entities.tasks.Task;
import enums.User;
import services.ConfigReader;
import services.database.DBService;
import services.database.HandbooksDBService;
import services.database.RegagroDBService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TaskBuilder {
    private final Task task;
    private final RegagroDBService regagroDB;
    private final HandbooksDBService handbooksDB;
    private final Random random;

    public TaskBuilder() {
        this.task = new Task();
        this.regagroDB = DBService.getRegagroDBService();
        this.handbooksDB = DBService.getHandbooksDBService();
        this.random = new Random();
    }

    public TaskBuilder setName(String type) {
        task.name = type + " авто " + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public TaskBuilder setDateFrom() {
        task.dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public TaskBuilder setDateBefore(int daysCount) {
        task.dateBefore = LocalDate.now().plusDays(daysCount).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public TaskBuilder setType(String type) {
        task.type = type;
        return this;
    }

    public TaskBuilder setServiceArea(User user) {
        task.serviceArea = ConfigReader.getUserServiceArea(user.getRole());
        return this;
    }

    public TaskBuilder setDiseaseGroup() {
        List<String> diseasesGroups = handbooksDB.values("SELECT disease_groups.name FROM disease_groups", "name");
        List<String> tasksGroups = new ArrayList<>();
        String secondGroup;
        tasksGroups.add(diseasesGroups.get(random.nextInt(diseasesGroups.size())));
        do {
           secondGroup = diseasesGroups.get(random.nextInt(diseasesGroups.size()));
        } while (!tasksGroups.get(0).equals(secondGroup));
        tasksGroups.add(secondGroup);
        task.diseasesGroups = tasksGroups;
        return this;
    }

    public TaskBuilder setDisease() {
        List<String> diseases = new ArrayList<>();
        String generalQuerySelect = "SELECT DISTINCT diseases.name FROM diseases \n" +
                "JOIN disease_kinds \n" +
                "ON diseases.id = disease_kinds.disease_id \n" +
                "JOIN disease_epizootology_events \n" +
                "ON disease_epizootology_events.disease_id = diseases.id \n" +
                "JOIN epizootology_events \n" +
                "ON epizootology_events.id = disease_epizootology_events.epizootology_event_id ";
        String generalQueryWithDrugs = " JOIN disease_drugs\n" +
                "ON disease_drugs.disease_id = diseases.id ";
        String generalQueryConditions = " WHERE disease_kinds.kind_id = 1 \n" +
                "AND epizootology_events.name  = '" + task.getType() + "' \n" +
                "AND diseases.deleted_at IS NULL ";
        switch (task.getType()) {
            case "Вакцинация":
                diseases = handbooksDB.values(generalQuerySelect + generalQueryWithDrugs +
                        "JOIN drug_use_schemes ON disease_drugs.drug_id = drug_use_schemes.drug_id \n" +
                        generalQueryConditions, "name");
                break;
            case "Дезинфекция":
                diseases = handbooksDB.values("SELECT DISTINCT diseases.name FROM diseases \n" +
                                "WHERE diseases.deleted_at IS NULL", "name");
                break;
            case "Аллергические исследования":
                diseases = handbooksDB.values(generalQuerySelect + generalQueryWithDrugs + generalQueryConditions, "name");
                break;
            case "Диагностические исследования":
                diseases = handbooksDB.values(generalQuerySelect +
                        " JOIN disease_research_schemes ON disease_research_schemes.disease_id = diseases.id " +
                        generalQueryConditions, "name");
                break;
            case "Дегельминтизация":
                diseases = handbooksDB.values(generalQuerySelect + generalQueryWithDrugs +
                        "  WHERE disease_kinds.kind_id = 1 \n" +
                        "  AND epizootology_events.name  = '" + task.getType() + "' \n" +
                        "  AND diseases.parent_id IN (\n" +
                        "  SELECT id from diseases \n" +
                        "  WHERE name = '" + task.getDiseasesGroups().get(1) +"' \n" +
                        "  ) \n" +
                        "  AND diseases.deleted_at IS NULL", "name");
        }
        task.disease = diseases.get(random.nextInt(diseases.size()));
        return this;
    }

    public TaskBuilder setEnterpriseName(User user, int enterprisesCount) {
        List<String> enterprises = new ArrayList<>();
        String mainPartOFQuery = "SELECT DISTINCT enterprises.name \n" +
                "FROM enterprises \n" +
                "JOIN service_areas ON service_areas.id = enterprises.service_area_id\n" +
                "JOIN animals ON animals.enterprise_id = enterprises.id \n" +
                "JOIN users ON enterprises.user_id = users.id \n" +
                "WHERE enterprises.name IS NOT NULL \n" +
                "AND users.email = '" + ConfigReader.getUserEmail(user.getRole()) +"' \n" +
                "AND enterprises.deleted_at IS NULL ";
        String firstSubQuery4d = "AND enterprises.id NOT IN ( " +
                "SELECT task_enterprises.enterprise_id \n" +
                "FROM tasks \n" +
                "JOIN task_enterprises  \n" +
                "ON tasks.id = task_enterprises.task_id \n" +
                "JOIN regagro_3_0_handbooks.epizootology_events \n" +
                "ON tasks.event_id = regagro_3_0_handbooks.epizootology_events.id \n" +
                "JOIN inventories ON inventories.task_id = tasks.id \n" +
                "WHERE regagro_3_0_handbooks.epizootology_events.name = '" + task.getType() + "' \n" +
                "AND (inventories.status_id IN (1, 4) OR tasks.date_to >= CURRENT_DATE()) ";
        String firstSubQueryForOtherEvent = " AND animals.kind_id = 1  " +
                "AND animals.deleted_at IS NULL \n" +
                "AND animals.id NOT IN ( \n" +
                "SELECT task_animals.animal_id \n" +
                "FROM task_animals \n" +
                "JOIN tasks ON tasks.id = task_animals.task_id \n" +
                "JOIN regagro_3_0_handbooks.epizootology_events ON regagro_3_0_handbooks.epizootology_events.id = tasks.event_id \n" +
                "JOIN task_diseases ON task_diseases.task_id = tasks.id \n" +
                "JOIN regagro_3_0_handbooks.diseases ON  regagro_3_0_handbooks.diseases.id = task_diseases.disease_id \n" +
                "JOIN inventories ON inventories.task_id = tasks.id \n" +
                "WHERE regagro_3_0_handbooks.epizootology_events.name = '" + task.getType() + "' \n" +
                "AND regagro_3_0_handbooks.diseases.name = '" + task.getDisease() + "' \n" +
                "AND (inventories.status_id IN (1, 4) OR tasks.date_to >= CURRENT_DATE()) \n" +
                "AND (task_animals.rejection_reason_id NOT IN (1, 2) OR task_animals.rejection_reason_id IS NULL) ";
        switch (task.getType()) {
            case "Дезинфекция":
                enterprises = regagroDB.values(mainPartOFQuery + firstSubQuery4d +
                        "UNION \n" +
                        "SELECT disinfections.enterprise_id \n" +
                        "FROM disinfections \n" +
                        "WHERE disinfections.next_date > CURRENT_DATE() \n" +
                        ")", "name");
                break;
            case "Дезинсекция":
                enterprises = regagroDB.values(mainPartOFQuery + firstSubQuery4d +
                        "UNION \n" +
                        "SELECT desinsections.enterprise_id \n" +
                        "FROM desinsections \n" +
                        "WHERE desinsections.next_date > CURRENT_DATE() \n" +
                        ")", "name");
                break;
            case "Дератизация":
                enterprises = regagroDB.values(mainPartOFQuery + firstSubQuery4d +
                        "UNION \n" +
                        "SELECT deratizations.enterprise_id \n" +
                        "FROM deratizations \n" +
                        "WHERE deratizations.next_date > CURRENT_DATE() \n" +
                        ")", "name");
                break;
            case "Аллергические исследования":
                enterprises = regagroDB.values(mainPartOFQuery +
                        "AND animals.kind_id = 1  \n" +
                        "AND animals.deleted_at IS NULL \n" +
                        "AND animals.id NOT IN ( \n" +
                        "    SELECT task_animals.animal_id \n" +
                        "    FROM task_animals \n" +
                        "    JOIN tasks ON tasks.id = task_animals.task_id \n" +
                        "    JOIN regagro_3_0_handbooks.epizootology_events ON regagro_3_0_handbooks.epizootology_events.id = tasks.event_id \n" +
                        "    JOIN task_diseases ON task_diseases.task_id = tasks.id \n" +
                        "    JOIN regagro_3_0_handbooks.diseases ON  regagro_3_0_handbooks.diseases.id = task_diseases.disease_id \n" +
                        "    JOIN inventories ON inventories.task_id = tasks.id \n" +
                        "    WHERE regagro_3_0_handbooks.epizootology_events.name = '" + task.getType() + "' \n" +
                        "    AND regagro_3_0_handbooks.diseases.name = '" + task.getDisease() + "' \n" +
                        "    AND (inventories.status_id IN (1, 4) OR tasks.date_to >= CURRENT_DATE()) \n" +
                        "    AND (task_animals.rejection_reason_id NOT IN (1, 2) OR task_animals.rejection_reason_id IS NULL) \n" +
                        ")", "name");
                break;
            case "Диагностические исследования":
                enterprises = regagroDB.values(mainPartOFQuery + firstSubQueryForOtherEvent +
                        "    UNION \n" +
                        "    SELECT animal_id \n" +
                        "    FROM animal_diagnostic_research_calendars \n" +
                        "    JOIN regagro_3_0_handbooks.diseases ON regagro_3_0_handbooks.diseases.id = animal_diagnostic_research_calendars.disease_id \n" +
                        "    WHERE regagro_3_0_handbooks.diseases.name = '" + task.getDisease() + "' \n" +
                        "    AND animal_diagnostic_research_calendars.date <= CURRENT_DATE() \n" +
                        "    HAVING COUNT(*) > 0  \n" +
                        ")", "name");
                break;
            case "Вакцинация":
                enterprises = regagroDB.values(mainPartOFQuery + firstSubQueryForOtherEvent +
                        "    UNION \n" +
                        "    SELECT animal_id \n" +
                        "    FROM animal_vaccination_calendars \n" +
                        "    JOIN regagro_3_0_handbooks.diseases ON regagro_3_0_handbooks.diseases.id = animal_vaccination_calendars.disease_id \n" +
                        "    WHERE regagro_3_0_handbooks.diseases.name = '" + task.getDisease() + "' \n" +
                        "    AND animal_vaccination_calendars.date <= CURRENT_DATE() \n" +
                        "    HAVING COUNT(*) > 0  \n" +
                        ")", "name");
                break;
        }
        List<String> enterpriseForTask = new ArrayList<>();

        if (enterprises.size() >= enterprisesCount) {

            // Рандомизируем список предприятий
            Collections.shuffle(enterprises);

            // Выбираем первые три
            for (int i = 0; i < enterprisesCount; i++) {
                enterpriseForTask.add(enterprises.get(i));
            }
        }
        task.enterpriseName = enterpriseForTask;
        return this;
    }

    public TaskBuilder setKindsForDiseasesGroup() {
        task.kinds = handbooksDB.values("SELECT kinds.name FROM diseases " +
                "JOIN disease_kinds " +
                "ON diseases.id = disease_kinds.disease_id " +
                "JOIN kinds " +
                "ON kinds.id = disease_kinds.kind_id  " +
                "WHERE diseases.parent_id = " + task.getDiseasesGroups(), "name");
        return this;
    }

    public TaskBuilder setKinds() {
        task.kinds = handbooksDB.values("SELECT kinds.name FROM diseases " +
                "JOIN disease_kinds " +
                "ON diseases.id = disease_kinds.disease_id " +
                "JOIN kinds " +
                "ON kinds.id = disease_kinds.kind_id  " +
                "WHERE diseases.name = '" + task.getDisease() + "'", "name");
        return this;
    }

    public TaskBuilder setCoExecutor() {
        List<String> userNames = regagroDB.values("SELECT DISTINCT users.name FROM users \n" +
                "JOIN role_user \n" +
                "ON users.id = role_user.user_id \n" +
                "JOIN user_service_areas \n" +
                "ON user_service_areas.user_id = users.id \n" +
                "JOIN service_areas\n" +
                "ON user_service_areas.service_area_id = service_areas.id \n" +
                "WHERE users.organization_id = 66\n" +
                "AND role_user.role_id = 2 \n" +
                "AND service_areas.name != '" + task.getServiceArea() + "'", "name");
        task.coExecutor = userNames.get(random.nextInt(userNames.size()));
        return this;
    }

    public Task build() {
        return task;
    }
}
