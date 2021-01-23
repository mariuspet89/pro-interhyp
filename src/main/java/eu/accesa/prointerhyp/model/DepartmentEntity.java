package eu.accesa.prointerhyp.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table(value = "department")
public class DepartmentEntity {

    @PrimaryKey
    private String name;
    private String description;
    private int size;
    @Column("users")
    private List<UUID> userIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<UUID> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UUID> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", users=" + userIds +
                '}';
    }
}
