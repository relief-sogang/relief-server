package com.sg.relief.domain.persistence.entity;

import com.sg.relief.domain.code.Role;
import com.sg.relief.domain.code.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;    // 유저 이름

    @Column(nullable = false)
    private String email; // 유저 이메일

    @Column
    private String userId;  // 유저 ID

    @Column
    private String phoneNumber; // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column
    private Date createdAt;

    @Column
    private Date modifiedAt;

    @Builder
    public User(String name, String email, Role role, UserStatus status, Date createdAt, Date modifiedAt) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

//    public User update(String name) {
//        this.name = name;
//        return this;
//    }

    public String getRoleKey(){
        return this.role.getKey();
    }


}
