package factory;

import entities.tasks.Inventory;
import builders.InventoriesBuilder;
import entities.tasks.Task;

public class InventoriesFactory {
    public Inventory createInventorie(Task task) {
        InventoriesBuilder inventoriesBuilder = new InventoriesBuilder();

        switch (task.getType()) {
            case "Вакцинация":
            case "Аллергические исследования":
                return createVaccinationOrAllergicInventory(inventoriesBuilder, task);

            case "Дезинфекция":
            case "Дезинсекция":
            case "Дератизация":
                return create4DInventorie(inventoriesBuilder, task);

            case "Диагностические исследования":
                return createDiagnosticInventorie(inventoriesBuilder, task);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + task.getType());
        }
    }

    private Inventory createVaccinationOrAllergicInventory(InventoriesBuilder inventoriesBuilder, Task task) {
        return inventoriesBuilder
                .setAvailableCount()
                .setDrugName(task)
                .setUnitName()
                .setSeries()
                .setNumberOfBatch()
                .setGosControl()
                .setDateFrom()
                .setDateBefore()
                .setDose()
                .setDrugUseWay()
                .setInjectionPlace()
                .setAntisepticName()
                .setAntisepticCount()
                .setCottonCount()
                .setSyringeCount()
                .setGlovesCount()
                .setPost()
                .setName()
                .setResult()
                .setThickness()
                .setDate()
                .build();
    }

    private Inventory create4DInventorie(InventoriesBuilder inventoriesBuilder, Task task) {
        return inventoriesBuilder
                .setTreatmentProduct(task)
                .setMethod()
                .setSolutionSpent()
                .setVolume()
                .setConcentration()
                .setCountOfDrug()
                .setTemperature()
                .setSolutionSpentFact()
                .setSquareOfPlace()
                .setSquareOfWalking()
                .setsquareOfArea()
                .setVolumeOfLiquidCollector()
                .setLaboratory()
                .setPost()
                .setName()
                .setTemperatureAtPlace()
                .setCloseTime()
                .setDate()
                .build();
    }

    private Inventory createDiagnosticInventorie(InventoriesBuilder inventoriesBuilder, Task task) {
        return inventoriesBuilder
                .setLaboratory()
                .setPost()
                .setName()
                .setResearchCounts()
                .setMaterialResearch()
                .setMethodResearch()
                .setNameOfBatchTwo()
                .setStorageConditions()
                .setTubeNumber()
                .setDateOfSending()
                .setDate()
                .build();
    }


}
