package com.osslab.controller.list;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.osslab.mapper.List_Mapper;
import com.osslab.service.list.List_Service;
import com.osslab.vo.list.ListVO;

@Controller
@EnableAutoConfiguration
@Component
public class List_Controller {

//	
	// private List_Mapper mapper;
	@Autowired
	private List_Service list_service;

	@RequestMapping(value = "/")
	@ResponseBody
	public String select_list(Model model) throws Exception {
		model.addAttribute("list", list_service.boardListService());

		return "list/board_list.html";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String go_insert_list() throws Exception {
		return "list/insert.html";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert_list_OK(HttpServletRequest request) throws Exception {
		ListVO listvo = new ListVO();
		listvo.setTitle(request.getParameter("title"));
		listvo.setContent(request.getParameter("content"));
		listvo.setWriter(request.getParameter("writer"));
		// mapper.insert_list(listvo);
		list_service.insert_list_data(listvo);

		return "list/insert.html";
	}

}
