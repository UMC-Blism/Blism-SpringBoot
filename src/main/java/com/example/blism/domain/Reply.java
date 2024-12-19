package com.example.blism.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 편지에 대한 답장인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id", nullable = false)
    private Letter letter;

    // 어떤 우체통에 속한 답장인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mailbox_id", nullable = false)
    private Mailbox mailbox;

    // 답장 발신, 수신자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Lob
    private String content;

    @Column(nullable = false)
    private Integer font; // 0 ~ 4

    @Column(length = 255)
    private String photoUrl;

}
