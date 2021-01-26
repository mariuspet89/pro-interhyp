package eu.accesa.prointerhyp.model.dto;

import java.util.UUID;

public class UserToDepartmentDto {
    private String department;
    private UUID userId;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

