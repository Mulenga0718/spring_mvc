package kr.or.ddit.controller.view;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.dto.MemberVO;
import com.jsp.dto.MenuVO;
import com.jsp.exception.InvalidPasswordException;
import com.jsp.exception.NotFoundIdException;
import com.jsp.service.LoginSearchMemberService;
import com.jsp.service.MenuService;

import kr.or.ddit.command.LoginCommand;

@Controller
public class CommonController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private LoginSearchMemberService loginSearchMemberService;
	
	
	@RequestMapping("/home")
	public String home() {
		String url = "home";
		return url;
	}
	
	@RequestMapping("/index")
	public String indexPage(@RequestParam(defaultValue="M000000")String mCode, Model model) throws SQLException {
		String url = "/common/indexPage";
		
		
			List<MenuVO> menuList = menuService.getMainMenuList();
			model.addAttribute("menuList", menuList);
			
			MenuVO menu = menuService.getMenuByMcode(mCode);
			model.addAttribute("menu",menu);
			
		return url;
	}
	
	@RequestMapping("/security/accessDenied")
	public void accessDenied() {}

	@RequestMapping("/common/loginTimeOut")
	public String loginTimeOut(Model model)throws Exception{
		String url = "security/sessionOut";
		
		model.addAttribute("message","세션이 만료되었습니다. \\n 다시 로그인 하세요!");
		return url;
	}
	/*
	 * @RequestMapping("/common/login") public String login(String id, String pwd,
	 * HttpSession session) throws Exception { String url = "redirect:/index.do";
	 * 
	 * try { loginSearchMemberService.login(id, pwd);
	 * session.setAttribute("loginUser", loginSearchMemberService.getMember(id));
	 * session.setMaxInactiveInterval(6*60); } catch (NotFoundIdException |
	 * InvalidPasswordException e) { //model.addAttribute("message",
	 * e.getMessage()); url = "/common/login_fail"; }catch(Exception e) {
	 * e.printStackTrace(); throw e; }
	 * 
	 * 
	 * return url; }
	 */
	@RequestMapping(value="/common/loginForm", method = RequestMethod.GET)
	public String loginForm(@RequestParam(defaultValue="")String error, HttpServletResponse response ) {
		String url = "/common/loginForm";
		
		if(error != null && error.equals("-1")) {
			response.setStatus(302);
		}
		return url;
	}

	/*
	 * @RequestMapping(value="/common/logout",method = RequestMethod.GET) public
	 * String logout(HttpSession session) { String url = "redirect:";
	 * 
	 * session.invalidate();
	 * 
	 * return url;
	 * 
	 * }
	 */
	
}
