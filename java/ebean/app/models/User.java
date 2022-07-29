package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public Long userId;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @Column(name = "user_email")
    public String email;

    public static final Finder<Long, User> find = new Finder<>(User.class);

}
