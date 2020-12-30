package eu.accesa.prointerhyp.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Table(value = "team")
public class UserEntity {

    @PrimaryKey
    private Integer id;
    @Column(value = "details")
    private String details;
    @Column(value = "first_name")
    private String firstName;
    @Column(value = "name")
    private String name;

    public UserEntity() {
    }

    public UserEntity(Integer id, String details, String firstName, String name) {
        this.id = id;
        this.details = details;
        this.firstName = firstName;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", details='" + details + '\'' +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(details, that.details) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, firstName, name);
    }
}
