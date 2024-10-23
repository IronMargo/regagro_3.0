package abstractclass;

import services.AnimalResponseService;
import services.database.DBService;
import services.database.HandbooksDBService;
import services.database.RegagroDBService;

import java.lang.reflect.Field;
import java.util.Random;

public abstract class Builder<T> {

    protected final T object;
    protected final RegagroDBService regagroDB;
    protected final HandbooksDBService handbooksDB;
    protected final AnimalResponseService animalResponseService;


    protected final Random random;

    public Builder() {
        this.object = createObject();
        this.regagroDB = DBService.getRegagroDBService();
        this.handbooksDB = DBService.getHandbooksDBService();
        this.animalResponseService = new AnimalResponseService();
        this.random = new Random();
    }

    protected abstract T createObject();

    public abstract T build();

    public void setField(String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Ошибка при установке значения поля " + name, e);
        }
    }
}
