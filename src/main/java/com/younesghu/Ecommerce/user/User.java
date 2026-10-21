package com.younesghu.Ecommerce.user;

import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.exception.InvalidEmailException;
import com.younesghu.Ecommerce.exception.InvalidPasswordException;
import com.younesghu.Ecommerce.exception.InvalidUserNameException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled = true;

    public User(String firstName, String lastName, String email, String encodedPassword, Role role) {
        changeName(firstName, lastName);
        changeEmail(email);
        changePassword(encodedPassword);
        this.role = role != null ? role : Role.CUSTOMER;
    }

    public void changeName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new InvalidUserNameException();
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    public void changePassword(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new InvalidPasswordException();
        }
        this.password = encodedPassword;
    }

    public void disable() {
        this.enabled = false;
    }

    public void enable() {
        this.enabled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
