package org.zerock.j2.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberCartDTO {

    private Long cno;

    private String email;

    private Long pno;

}
