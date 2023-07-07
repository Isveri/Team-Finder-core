package com.evi.teamfindercore.domain;

import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name="users")
@EqualsAndHashCode(of = {"id","username","email"})
@Where(clause = "deleted=false")
public class User implements UserDetails, CredentialsContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

   // @NotBlank
    private String email;

    private String name;

    private String info;

    private String age;

    private String phone;

    private String city;

    private String profileImgName;

    private String reason;

    private String bannedBy;

    @Transient
    private String token;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="role_id")
    private Role role;


    @OneToMany(mappedBy = "user",cascade = CascadeType.MERGE)
    private List<Platform> platforms;

    @OneToMany(mappedBy="reportedUser")
    private List<Report> reports;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_friends",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="friend_id")
    )
    private List<Friend> friendList;

    @ManyToMany
    @JoinTable(
            name = "users_ingamerole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ingamerole_id"))
    private List<InGameRole> inGameRoles;

    public String roleToString(){
        return this.role.getName();
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = this.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));

        return authorities;
    }

    @Builder.Default
    private boolean deleted = false;



    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = false;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}