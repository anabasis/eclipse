package com.slug.dao.sql;


import com.slug.vo.entity.JFoundEntity;


public class StatementItemVO extends JFoundEntity

{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4061313151254609249L;
	
	//public String module              ;
    public String sql_id                ;
    public String type                  ;
    public String statement           ;
    public String desc                  ;

    public StatementItemVO manager = null;
    
    public StatementItemVO(String sql_id, String type, String statement, String desc){
    	//this.module    = module;
    	this.sql_id       = sql_id;
    	this.type        = type;
    	this.statement = statement;
    	this.desc        = desc;
    }

	@Override
	public String toString() {
		return "StatementItemVO [sql_id=" + sql_id + ", type=" + type
				+ ", statement=" + statement + ", desc=" + desc + ", manager="
				+ manager + "]";
	}
        
}