package entities.disposals;

import lombok.Getter;

@Getter
public class DisposalList {
    String animalId;
    String reason;
    String reasonClarification;
    String enterpriseFrom;
    String supervisedObjectFrom;
    String supervisedObjectTo;
    String enterpriseTo;
    String animalNumber;
    String animalKind;
    int ownerId;
    int userId;
    int initialCount;
    int initialCountOfMale;
    int initialCountOfFemale;
}
