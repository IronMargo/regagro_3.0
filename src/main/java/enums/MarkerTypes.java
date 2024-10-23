package enums;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static datagenerator.DataGeneratorDate.isDateBeforeMarch;

public enum MarkerTypes {
    BIRK("Бирка"),
    CHIP("Чип"),
    ElECTRONIC_TAG("Эл. метка"),
    BOLUS("Болюс"),
    INVENTORY_NUMBER("Инвентарй номер"),
    FOREIGN_BIRK("Иностранная бирка"),
    REGAGRO_NUMBER("Номер REGAGRO"),
    TATTOO("Татуировка"),
    RSHN_HOLDING_PLACE_MARKER("Маркер места содержания РСХН"),
    COLLAR("Ошейник"),
    RING("Кольцо"),
    SCOREBOARD("Табло"),
    WING_MARK("Крыло-метка"),
    ON_FARM_BIRK("Внутрихозяйственная бирка"),
    ON_FARM_SCOREBOARD("Внутрихозяйственное табло"),
    ELECTRONIC_COLLAR("Электронный ошейник"),
    ELECTRONIC_WING_MARK("Электронное крыло-метка"),
    ELECTRONIC_RING("Электронное кольцо"),
    REGAGRO_TRANSPONDER("Транспондер REGAGRO");

    private final String markerType;
    public static final Set<MarkerTypes> MAIN_MARKER_TYPES_BEFORE_MARCH = new HashSet<>(Arrays.asList(BIRK, CHIP, ElECTRONIC_TAG, BOLUS, INVENTORY_NUMBER, FOREIGN_BIRK,
            TATTOO, COLLAR, RING, WING_MARK, ON_FARM_BIRK, ELECTRONIC_COLLAR, ELECTRONIC_WING_MARK, ELECTRONIC_RING, SCOREBOARD, ON_FARM_SCOREBOARD));
    public static final Set<MarkerTypes> MAIN_MARKER_TYPES_AFTER_MARCH = new HashSet<>(Arrays.asList(BIRK, CHIP, ElECTRONIC_TAG, BOLUS, FOREIGN_BIRK,
            COLLAR, RING, WING_MARK, ELECTRONIC_COLLAR, ELECTRONIC_WING_MARK, ELECTRONIC_RING, SCOREBOARD));
    public static final Set<MarkerTypes> NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_BEFORE_MARCH = new HashSet<>(Arrays.asList(CHIP, ElECTRONIC_TAG, BOLUS, FOREIGN_BIRK, INVENTORY_NUMBER,
            COLLAR, RING, WING_MARK, ELECTRONIC_COLLAR, ELECTRONIC_WING_MARK, ELECTRONIC_RING, TATTOO, ON_FARM_BIRK));
    public static final Set<MarkerTypes> NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_AFTER_MARCH = new HashSet<>(Arrays.asList(CHIP, ElECTRONIC_TAG, BOLUS, FOREIGN_BIRK, INVENTORY_NUMBER,
            ELECTRONIC_COLLAR, ELECTRONIC_WING_MARK, ELECTRONIC_RING, TATTOO, ON_FARM_BIRK));

    MarkerTypes(String markerType) {
        this.markerType = markerType;
    }

    public String getMarkerType() {
        return markerType;
    }

    public MarkerTypes getMarkerTypeByName(String markerType) {
        try {
            MarkerTypes result = null;
            for (MarkerTypes markerTypes : values()) {
                if (markerTypes.getMarkerType().equals(markerType)) {
                    result = markerTypes;
                    break;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("В системе отсутствует средство маркирования со значением: " + markerType);
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при поиске средства маркирования" + e.getMessage());
        }
    }

    // Проверка - основное ли средство маркирования
    public boolean isMarkerTypeMain(LocalDate date, String markerTypeName) {
        MarkerTypes markerType = getMarkerTypeByName(markerTypeName);
        if (isDateBeforeMarch(date)) {
            return MAIN_MARKER_TYPES_BEFORE_MARCH.contains(markerType);
        } else {
            return MAIN_MARKER_TYPES_AFTER_MARCH.contains(markerType);
        }
    }
}
