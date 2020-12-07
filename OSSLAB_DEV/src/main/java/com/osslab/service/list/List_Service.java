package com.osslab.service.list;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osslab.mapper.List_Mapper;
import com.osslab.vo.list.ListVO;

@Service
public class List_Service {
	@Autowired
	public List_Mapper mapper;

	public void insert_list_data(ListVO listvo) throws Exception {
		mapper.insert_list(listvo);
	}

	public List<ListVO> boardListService() throws Exception {
		return mapper.boardList();
	}

}
