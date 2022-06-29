package kr.or.ddit.controller.view;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteria;
import com.jsp.command.SearchCriteriaCommand;
import com.jsp.controller.FileDownloadResolver;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.MakeFileName;
import com.jsp.controller.XSSMultipartHttpServletRequestParser;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

import kr.or.ddit.command.PdsModifyCommand;
import kr.or.ddit.command.PdsRegistCommand;
import kr.or.ddit.controller.GetAttachsByMultipartFileAdapter;

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
		public String list(SearchCriteria cri, Model model )throws Exception{
			String url ="/pds/list";
			
			Map<String, Object> dataMap = pdsService.getList(cri);
			model.addAttribute("dataMap", dataMap);
			
			return url;
		}
		@RequestMapping("/registForm")
		public String registForm() throws Exception{
			String url = "pds/regist";
			return url;
		}
	
		@RequestMapping("/detail")
		public String detail(int pno, @RequestParam(defaultValue="") String from, Model model) throws Exception{
			
			String url = "/pds/detail";
			PdsVO pds = null;
			
			if(from != null && from.equals("list")) {
				pds = pdsService.read(pno);
				url = "redirect:/pds/detail.do?pno="+pno;
			}else {
				pds = pdsService.getPds(pno);
			}
			
			if(pds != null) {
				List<AttachVO> attachList = pds.getAttachList();
				if(attachList != null) {
					for(AttachVO attach : attachList) {
						String fileName = attach.getFileName().split("\\$\\$")[1];
						attach.setFileName(fileName);
					}
				}
			}
			model.addAttribute("pds", pds);
			return url;
		}
	   @RequestMapping("/getFile")
	   public String getFile(int ano,Model model) throws Exception{
		String url = "downloadFile";
		
		AttachVO attach = pdsService.getAttachByAno(ano);
	
		model.addAttribute("savedPath", attach.getUploadPath());
		model.addAttribute("fileName", attach.getFileName());
		
		
		return url;
	   }
	   
	   @RequestMapping("/modifyForm")
	   public String modifyForm(int pno, @RequestParam(defaultValue="") String from, Model model  )throws Exception{
		   String url = "/pds/modify";
		   PdsVO pds = pdsService.getPds(pno);
		   
		   //파일명 재정의
		   if(pds != null) {
				List<AttachVO> attachList = pds.getAttachList();
				if(attachList != null) {
					for(AttachVO attach : attachList) {
						String fileName = attach.getFileName().split("\\$\\$")[1];
						attach.setFileName(fileName);
					}
				}
			}
			model.addAttribute("pds", pds);
			return url;
	   }
	   @RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	   public String regist(PdsRegistCommand registReq,HttpServletRequest request, RedirectAttributes rttr)throws Exception{
			String url = "redirect:/pds/list.do";
			
			String savePath = this.fileUploadPath;
			
			List<AttachVO> attachList = GetAttachsByMultipartFileAdapter.save(registReq.getUploadFile(), savePath);
			
			PdsVO pds = registReq.toPdsVO();
			pds.setAttachList(attachList);
			pds.setTitle((String)request.getAttribute("XSStitle"));
			pdsService.regist(pds);
			
			rttr.addFlashAttribute("from", "regist");
			
			return url;
	   }
	   
	   @RequestMapping("/modify")
	   public String modifyPOST(PdsModifyCommand modifyReq, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		 String url = "redirect:/pds/detail.do";
		 
		 if(modifyReq.getDeleteFile() != null && modifyReq.getDeleteFile().length>0 ) {
			 for(String anoStr : modifyReq.getDeleteFile()) {
				 int ano = Integer.parseInt(anoStr);
				 AttachVO attach = pdsService.getAttachByAno(ano);
				 
				 File deleteFile = new File(attach.getUploadPath(), attach.getFileName());
				 
				 if(deleteFile.exists()) {
					 deleteFile.delete();
				 }
				 pdsService.removeAttachByAno(ano);
			 }
		 }
		 List<AttachVO> attachList = GetAttachsByMultipartFileAdapter.save(modifyReq.getUploadFile(), fileUploadPath);
		 
		 PdsVO pds = modifyReq.toPdsVO();
		 pds.setAttachList(attachList);
		 pds.setTitle((String)request.getAttribute("XSStitle"));
		 
		 pdsService.modify(pds);
		 
		 rttr.addFlashAttribute("from", "modify");
		 rttr.addAttribute("pno", pds.getPno());
		 return url;
		   
	   }
	   @RequestMapping("/remove")
	   public String remove(int pno, RedirectAttributes rttr)throws Exception{
		   String url = "redirect:/pds/detail.do";
		   
		   List<AttachVO> attachList = pdsService.getPds(pno).getAttachList();
		   if(attachList != null) {
			   for(AttachVO attach : attachList) {
				   File target = new File(attach.getUploadPath(), attach.getFileName());
				   if(target.exists()) {
					   target.delete();
				   }
			   }
		   }
		   return url;
	   }
	   
}
	   

