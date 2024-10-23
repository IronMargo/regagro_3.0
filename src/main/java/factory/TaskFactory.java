package factory;

import builders.TaskBuilder;
import entities.tasks.Task;
import enums.User;

public class TaskFactory {
    TaskBuilder taskBuilder = new TaskBuilder();
    public Task createTask(String taskType, User user, int daysCount, int enterprisesCount) {
        if (taskType.equals("Дезинфекция") || taskType.equals("Дезинсекция") | taskType.equals("Дератизация")) {
            return taskBuilder
                    .setName(taskType)
                    .setDateFrom()
                    .setDateBefore(daysCount)
                    .setType(taskType)
                    .setServiceArea(user)
                    .setEnterpriseName(user, enterprisesCount)
                    .build();
        } else if (taskType.equals("Дегельминтизация")) {
            return taskBuilder
                    .setName(taskType)
                    .setDateFrom()
                    .setDateBefore(daysCount)
                    .setType(taskType)
                    .setServiceArea(user)
                    .setDiseaseGroup()
                    .setDisease()
                    .setEnterpriseName(user, enterprisesCount)
                    .setKinds()
                    .build();
        } else {
            return taskBuilder
                    .setName(taskType)
                    .setDateFrom()
                    .setDateBefore(daysCount)
                    .setType(taskType)
                    .setServiceArea(user)
                    .setDisease()
                    .setEnterpriseName(user, enterprisesCount)
                    .setKinds()
                    .setCoExecutor()
                    .build();
        }
    }
}
