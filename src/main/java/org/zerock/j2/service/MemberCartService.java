package org.zerock.j2.service;

import jakarta.transaction.Transactional;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

@Transactional
public interface MemberCartService {


    List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO);

    //변환작업
    default MemberCart dtoToEntity(MemberCartDTO dto){

        MemberCart entity = MemberCart.builder()
                .cno(dto.getCno())
                .pno(dto.getPno())
                .email(dto.getEmail())
                .build();

        return entity;

    }

    default MemberCartDTO entityToDto(MemberCart entity){

        MemberCartDTO dto = MemberCartDTO.builder()
                .cno(entity.getCno())
                .pno(entity.getPno())
                .email(entity.getEmail())
                .build();

        return dto;

    }


    public List<MemberCartDTO> getCart(String enmil);

}
