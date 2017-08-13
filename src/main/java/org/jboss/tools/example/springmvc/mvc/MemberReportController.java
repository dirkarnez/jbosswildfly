package org.jboss.tools.example.springmvc.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.tools.example.springmvc.domain.Member;
import org.jboss.tools.example.springmvc.repo.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/report/")
public class MemberReportController {

    @Autowired
    private MemberDao memberDao;

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	public ModelAndView generatePdfReport(ModelAndView modelAndView) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
    	List<Member> members = memberDao.findAllOrderedByName();
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(members);
		parameterMap.put("datasource", JRdataSource);
		// pdfReport bean has ben declared in the jasper-views.xml file
		return new ModelAndView("pdfReport", parameterMap);
	}

//	@RequestMapping(method = RequestMethod.GET, value = "xls")
//	public ModelAndView generateXlsReport(ModelAndView modelAndView) {
//
//		Map<String, Object> parameterMap = new HashMap<String, Object>();
//
//		List<Member> members = memberDao.findAllOrderedByName();
//
//		JRDataSource JRdataSource = new JRBeanCollectionDataSource(members);
//
//		parameterMap.put("datasource", JRdataSource);
//
//		// xlsReport bean has ben declared in the jasper-views.xml file
//		modelAndView = new ModelAndView("xlsReport", parameterMap);
//
//		return modelAndView;
//	}
//
//	@RequestMapping(method = RequestMethod.GET, value = "csv")
//	public ModelAndView generateCsvReport(ModelAndView modelAndView) {
//
//		Map<String, Object> parameterMap = new HashMap<String, Object>();
//
//		List<Member> members = memberDao.findAllOrderedByName();
//
//		JRDataSource JRdataSource = new JRBeanCollectionDataSource(members);
//
//		parameterMap.put("datasource", JRdataSource);
//
//		// xlsReport bean has ben declared in the jasper-views.xml file
//		modelAndView = new ModelAndView("csvReport", parameterMap);
//
//		return modelAndView;
//	}
//
//	@RequestMapping(method = RequestMethod.GET, value = "html")
//	public ModelAndView generateHtmlReport(ModelAndView modelAndView) {
//
//		Map<String, Object> parameterMap = new HashMap<String, Object>();
//
//		List<Member> members = memberDao.findAllOrderedByName();
//
//		JRDataSource JRdataSource = new JRBeanCollectionDataSource(members);
//
//		parameterMap.put("datasource", JRdataSource);
//
//		// xlsReport bean has ben declared in the jasper-views.xml file
//		modelAndView = new ModelAndView("htmlReport", parameterMap);
//
//		return modelAndView;
//	}
}