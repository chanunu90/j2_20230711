package org.zerock.j2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_cart" , indexes = @Index(columnList = "email,cno"))
public class MemberCart{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long cno;

        private String email;

        private Long pno;

        //baseentity로 시간넣어줘야함

}
