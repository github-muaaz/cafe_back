package com.example.cafe.entity;

import com.example.cafe.entity.template.AbsUUIDEntity;
import com.example.cafe.utils.ColumnKey;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class User extends AbsUUIDEntity implements UserDetails {
    @Column(nullable = false, name = ColumnKey.FIRST_NAME)
    private String firstname;

    @Column(name = ColumnKey.LAST_NAME)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne()
    private Role role;

    private boolean enabled = true;

    private LocalDateTime tokenIssuedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.isNull(role))
            return null;
        return role.getPermissions();
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
