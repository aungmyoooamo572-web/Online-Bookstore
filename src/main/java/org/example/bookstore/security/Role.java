package org.example.bookstore.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "roles")
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue
    private UUID id;
    private String roleName;
}
