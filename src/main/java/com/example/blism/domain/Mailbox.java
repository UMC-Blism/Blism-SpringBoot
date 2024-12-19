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
@Table(name = "mailboxes")
public class Mailbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member owner;

    // 우체통에 속한 편지들
    @OneToMany(mappedBy = "mailbox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> letters = new ArrayList<>();

    @Column(nullable = false)
    private int visibility; // 0: false, 1: true => Boolean으로 처리

    @Column(nullable = false)
    private Integer password;

    // 필요하다면 letter_id를 통해 특정 Letter 참조 가능
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "letter_id")
    // private Letter mainLetter;

    // 답장과의 관계
    @OneToMany(mappedBy = "mailbox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

}
