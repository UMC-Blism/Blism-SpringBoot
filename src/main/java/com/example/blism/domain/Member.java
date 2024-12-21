package com.example.blism.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 255, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String checkCode;

    // 한 Member는 여러 Mailbox를 가질 수 있음
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mailbox> mailboxes = new ArrayList<>();

    // 편지 발신, 수신 관계 매핑
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> sentLetters = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> receivedLetters = new ArrayList<>();

    // 답장 발신, 수신 관계 매핑
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> sentReplies = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> receivedReplies = new ArrayList<>();

}
