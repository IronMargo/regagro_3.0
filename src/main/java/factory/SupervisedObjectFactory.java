package factory;

import builders.SupervisedObjectBuilder;
import entities.objects_enterprises.SupervisedObject;

public class SupervisedObjectFactory {
    SupervisedObjectBuilder supervisedObjectBuilder = new SupervisedObjectBuilder();
    public SupervisedObject createSupervisedObject(String supervisedObjectName, String enterpriseName){
        return supervisedObjectBuilder
                .setEnterpriseName(enterpriseName)
                .setOwnerInn()
                .setSupervisedObjectName(supervisedObjectName)
                .setSupervisedObjectType()
                .setSupervisedObjectActivity()
                .build();
    }

}
