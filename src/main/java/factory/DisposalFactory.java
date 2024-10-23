package factory;

import builders.DisposalListBuilder;
import entities.disposals.DisposalList;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;

import java.util.Map;

public class DisposalFactory {
    private final DisposalListBuilder disposalListBuilder = new DisposalListBuilder();

    public DisposalList createDisposalList(String reason, String enterpriseName, Map<String, String> cookies) throws JsonProcessingException {
        switch (reason) {
            case "Личные нужды":
            case "Утеряно":
            case "Фактический убой":
            case "Выбытие за пределы РФ":
                return disposalListBuilder
                        .setReason(reason)
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber()
                        .setKind()
                        .setSupervisedObjectFrom()
                        .build();
            case "Падеж":
            case "Вынужденный убой":
                return disposalListBuilder
                        .setReason(reason)
                        .setReasonClarification()
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber()
                        .setKind()
                        .setSupervisedObjectFrom()
                        .build();
            case "Продажа":
            case "Направление на убой":
            case "Перемещение между объектами владельца":
                return disposalListBuilder
                        .setReason(reason)
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber()
                        .setId()
                        .setOwnerId(cookies)
                        .setUserId(cookies)
                        .setKind()
                        .setSupervisedObjectFrom()
                        .setSupervisedObjectTo(reason)
                        .build();
            case "Временное перемещение":
                return disposalListBuilder
                        .setReason(reason)
                        .setReasonClarification()
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber()
                        .setId()
                        .setOwnerId(cookies)
                        .setUserId(cookies)
                        .setKind()
                        .setSupervisedObjectFrom()
                        .setSupervisedObjectTo(reason)
                        .build();
            default:
                throw new IllegalArgumentException("Неизвестная причина выбытия: " + reason);
        }
    }

    public DisposalList createDisposalListFromGroup(
            String reason, String number, String enterpriseName, Map<String, String> cookies) throws JsonProcessingException {
        switch (reason) {
            case "Личные нужды":
            case "Утеряно":
            case "Фактический убой":
            case "Выбытие за пределы РФ":
                return disposalListBuilder
                        .setId(number)
                        .setReason(reason)
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber(number)
                        .setCount(cookies)
                        .setCountOfMale(cookies)
                        .setCountOfFemale(cookies)
                        .build();
            case "Падеж":
            case "Вынужденный убой":
                return disposalListBuilder
                        .setId(number)
                        .setReason(reason)
                        .setReasonClarification()
                        .setEnterpriseFrom(enterpriseName)
                        .setAnimalNumber(number)
                        .setCount(cookies)
                        .setCountOfMale(cookies)
                        .setCountOfFemale(cookies)
                        .build();
            default:
                throw new IllegalArgumentException("Неизвестная причина выбытия: " + reason);
        }
    }
}