package builders;

import entities.tasks.Inventory;
import entities.tasks.Task;
import services.database.DBService;
import services.database.HandbooksDBService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static datagenerator.DataGeneratorNumbers.getCount;

public class InventoriesBuilder {
    private final Inventory inventory;
    private final HandbooksDBService dbHelper;
    private final Random random;

    public InventoriesBuilder() {
        this.inventory = new Inventory();
        this.dbHelper = DBService.getHandbooksDBService();
        this.random = new Random();
    }

    public InventoriesBuilder setTreatmentProduct(Task task) {
        List<String> drugs = dbHelper.values("SELECT treatment_products.name\n" +
                "FROM treatment_products \n" +
                "JOIN treatment_product_epizootology_events \n" +
                "ON treatment_product_epizootology_events.treatment_product_id = treatment_products.id \n" +
                "JOIN regagro_3_0_handbooks.epizootology_events \n" +
                "ON treatment_product_epizootology_events.epizootology_event_id = regagro_3_0_handbooks.epizootology_events.id \n" +
                "WHERE regagro_3_0_handbooks.epizootology_events.name = '" + task.getType() + "' ", "name");
        inventory.treatmentProduct = drugs.get(random.nextInt(drugs.size()));
        return this;
    }

    public InventoriesBuilder setMethod() {
        List<String> methods = dbHelper.values("SELECT name FROM treatment_product_methods", "name");
        inventory.method = methods.get(random.nextInt(methods.size()));
        return this;
    }

    public InventoriesBuilder setSolutionSpent() {
        inventory.solutionSpent = "2";
        return this;
    }

    public InventoriesBuilder setVolume() {
        inventory.volume = "м²";
        return this;
    }

    public InventoriesBuilder setConcentration() {
        inventory.concentration = "0.2";
        return this;
    }

    public InventoriesBuilder setCountOfDrug() {
        inventory.countOfDrug = "2";
        return this;
    }

    public InventoriesBuilder setTemperature() {
        inventory.temperature = "2";
        return this;
    }

    public InventoriesBuilder setSolutionSpentFact() {
        inventory.solutionSpentFact = "2";
        return this;
    }

    public InventoriesBuilder setSquareOfPlace() {
        inventory.squareOfPlace = "2";
        return this;
    }

    public InventoriesBuilder setSquareOfWalking() {
        inventory.squareOfWalking = "2";
        return this;
    }

    public InventoriesBuilder setsquareOfArea() {
        inventory.squareOfArea = "2";
        return this;
    }

    public InventoriesBuilder setVolumeOfLiquidCollector() {
        inventory.volumeOfLiquidCollector = "2";
        return this;
    }

    public InventoriesBuilder setLaboratory() {
        inventory.laboratory = "ливенская";
        return this;
    }

    public InventoriesBuilder setPost() {
        inventory.post = "Ветеринарный врач";
        return this;
    }

    public InventoriesBuilder setName() {
        inventory.name = "Ветеринаров Игорь Сергеевич";
        return this;
    }

    public InventoriesBuilder setTemperatureAtPlace() {
        inventory.temperatureAtPlace = "22";
        return this;
    }

    public InventoriesBuilder setCloseTime() {
        inventory.closeTime = "22";
        return this;
    }

    // Вакцинация
    public InventoriesBuilder setAvailableCount() {
        inventory.availableCount = getCount(3);
        return this;
    }

    public InventoriesBuilder setDrugName(Task task) {
        List<String> drugNames;
        String generalQuery = "SELECT DISTINCT drug_info.trade_name  FROM drug_info\n" +
                "JOIN disease_drugs \n" +
                "ON disease_drugs.drug_id = drug_info.drug_id  \n" +
                "JOIN diseases \n" +
                "ON diseases.id = disease_drugs.disease_id \n";
        String queryAllergicJoin = " WHERE diseases.name = '" + task.getDisease() + "'";
        String queryVaccineJoin = "JOIN drug_use_schemes ON disease_drugs.drug_id = drug_use_schemes.drug_id \n" +
                " WHERE diseases.name = '" + task.getDisease() + "' \n" +
                "AND drug_use_schemes.kind_id = 1";
        if (task.getType().equals("Аллергические исследования")) {
            drugNames = dbHelper.values(generalQuery + queryAllergicJoin, "trade_name");
        } else {
            drugNames = dbHelper.values(generalQuery + queryVaccineJoin, "trade_name");
        }
        inventory.drugName = drugNames.get(random.nextInt(drugNames.size()));
        return this;
    }

