package kr.or.ddit.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.dto.MemberVO;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private String savePath="c:\\log";
	private String saveFileName ="login_user_log.csv";
	
	
	
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
   
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		User user = (User)authentication.getDetails();
		// session저장
		MemberVO loginUser  = user.getMemberVO();
		HttpSession session =  request.getSession();
		session.setAttribute("loginUser", loginUser);
		session.setMaxInactiveInterval(60*6);
		
		//log작성
		LoginUserlogFile(loginUser, request);
		//화면저장
		super.onAuthenticationSuccess(request, response, authentication);
		
	}
	
	private void LoginUserlogFile(MemberVO loginUser, HttpServletRequest request)throws IOException{
		String tag = "[login:user]";
		  String log= tag
				  + loginUser.getId()+","
				  + loginUser.getPhone()+","
				  + loginUser.getEmail()+","
				  + request.getRemoteAddr()+","
				  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		  File file = new File(savePath);
	
		  file.mkdirs();
		  
		  String logFilePath = savePath+File.separator+saveFileName;
		  System.out.println(logFilePath);
		  BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath,true));
		  
		  out.write(log);
		  out.newLine();
		  
		  out.close();
		
	}

}
