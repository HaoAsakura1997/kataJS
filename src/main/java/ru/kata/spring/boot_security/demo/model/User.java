package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Поля для сприна и аутентификации
    private String username;
    private String password;
    private boolean active;

    private String email;

    //Роли пользователей
    //Eager потому что количество ролей это практически всегда мало данных,
    //так что проблем с жадной загрузкой в данной ситуации нет
    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String surname;

    private int age;


    // Методы из UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    //Мы сделали булево поле active которое будет отображать данный
    //для отключения Юзера
    @Override
    public boolean isEnabled() {
        return isActive();
    }

    //Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (result + (((int) surname.length()) << 5)) * 31;
        result = (result + (((int) age) << 5)) * 31;
        result = (result + (((int) username.length()) << 5)) * 31;
        result = (result + (((int) username.length()) << 5)) * 31;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || obj.getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return ((username.compareTo(user.getUsername()) == 0) &&
                ((surname.compareTo(user.getSurname())) == 0) &&
                ((email.compareTo(user.getEmail()) == 0)) &&
                ((Long.compare(id, user.getId())) == 0) &&
                (Integer.compare(age, user.getAge())) == 0);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
