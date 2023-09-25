package com.example.memberboard.dto;

import com.example.memberboard.entity.MemberEntity;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
