package builders;

import entities.objects_enterprises.SupervisedObject;

import services.database.DBService;
import services.database.HandbooksDBService;
import services.database.RegagroDBService;

import java.util.List;
import java.util.Random;

public class SupervisedObjectBuilder {
    private final Random random;
    private final HandbooksDBService handbooksDBService;
    private final RegagroDBService regagroDBService;
    private final SupervisedObject supervisedObject;

    public SupervisedObjectBuilder() {
        this.supervisedObject = new SupervisedObject();
        this.handbooksDBService = DBService.getHandbooksDBService();
        this.regagroDBService = DBService.getRegagroDBService();
        this.random = new Random();
    }

    public SupervisedObjectBuilder setEnterpriseName(String enterpriseName) {
        supervisedObject.enterpriseName = enterpriseName;
        return this;
    }
    public SupervisedObjectBuilder setOwnerInn() {
        supervisedObject.ownerInn = "7736280232";
        return this;
    }
    public SupervisedObjectBuilder setSupervisedObjectName(String name) {
        supervisedObject.supervisedObjectName = name;
        return this;
    }
    public SupervisedObjectBuilder setSupervisedObjectType() {
        List<String> supervisedObjectTypes = handbooksDBService.values(
                "SELECT name FROM supervised_object_types", "name");
        supervisedObject.type = supervisedObjectTypes.get(random.nextInt(supervisedObjectTypes.size()));
        return this;
    }

    public SupervisedObjectBuilder setSupervisedObjectActivity() {
        List<String> supervisedObjectActivities = handbooksDBService.values(
                "SELECT name FROM supervised_object_activities", "name");
        supervisedObject.activity = supervisedObjectActivities.get(random.nextInt(supervisedObjectActivities.size()));
        return this;
    }
    public SupervisedObject build(){
        return supervisedObject;
    }
}
