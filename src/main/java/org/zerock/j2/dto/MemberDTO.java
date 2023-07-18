package org.zerock.j2.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private String email;


    private String pw;

    private String nickname;

    private boolean admin;


}
