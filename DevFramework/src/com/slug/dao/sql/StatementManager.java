package com.slug.dao.sql;


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.slug.config.AppConfigManager;
import com.slug.exception.PException;
import com.slug.key.JFoundKey;
import com.slug.lifecycle.Lifecycle;
import com.slug.lifecycle.LifecycleException;
import com.slug.lifecycle.LifecycleListener;
import com.slug.lifecycle.LifecycleSupport;
import com.slug.logging.Logging;
import com.slug.util.StringUtil;

public class StatementManager implements Lifecycle{

    private static final String StatementVO = null;
	private String APP_PATH = JFoundKey.WebApplicationPath;


	/**
     * StatementManger Singleton Instance
     */
	private static StatementManager instance = new StatementManager();

    /**
     * 
     */
	private LifecycleSupport lifecycle = new LifecycleSupport(this);
    
	/**
	 * 
	 */
	private boolean started;
	
	private static HashMap<String,StatementVO> statements;
	
	private StatementManager(){
		this.statements = new HashMap<String,StatementVO>();
	}

	/**
	 * StatementManager Instance를 반환하는 Singleton getter Method
	 * @return
	 */
	public static StatementManager getInstance(){
		return instance;
	}
    /**
     * 
     */
    public void start(String prop_file) throws LifecycleException{
        if (started)
            throw new LifecycleException("RECEAICSM201");

        Logging.sys.println("< StatementManager > < start >");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTING_EVENT, this);
        
        try{
	        Document doc[] = getStatementXmlDoc();
	        
	        for(int i =0;i<doc.length;i++){
	            StatementVO stVO = new StatementVO();
	        	
		        Element root = doc[i].getRootElement();
		        Element sqlElement;
		        List sql = root.getChildren("sql");
		        
		        String module_id = "";
		        String sql_id     = "";
		        String query_str  = "";
		        String rtn_type  = "";
		        String desc  = "";
		        
		        
		        module_id = root.getAttribute("module").getValue();
	
		        for (int x = 0; x < sql.size(); x++) 
		        {
		          sqlElement = (Element) sql.get(x);
		          sql_id        = sqlElement.getAttribute("id").getValue();
	        	  query_str   = sqlElement.getChild("query").getText();
		          rtn_type    = sqlElement.getAttribute("type").getValue();
	        	  desc   = sqlElement.getChild("description").getText();

	        	  Logging.sys.println("< StatementManager > < start > ******************************************************");
	        	  Logging.sys.println("< StatementManager > < start > MODULE ID  : "+module_id);
	        	  Logging.sys.println("< StatementManager > < start > SQL ID     : "+sql_id);
	        	  Logging.sys.println("< StatementManager > < start > RETURN TYPE: "+rtn_type);
	        	  Logging.sys.println("< StatementManager > < start > Describes  : "+desc);
	        	  Logging.sys.println("< StatementManager > < start > Query      : "+ query_str);
	        	  
	        	  StatementItemVO itemVo = new StatementItemVO(sql_id,rtn_type,query_str,desc);
	        	  stVO.addStatementItemVO(itemVo);
	        	  
	        	  
		        }
		        statements.put(module_id, stVO);
	        }
	        
	        
	        
        }catch(PException pe){
        
        	pe.printStackTrace();
        }

        started = true;
        
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTED_EVENT, this);
    }
    /**
     * 
     */
    public void stop() throws LifecycleException {
        // Validate and update our current component state
        if (!started)
            throw new LifecycleException("RECEAICSM203");
		
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPING_EVENT, this);
        
        this.statements = null;
                
        started = false;
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPPED_EVENT, this);
    }
    
    
    /**
     * 
     * @return
     * @throws PException
     */
    public Document[] getStatementXmlDoc() throws PException{
        
    	AppConfigManager conf = AppConfigManager.getInstance();
    	//String R_PATH = this.APP_PATH;
    	String sql_path = this.APP_PATH+conf.getString("webApp.sql.default.dir");
    	String xml_name = "";
       
    	SAXBuilder builder;
        File[] xml = getStatementXml();
        int xml_cnt = xml.length;
        
        Document doc[] = new Document[xml_cnt];
        
        try {
    		builder = new SAXBuilder();
    		
    		for(int i =0 ; i < xml_cnt;i++){
    			xml_name = xml[i].getName();
    			if(xml_name.endsWith(".xml"))
    				doc[i] = builder.build(new File(sql_path+xml[i].getName()));
        	}
        
        }catch(JDOMException je) {
        	throw new PException(je);
        }catch(Exception e) {
        	throw new PException(e);
        }
        return doc;
    }
    
    
    /**
     * 
     * @return
     */
    public File[] getStatementXml(){
    	
    	File curDir = new File(this.APP_PATH+"/WEB-INF/sql");
    	Logging.dev.println("< Statementmanager > getStatementXml Path:"+curDir);
    	
    	File files[] = curDir.listFiles(new FilenameFilter() {
    	    public boolean accept(File directory, String fileName) {
    	        return fileName.endsWith(".xml");
    	    }
    	});
    	String currPwd = curDir.getAbsolutePath();    	
    	return files;
    	
    }
    
    public static String getStatement(String module_id, String sql_id){
    	
        Logging.debug.println("< StatementManager >< getStatement() > SQL Module ID:"+module_id+", SQL ID:"+sql_id);

    	StatementItemVO itemVO = getStatementItemVOs(module_id,sql_id);
    	return itemVO.statement;
    }
    
    private static StatementItemVO getStatementItemVOs(String module_id, String sql_id){
    	StatementVO statVO = (StatementVO)(statements.get(module_id));
    	
    	Logging.debug.println("< StatementManager >< getStatementItemVOs() > SQL Module ID:"+module_id+", SQL ID:"+sql_id);
    	Logging.debug.println("< StatementManager >< getStatementItemVOs() > getStatementItemVO:"+statVO.getStatementItemVO(sql_id).toString());
    	
    	return statVO.getStatementItemVO(sql_id);
    }
    /**
     * 
     */
    public void addLifecycleListener(LifecycleListener listener){
        lifecycle.addLifecycleListener(listener);
    }
    public LifecycleListener[] findLifecycleListeners(){
        return lifecycle.findLifecycleListeners();
    }
    /**
     * 
     */
    public void removeLifecycleListener(LifecycleListener listener){
        lifecycle.removeLifecycleListener(listener);
    }
    /**
     * 
     */
    public boolean isStarted(){
        return this.started;   
    }



}
