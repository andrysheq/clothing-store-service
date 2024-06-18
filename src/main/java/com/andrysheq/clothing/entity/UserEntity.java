package com.andrysheq.clothing.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class UserEntity implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    //@NotBlank(message="Поле 'Логин' не может быть пустым")
    private String username;
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Некорректный формат пароля")
    private String password;
    //@NotBlank(message="Поле 'Имя' не может быть пустым")
    private String fullName;
    //@NotBlank(message="Поле 'Улица' не может быть пустым")
    private String street;
    //@NotBlank(message="Поле 'Город' не может быть пустым")
    private String city;
    //@NotBlank(message="Поле 'Область' не может быть пустым")
    private String state;
    //@NotBlank(message="Поле 'Индекс' не может быть пустым")
    private String zip;
    //@Pattern(regexp = "\\+\\d{11}", message = "Некорректный формат номера телефона")
    private String phoneNumber;
    private byte[] photo;
    private boolean accountNonLocked;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private WishList wishList = new WishList();

    // Добавьте методы для управления ролями, например, добавление/удаление
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
    public boolean hasRole(String id){
        for(Role role : roles){
            if(role.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void removeAllRoles(){
        roles.clear();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    public String getRole() {
        if (roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return "ROLE_ADMIN";
        } else if (roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_MODERATOR"))) {
            return "ROLE_MODERATOR";
        } else {
            return "ROLE_USER"; // Или любую другую роль по умолчанию
        }
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }
}
