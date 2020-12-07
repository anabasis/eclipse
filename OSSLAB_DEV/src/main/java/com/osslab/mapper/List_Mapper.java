package com.osslab.mapper;

import java.util.List;

import com.osslab.vo.list.ListVO;

public interface List_Mapper {
	   public List<ListVO> boardList() throws Exception;
	   public void insert_list(ListVO listvo) throws Exception;
}
