package com.restaurant.apsheron.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

import static com.restaurant.apsheron.util.FoodsUtil.DEFAULT_CALORIES_PER_DAY;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = Manager.DELETE, query = "DELETE FROM Manager m WHERE m.id=:id"),
        @NamedQuery(name = Manager.BY_EMAIL, query = "SELECT DISTINCT m FROM Manager m LEFT JOIN FETCH m.auths WHERE m.email=?1"),
        @NamedQuery(name = Manager.ALL_SORTED, query = "SELECT m FROM Manager m ORDER BY m.name, m.email"),
})
@Entity
@Table(name = "managers", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "managers_unique_email_idx")})
public class Manager extends AbstractNamedEntity {

    public static final String DELETE = "Manager.delete";
    public static final String BY_EMAIL = "Manager.getByEmail";
    public static final String ALL_SORTED = "Manager.getAllSorted";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "manager_auths", joinColumns = @JoinColumn(name = "manager_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"manager_id", "auth"}, name = "manager_auths_unique_idx")})
    @Column(name = "auth")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    private Set<Auth> auths;

    @Column(name = "calories_per_day", nullable = false, columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("dateTime DESC")
//    @JsonIgnore
    private List<Food> foods;

    public Manager() {
    }

    public Manager(Manager u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCaloriesPerDay(), u.isEnabled(), u.getRegistered(), u.getAuths());
    }

    public Manager(Integer id, String name, String email, String password, Auth auth, Auth... auths) {
        this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, new Date(), EnumSet.of(auth, auths));
    }

    public Manager(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Date registered, Collection<Auth> auths) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.registered = registered;
        setAuths(auths);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public String getPassword() {
        return password;
    }

    public void setAuths(Collection<Auth> auths) {
        this.auths = CollectionUtils.isEmpty(auths) ? EnumSet.noneOf(Auth.class) : EnumSet.copyOf(auths);
    }

    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", auths=" + auths +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}