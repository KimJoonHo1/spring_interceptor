package com.care.root.member.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
			@RequestParam(required = false) String autoLogin,
			RedirectAttributes rs) {
		int result = ms.userCheck(id, pw);
		if(result == 0) {
			rs.addAttribute("id", id);
			rs.addAttribute("autoLogin", autoLogin);
			return "redirect:successLogin";
		} else {
			return "redirect:Login";
		}
	}
	@GetMapping("member/successLogin")
	public String successLogin(@RequestParam String id,
			@RequestParam(required = false) String autoLogin,
			HttpSession session,
			HttpServletResponse resp) {
		session.setAttribute(LOGIN, id);
		if(autoLogin != null) {
			int limitTime = 60 * 60 * 24 * 90; //90일
			Cookie loginCookie = new Cookie("loginCookie", session.getId());
			loginCookie.setPath("/");
			loginCookie.setMaxAge(limitTime);
			resp.addCookie(loginCookie);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, 3);
			
			java.sql.Date limitDate = new java.sql.Date(cal.getTimeInMillis());
			ms.keepLogin(session.getId(), limitDate, id);
		}
		return "member/successLogin";
	}
	
	@GetMapping("member/Logout")
	public String logout(HttpSession session, HttpServletResponse resp,
			@CookieValue(value="loginCookie", required = false) Cookie loginCookie) {
		if(session.getAttribute(LOGIN) != null) {
			if(loginCookie != null) {
				System.out.println("das");
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				loginCookie.setValue(null);
				resp.addCookie(loginCookie);
				ms.keepLogin("nan", new java.sql.Date(0), (String) session.getAttribute(LOGIN));
			}
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
