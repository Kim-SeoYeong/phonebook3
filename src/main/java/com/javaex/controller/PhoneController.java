package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

		//return "/WEB-INF/views/list.jsp";
		return "list";
	}
	
	//등록폼
	@RequestMapping(value="/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		return "writeForm";	//포워드
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
		
		return "modifyForm";	//포워드
	}
	
	//수정	--> modify2
	//modify2--> @RequestParam을 사용했을 경우
	@RequestMapping(value="/modify2", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify2(@RequestParam("name") String name, @RequestParam("hp") String hp, 
						@RequestParam("company") String company, @RequestParam("personId") int personId) {
		System.out.println("modify");
		
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		
		//phoneDao --> personUpdate();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";	//redirect 해줌.
	}
	
	//수정	--> modify	--> @ModelAttribute를 이용한것.
	//modify--> @RequestParam을 사용했을 경우
	@RequestMapping(value="/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute PersonVo personVo) {	//@ModelAttribure 자료형 모델명
		System.out.println("modify");
		
		//이게 필요가 없어짐.
		//PersonVo personVo = new PersonVo(personId, name, hp, company);
		//modifyForm의 코드id 부분 name을 personVo 변수이름과 맞추어야함..name=어쩌고 hp=010 company=02222 personId=3 이런식으로 넘거야함.
		System.out.println(personVo.toString());
		
		//phoneDao --> personUpdate();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";	//redirect 해줌.
	}
	
	//삭제	--> delete --> @RequestMapping 약식 표현
	@RequestMapping(value="/delete2", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete2(@RequestParam("personId") int personId) {
		System.out.println("delete");
		
		//PhoneDao --> personDelete();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
	//삭제	--> delete	--> 
	@RequestMapping(value="/delete/{personId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@PathVariable("personId") int personId) {	//주소에서 어떤 값(personId)을 꺼내서 personId에 담아줘.
		System.out.println("delete");
		
		//PhoneDao --> personDelete();
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
}
