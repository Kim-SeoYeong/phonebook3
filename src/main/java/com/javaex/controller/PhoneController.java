package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value="/phone")
public class PhoneController {
	//필드
	//생성자
	//메소드 g/s
	
	/***** *메소드 일반* 메소드마다  기능 1개씩  --> 기능마다 url 부여 *****/
	
	//리스트
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		//dao를 통해 리스트를 가져온다.
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPersonList();
		
		//model --> data를 보내는 방법 --> 담아 놓으면 된다.
		//이전 request.setAttribure("pList", personList)를 표현
		model.addAttribute("pList", personList);

		return "/WEB-INF/views/list.jsp";
	}
	
	//등록폼
	@RequestMapping(value="/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		return "/WEB-INF/views/writeForm.jsp";	//포워드
	}
	
	//http://localhost:8088/phonebook3/phone/write?name=김서영&hp=010-1111-1111&company=02-1111-1111
	//등록
	@RequestMapping(value="/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name, @RequestParam("hp") String hp, @RequestParam("company") String company) {
		//request.getParameter("name")을 @RequestParam("name")으로 표현하고 담을 변수를 옆에 적어줌.
		System.out.println("write");
		
		PersonVo personVo = new PersonVo(name, hp, company);

		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";	//인터넷 주소를 다시 숨어서 보내는 것  redirect 해주는부분.
	}
	
	//수정폼	--> modifyForm
	@RequestMapping(value="/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("id") int personId) {
		System.out.println("modifyForm");
		
		PhoneDao phoneDao = new PhoneDao();
		PersonVo personVo = phoneDao.getPerson(personId);
		
		//personVo를 보내기 위해 model에 담아줌.
		model.addAttribute("pvo", personVo);
		
		return "/WEB-INF/views/modifyForm.jsp";	//포워드
	}
	
	//수정	--> modify
	@RequestMapping(value="/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@RequestParam("name") String name, @RequestParam("hp") String hp, 
						@RequestParam("company") String company, @RequestParam("id") int personId) {
		System.out.println("modify");
		
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		
		//phoneDao --> personUpdate();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";	//redirect 해줌.
	}
	
	//삭제	--> delete
	@RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("id") int personId) {
		System.out.println("delete");
		
		//PhoneDao --> personDelete();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
}
