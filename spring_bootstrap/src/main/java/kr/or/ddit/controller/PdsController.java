package kr.or.ddit.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteriaCommand;
import com.jsp.controller.FileDownloadResolver;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.MakeFileName;
import com.jsp.controller.XSSMultipartHttpServletRequestParser;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	private PdsService pdsService;
	
	@Resource(name = "fileUploadPath")
	private String fileUploadPath;
	
	
		@RequestMapping("/main")
		public String main() {
			String url ="/pds/main";
			return url;
		}
		@RequestMapping("/list")
		public String list(SearchCriteriaCommand criCMD, Model model )throws Exception{
			String url ="/pds/list";
			
			
			Criteria cri = criCMD.toSearchCriteria();
			Map<String, Object> dataMap = pdsService.getList(cri);
			model.addAttribute("dataMap", dataMap);
			
			return url;
		}
	
		@RequestMapping("/detail")
		public String detail(int pno, @RequestParam(defaultValue="") String from, Model model) throws Exception{
			
			String url = "/pds/detail";
			PdsVO pds = null;
			
			if(!from.equals("list")) {
				pds = pdsService.getPds(pno);
			}else {
				pds = pdsService.read(pno);
			}
			
			List<AttachVO> renamedAttachList=
					MakeFileName.parseFileNameFromAttaches(pds.getAttachList(), "\\$\\$");
			pds.setAttachList(renamedAttachList);
			
			model.addAttribute("pds", pds);
			
			return url;
		}
	   @RequestMapping("/getFile")
	   public String getFile(int ano,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String url = null;
		AttachVO attach = pdsService.getAttachByAno(ano);
		String fileName = attach.getFileName();
		String savedPath = attach.getUploadPath();
		
		FileDownloadResolver.sendFile(fileName, savedPath, request, response);
		
		return url;
	   }
	   
	   @RequestMapping("/modify")
	   public String modify()throws Exception{
		   String url = "";
		   return url ;
	   }
	   
	   @RequestMapping("/modifyForm")
	   public String modifyForm(int pno, @RequestParam(defaultValue="") String from, Model model  )throws Exception{
		   String url = "/pds/modify";
		   PdsVO pds = null;
		   if(!from.equals("list")) {
			   pds = pdsService.getPds(pno);			
		   }else {
			   pds = pdsService.read(pno);
			   url = "redirect:/pds/detail.do?pno="+pno;
		   }
		   List<AttachVO> renamedAttachList=
					MakeFileName.parseFileNameFromAttaches(pds.getAttachList(), "\\$\\$");
			pds.setAttachList(renamedAttachList);
			model.addAttribute("pds", pds);
			
			return url;
	   }
	   @RequestMapping("/regist")
	   public String regist(MultipartFile multi)throws Exception{
			String url = "redirect:/pds/list";
			List<AttachVO> attachList = null;
			
			//파일 처리
			//실제 저장 경로를 설정 

			//파일 저장후 List<File>를 리턴..
			List<File> fileList =  null; 
			/*	= FileUploadResolver.fileUpload(multi.("uploadFile"), fileUploadPath);
			*/
			//List<File> -> List<AttachVO>
			if(fileList != null) {
				attachList = new ArrayList<AttachVO>();
				for(File file : fileList) {
					AttachVO attach = new AttachVO();
					
					attach.setFileName(file.getName());
					attach.setUploadPath(fileUploadPath);
					attach.setFileType(file.getName().substring(file.getName().lastIndexOf(".")+1));
					
					attachList.add(attach);
				}
			}
			return url;
	   }
}
	   

