package com.example.blism.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.example.blism.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "mailboxes")
public class Mailbox extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member owner;

    // 우체통에 속한 편지들
    @OneToMany(mappedBy = "mailbox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> letters = new ArrayList<>();

    @Column(nullable = false)
    private Integer visibility; // 0: false, 1: true => Boolean으로 처리

    // 답장과의 관계
    @OneToMany(mappedBy = "mailbox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    private Mailbox(Member owner) {
        this.owner = owner;
    }

    public static Mailbox createMailbox(Member owner) {
        return Mailbox.builder()
            .owner(owner)
            .build();
    }

    public void changeVisibility(int visibility) {
        if(visibility == 1)
            this.visibility = 1;
        else
            this.visibility = visibility;
    }

    public void addLetter(List<Letter> letters) {
        if(this.letters == null){
            this.letters = new ArrayList<>();
        }
        this.letters.addAll(letters);
    }

    public void addReply(List<Reply> replies) {
        if(this.replies == null){
            this.replies = new ArrayList<>();
        }
        this.replies.addAll(replies);
    }
}
