package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/save")
    public String save() {
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "/memberPages/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession httpSession) {
        MemberDTO memberDTO1 = memberService.login(memberDTO);

        if (memberDTO1 != null) {
            httpSession.setAttribute("loginEmail", memberDTO1.getMemberEmail());
            return "/memberPages/main";
        } else {
            return "/memberPages/login";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id")Long id,
                         Model model){
        try {
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "/memberPages/detail";
        }catch (NoSuchElementException e){
            return "/memberPages/NotFound";
        }
    }




    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Long id,
                         Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO,
                         Model model){
        memberService.update(memberDTO);
        MemberDTO memberDTO1 = memberService.findById(memberDTO.getId());
        model.addAttribute("member", memberDTO1);
        return "/memberPages/update";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        memberService.delete(id);
        return "redirect:/member/list";
    }
}











