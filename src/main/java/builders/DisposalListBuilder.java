package builders;
import abstractclass.Builder;
import entities.animals.AnimalGroupResponse;
import entities.disposals.DisposalList;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DisposalListBuilder extends Builder<DisposalList> {
    private AnimalGroupResponse animalGroupResponse;

    public DisposalListBuilder() {
        super();
    }

    @Override
    protected DisposalList createObject() {
        return new DisposalList();
    }

    public DisposalListBuilder setId(String animalNumber) {
        String animalId = regagroDB.getAnimalId(animalNumber);
        setField("animalId", animalId);
        return this;
    }

    public DisposalListBuilder setReason(String reason) {
        setField("reason", reason);
        return this;
    }

    public DisposalListBuilder setReasonClarification() {
        List<String> reasonClassification = handbooksDB.values("SELECT disposal_reason_clarifications.name FROM disposal_reason_clarifications \n" +
                "JOIN disposal_clarification_reasons ON disposal_reason_clarifications.id = disposal_clarification_reasons.clarification_id \n" +
                "JOIN disposal_reasons ON disposal_reasons.id = disposal_clarification_reasons.disposal_reason_id \n" +
                "WHERE disposal_reasons.name = '" + object.getReason() + "'", "name");
        Collections.shuffle(reasonClassification);
        setField("reasonClarification", reasonClassification.get(random.nextInt(reasonClassification.size())));
        return this;
    }

    public DisposalListBuilder setEnterpriseFrom(String enterpriseName) {
        setField("enterpriseFrom", enterpriseName);
        return this;
    }

    public DisposalListBuilder setAnimalNumber() {
        String generalQuery = "SELECT DISTINCT animals.number FROM animals \n" +
                "JOIN supervised_objects ON supervised_objects.id = animals.supervised_object_id \n" +
                "JOIN enterprises ON supervised_objects.enterprise_id = enterprises.id \n" +
                "WHERE animals.is_super_group = 0 \n" +
                "AND animals.deleted_at IS NULL \n" +
                "AND supervised_objects.deleted_at IS NULL \n" +
                "AND supervised_objects.deleted_at IS NULL \n" +
                "AND enterprises.name = '"+ object.getEnterpriseFrom() + "' \n" +
                "AND animals.id NOT IN (\n" +
                "SELECT animals.id FROM animals\n" +
                "JOIN inventory_animals ON inventory_animals.animal_id = animals.id \n" +
                "JOIN inventories ON inventories.id = inventory_animals.inventory_id \n" +
                "AND (inventories.status_id = 2 OR inventories.status_id = 1)\n" +
                ")\n" +
                "AND animals.id NOT IN (\n" +
                "SELECT animals.id FROM animals\n" +
                "JOIN disposal_list_animals ON disposal_list_animals.animal_id = animals.id \n" +
                "JOIN disposal_lists ON disposal_lists.id = disposal_list_animals.id \n" +
                "WHERE disposal_lists.disposal_status_id = 1\n" +
                ") ";
        List<String> animalNumbers;
        if (object.getReason().equals("Фактический убой")) {
            animalNumbers = regagroDB.values(generalQuery + "AND supervised_objects.type_id = 22 \n", "number");
        } else {
            animalNumbers = regagroDB.values(generalQuery, "number");
        }
        Collections.shuffle(animalNumbers);
        setField("animalNumber", animalNumbers.get(random.nextInt(animalNumbers.size())));
        return this;
    }

    public DisposalListBuilder setAnimalNumber(String animalNumber) {
        setField("animalNumber", animalNumber);
        return this;
    }

    public DisposalListBuilder setId() {
        String animalId = regagroDB.getAnimalId(object.getAnimalNumber());
        setField("animalId", animalId);
        return this;
    }

    public DisposalListBuilder setUserId(Map<String, String> cookies) throws JsonProcessingException {
        animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(object.getAnimalNumber(), cookies);
        setField("userId", animalGroupResponse.getUser_id());
        return this;
    }

    public DisposalListBuilder setOwnerId(Map<String, String> cookies) throws JsonProcessingException {
        animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(object.getAnimalNumber(), cookies);
        setField("ownerId", animalGroupResponse.getOwner_id());
        return this;
    }

    public DisposalListBuilder setSupervisedObjectFrom() {
        List<String> supervisedObjects = regagroDB.values("SELECT supervised_objects.name FROM supervised_objects\n" +
                "JOIN animals ON animals.supervised_object_id = supervised_objects.id \n" +
                "JOIN regagro_3_0_handbooks.supervised_object_types \n" +
                "WHERE animals.number = '" + object.getAnimalNumber() + "'", "name");
        setField("supervisedObjectFrom", supervisedObjects.get(0));
        return this;
    }

    public DisposalListBuilder setSupervisedObjectTo(String reason) {
        String queryBase = "SELECT * FROM supervised_objects \n" +
                "JOIN users ON supervised_objects.user_id = users.id \n" +
                "WHERE users.organization_id = 66\n" +
                "AND supervised_objects.deleted_at IS NULL \n" +
                "AND supervised_objects.name NOT LIKE '" + object.getSupervisedObjectFrom() + "'";
        String queryForOtherUser = "AND supervised_objects.user_id NOT LIKE " + object.getUserId() + "\n";
        String queryForKillReason = "AND supervised_objects.type_id = 22";
        String queryToSameOwner = "AND supervised_objects.owner_id = " + object.getOwnerId() + " \n";
        List<String> supervisedObjects = new ArrayList<>();
        try {
            if (reason.equals("Продажа")) {
                supervisedObjects = regagroDB.values(queryBase + queryForOtherUser, "name");
            } else if (reason.equals("Временное перемещение")) {
                supervisedObjects = regagroDB.values(queryBase, "name");
            } else if (reason.equals("Направление на убой")) {
                supervisedObjects = regagroDB.values(queryBase + queryForKillReason, "name");
            } else if (reason.equals("Перемещение между объектами владельца")) {
                supervisedObjects = regagroDB.values(queryBase + queryToSameOwner, "name");
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Список пуст");
        }
        Collections.shuffle(supervisedObjects);
        setField("supervisedObjectTo", supervisedObjects.get(0));
        return this;
    }

    public DisposalListBuilder setKind() {
        List<String> kinds = regagroDB.values("SELECT regagro_3_0_handbooks.kinds.name FROM regagro_3_0_handbooks.kinds\n" +
                "JOIN animals ON animals.kind_id  = regagro_3_0_handbooks.kinds.id \n" +
                "WHERE animals.number = '" + object.getAnimalNumber() + "'", "name");
        setField("animalKind", kinds.get(0));
        return this;
    }

    public DisposalListBuilder setCount(Map<String, String> cookies) throws JsonProcessingException {
        animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(object.getAnimalNumber(), cookies);
        setField("initialCount", animalGroupResponse.getCount());
        return this;
    }

    public DisposalListBuilder setCountOfMale(Map<String, String> cookies) throws JsonProcessingException {
        animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(object.getAnimalNumber(), cookies);
        setField("initialCountOfMale", animalGroupResponse.getCount_male());
        return this;
    }
    public DisposalListBuilder setCountOfFemale(Map<String, String> cookies) throws JsonProcessingException {
        animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(object.getAnimalNumber(), cookies);
        setField("initialCountOfFemale", animalGroupResponse.getCount_female());
        return this;
    }
    @Override
    public DisposalList build() {
        return object;
    }
}
