package enums;

public enum DisposalReasons {
    // Окончательные причины

    MURRIAN("Падеж"),
    FORCED_KILL("Вынужденный убой"),
    PERSONAL_NEEDS("Личные нужды"),
    LOST("Утеряно"),
    FACT_KILL("Фактический убой"),
    OUTSIDE_THE_RF("Выбытие за пределы РФ"),

    // Неокончательные причины
    SALE("Продажа"),
    MOVING_BETWEEN_OWNERS_OBJECTS("Перемещение между объектами владельца"),
    TEMPORARY_MOVEMENT("Временное перемещение"),
    DIRECTION_FOR_KILL("Направление на убой");
    private final String reason;
    DisposalReasons(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return reason;
    }
}
