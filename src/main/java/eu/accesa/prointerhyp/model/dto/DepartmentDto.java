package eu.accesa.prointerhyp.model.dto;

import java.util.Objects;
import java.util.UUID;

public class DepartmentDto {

    private String name;
    private String description;
    private int size;
    private UUID userId;

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return size == that.size && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, size, userId);
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", userId=" + userId +
                '}';
    }
}
