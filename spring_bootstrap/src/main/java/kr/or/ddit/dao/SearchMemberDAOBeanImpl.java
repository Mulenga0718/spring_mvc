package kr.or.ddit.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchCriteria;
import com.jsp.dao.SearchMemberDao;
import com.jsp.dto.MemberVO;

public class SearchMemberDAOBeanImpl implements SearchMemberDAOBean{
	
	private SqlSession session;
	private SearchMemberDao searchmemberDAO;
	
	public void setSearchMemberDao(SearchMemberDao searchmemberDAO) {
		this.searchmemberDAO = searchmemberDAO;
	}

	@Override
	public List<MemberVO> selectSearchMemberList(SearchCriteria cri) throws Exception {
		return searchmemberDAO.selectSearchMemberList(session, cri);
	}

	@Override
	public int selectSearchMemberListCount(SearchCriteria cri) throws Exception {
		return searchmemberDAO.selectSearchMemberListCount(session, cri);
	}
	
	
	
}
