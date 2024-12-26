package kr.ontherec.authorization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kr.ontherec.authorization.entity.type.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    private static final String DELIMITER =  ", ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private String roles;

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles.stream().map(Enum::toString).collect(Collectors.joining(DELIMITER));
    }

    public List<Role> getRoles() {
        return Arrays.stream(roles.split(DELIMITER)).map(Role::valueOf).toList();
    }
}
