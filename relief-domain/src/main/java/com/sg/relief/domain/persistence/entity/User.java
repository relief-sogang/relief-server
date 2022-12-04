package com.sg.relief.domain.persistence.entity;

import com.sg.relief.domain.auth.code.Role;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Table(name="users")
@Entity
//@SuperBuilder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;    // 유저 이름

    @Column(nullable = false)
    private String email; // 유저 이메일

    @Column(nullable = false)
    private String userId;  // 유저 ID

    @Column
    private String phoneNumber; // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }


}
