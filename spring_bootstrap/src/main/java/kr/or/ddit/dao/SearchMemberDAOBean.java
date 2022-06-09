package kr.or.ddit.dao;

import java.util.List;


import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;

public interface SearchMemberDAOBean {
	
	List<MemberVO> selectSearchMemberList(SearchCriteria cri)throws Exception;
	int selectSearchMemberListCount(SearchCriteria cri)throws Exception;
	
}
