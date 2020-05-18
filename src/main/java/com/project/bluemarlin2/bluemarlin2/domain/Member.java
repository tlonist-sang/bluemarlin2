package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain=true)
public class Member {
    public Member() {
    }

    public Member(String email, String userId, RoleType roleType, String password) {
        this.email = email;
        this.userId = userId;
        this.roleType = roleType;
        this.password = password;
    }

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @OneToMany (mappedBy = "member", cascade = CascadeType.ALL)
    private List<UrlSource> urlSources = new ArrayList<>();
    private String email;

    private String userId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private String password;
    private int mailInterval;
    private LocalDateTime recentLogin;
    private Long refreshTokenVersion;


    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
