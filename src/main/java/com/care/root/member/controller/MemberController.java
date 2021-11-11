package com.care.root.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.root.common.MemberSessionName;
import com.care.root.member.dto.MemberDTO;
import com.care.root.member.service.MemberService;

@Controller
public class MemberController implements MemberSessionName{
	@Autowired MemberService ms;
	
	@GetMapping("member/Login")
	public String login() {
		return "member/login";
	}
	@PostMapping("member/user_check")
	public String userCheck(@RequestParam String id, @RequestParam String pw,
			RedirectAttributes rs) {
		int result = ms.userCheck(id, pw);
		if(result == 0) {
			rs.addAttribute("id", id);
			return "redirect:successLogin";
		} else {
			return "redirect:Login";
		}
	}
	@GetMapping("member/successLogin")
	public String successLogin(@RequestParam String id, HttpSession session) {
		session.setAttribute(LOGIN, id);
		return "member/successLogin";
	}
	
	@GetMapping("member/Logout")
	public String logout(HttpSession session) {
		if(session.getAttribute(LOGIN) != null) {
			session.invalidate();
		}
		return "redirect:/index";
	}
	
	@GetMapping("/member/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		//if(session.getAttribute(LOGIN) != null) {
			ms.memberInfo(model);
			return "/member/memberInfo";
		//}
		//return "redirect:Login";
	}
	
	@GetMapping("/member/info")
	public String info(@RequestParam String id, Model model) {
		ms.info(model, id);
		return "member/info";
	}

	@GetMapping("/member/register_form")
	public String register_form() {
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String register(MemberDTO dto) {
		int result = ms.register(dto);
		if(result == 1) {
			return "redirect:Login";
		}
		return "redirect:register_form";
	}
}
