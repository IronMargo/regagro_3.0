package entities.tasks;

import lombok.Getter;

import java.util.List;

@Getter
public class Task {
    public String name;
    public String dateFrom;
    public String dateBefore;
    public String type;
    public String serviceArea;
    public List<String> enterpriseName;
    public String disease;
    public List<String> diseasesGroups;
    public List<String> kinds;
    public String coExecutor;
}
