package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String login(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI,
                        Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession httpSession,
                        @RequestParam("redirectURI") String redirectURI) {
        MemberDTO memberDTO1 = memberService.login(memberDTO);

        if (memberDTO1 != null) {
            httpSession.setAttribute("loginEmail", memberDTO1.getMemberEmail());
//            return "/memberPages/main";
            // 사용자가 로그인 성공하면, 직전에 요청한 페이지로 이동시킴
            // 별도로 요청한 페이지가 없다면 정상적으로 myPage로 이동시킴. (redirect:/member/mypage)
            return "redirect:" + redirectURI;
        } else {
            return "/memberPages/login";
        }
    }

    @GetMapping("/mypage")
    public String myPage(){
        return "memberPages/main";
    }



    @GetMapping
    public String list(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "/memberPages/detail";
        } catch (NoSuchElementException e) {
            return "/memberPages/NotFound";
        }
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO,
                         Model model) {
        memberService.update(memberDTO);
        MemberDTO memberDTO1 = memberService.findById(memberDTO.getId());
        model.addAttribute("member", memberDTO1);
        return "/memberPages/update";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }

    @PostMapping("/dup-check")
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable("id") Long id) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO, HttpSession session) {
        memberService.update(memberDTO);
        session.removeAttribute("loginEmail");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}











