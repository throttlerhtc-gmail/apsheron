package com.restaurant.apsheron.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Food.ALL_SORTED, query = "SELECT m FROM Food m WHERE m.manager.id=:managerId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Food.DELETE, query = "DELETE FROM Food m WHERE m.id=:id AND m.manager.id=:managerId"),
        @NamedQuery(name = Food.GET_BETWEEN, query = "SELECT m FROM Food m " +
                "WHERE m.manager.id=:managerId AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC"),
//        @NamedQuery(name = Food.UPDATE, query = "UPDATE Food m SET m.dateTime = :datetime, m.calories= :calories," +
//                "m.description=:desc where m.id=:id and m.manager.id=:managerId")
})
@Entity
@Table(name = "foods", uniqueConstraints = {@UniqueConstraint(columnNames = {"manager_id", "date_time"}, name = "foods_unique_manager_datetime_idx")})
public class Food extends AbstractBaseEntity {
    public static final String ALL_SORTED = "Food.getAll";
    public static final String DELETE = "Food.delete";
    public static final String GET_BETWEEN = "Food.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @NotNull
    private Manager manager;

    public Food() {
    }

    public Food(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Food(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