    public InventoriesBuilder setUnitName() {
        List<String> unitNames = dbHelper.values("SELECT name FROM units", "name");
        inventory.unitName = unitNames.get(random.nextInt(unitNames.size()));
        return this;
    }

    public InventoriesBuilder setSeries() {
        inventory.series = getCount(2);
        return this;
    }

    public InventoriesBuilder setNumberOfBatch() {
        inventory.numberOfBatch = getCount(2);
        return this;
    }

    public InventoriesBuilder setGosControl() {
        inventory.gosControl = getCount(2);
        return this;
    }

    public InventoriesBuilder setDateFrom() {
        inventory.dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public InventoriesBuilder setDateBefore() {
        inventory.dateBefore = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public InventoriesBuilder setDose() {
        inventory.dose = getCount(1);
        return this;
    }

    public InventoriesBuilder setDrugUseWay() {
        List<String> drugUseWays = dbHelper.values("SELECT name FROM drug_use_ways", "name");
        inventory.drugUseWay = drugUseWays.get(random.nextInt(drugUseWays.size()));
        return this;
    }

    public InventoriesBuilder setInjectionPlace() {
        List<String> injectionPlaces = dbHelper.values("SELECT name FROM injection_places", "name");
        inventory.injectionPlace = injectionPlaces.get(random.nextInt(injectionPlaces.size()));
        return this;
    }

    public InventoriesBuilder setAntisepticName() {
        List<String> antisepticNames = dbHelper.values("SELECT name FROM antiseptics", "name");
        inventory.antisepticName = antisepticNames.get(random.nextInt(antisepticNames.size()));
        return this;
    }

    public InventoriesBuilder setAntisepticCount() {
        inventory.antisepticCount = getCount(1);
        return this;
    }

    public InventoriesBuilder setCottonCount() {
        inventory.cottonCount = getCount(1);
        return this;
    }

    public InventoriesBuilder setSyringeCount() {
        inventory.syringeCount = getCount(1);
        return this;
    }

    public InventoriesBuilder setGlovesCount() {
        inventory.glovesCount = getCount(1);
        return this;
    }

    // Аллергические исследования
    public InventoriesBuilder setResult() {
        List<String> results = dbHelper.values("SELECT name FROM research_results", "name");
        inventory.result = results.get(random.nextInt(results.size()));
        return this;
    }

    public InventoriesBuilder setThickness() {
        inventory.thickness = getCount(1);
        return this;
    }

    // Диагностические исследования
    public InventoriesBuilder setResearchCounts() {
        inventory.researchCounts = getCount(1);
        inventory.researchCountsBatchTwo = getCount(1);
        return this;
    }

    public InventoriesBuilder setMaterialResearch() {
        List<String> materials = dbHelper.values("SELECT name FROM research_materials", "name");
        inventory.materialResearch = materials.get(random.nextInt(materials.size()));
        inventory.materialResearchBatchTwo = materials.get(random.nextInt(materials.size()));
        return this;
    }

    public InventoriesBuilder setMethodResearch() {
        List<String> methods = dbHelper.values("SELECT name FROM research_methods " +
                "WHERE name NOT LIKE('Другое')  ", "name");
        inventory.methodResearch = methods.get(random.nextInt(methods.size()));
        inventory.methodResearchBatchTwo = methods.get(random.nextInt(methods.size()));
        return this;
    }

    public InventoriesBuilder setNameOfBatchTwo() {
        inventory.nameOfBatchTwo = "Партия 2";
        return this;
    }

    public InventoriesBuilder setStorageConditions() {
        inventory.storageConditions = "Холодильник";
        return this;
    }

    public InventoriesBuilder setTubeNumber() {
        inventory.tubeNumber = getCount(2);
        return this;
    }

    public InventoriesBuilder setDateOfSending() {
        inventory.dateOfSending = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public InventoriesBuilder setDate() {
        inventory.date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
