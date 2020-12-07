package com.osslab.vo.list;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Repository
@Data
public class ListVO {
	
			//@Autowired
			private int idx  ; 
			private String  title ;
			private String  content ;
			private String writer;
			private int view_cnt;
			private Date insert_time;
			private Date update_time;
			
//			public int getIdx() {
//				return idx;
//			}
//			public void setIdx(int idx) {
//				this.idx = idx;
//			}
//			public String getTitle() {
//				return title;
//			}
//			public void setTitle(String title) {
//				this.title = title;
//			}
//			public String getContent() {
//				return content;
//			}
//			public void setContent(String content) {
//				this.content = content;
//			}
//			public String getWriter() {
//				return writer;
//			}
//			public void setWriter(String writer) {
//				this.writer = writer;
//			}
//			public int getView_cnt() {
//				return view_cnt;
//			}
//			public void setView_cnt(int view_cnt) {
//				this.view_cnt = view_cnt;
//			}
//			public Date getInsert_time() {
//				return insert_time;
//			}
//			public void setInsert_time(Date insert_time) {
//				this.insert_time = insert_time;
//			}
//			public Date getUpdate_time() {
//				return update_time;
//			}
//			public void setUpdate_time(Date update_time) {
//				this.update_time = update_time;
//			}

			
			
			
			
			
			
	}

