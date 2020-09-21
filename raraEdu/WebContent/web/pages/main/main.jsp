<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>RARA Education</title>

	<!--1.classic -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs/theme-classic/resources/theme-classic-all.css"/>
	<!--2.black 테마-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-aria/build/resources/ext-theme-aria-all.css"/> -->
	<!--3.Crisp테마 깔끔함-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-crisp/build/resources/ext-theme-crisp-all.css"/> -->
	<!--4.Crisp터치테마 깔끔하며 Crisp테마보다 사이즈가 조금씩 더 큼(모바일용?)-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-crisp-touch/build/resources/ext-theme-crisp-touch-all.css"/> -->
	<!--5.Gray 회색테마-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-gray/build/resources/ext-theme-gray-all.css"/> -->
	<!--6.Neptune테마 (Facebook?Window8과 비슷)-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-neptune/build/resources/ext-theme-neptune-all.css"/> -->
	<!--7.Neptune터치테마 (Facebook?Window8과 비슷) 모바일용?-->
	<!-- <link rel="stylesheet" type="text/css" href="/extjs/ext-theme-neptune-touch/build/resources/ext-theme-neptune-touch-all.css"/> -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/extjs/ext-all-debug.js"></script>
		
	<!--1.classic 테마-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/extjs/theme-classic/theme-classic.js"></script>
	<!--2.black 테마-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-aria/build/ext-theme-aria.js"></script> -->
	<!--3.ExtJS5부터 추가된 Crisp테마 깔끔함-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-crisp/build/ext-theme-crisp.js"></script> -->
	<!--4.Crisp터치테마 깔끔하며 Crisp테마보다 사이즈가 조금씩 더 큼 모바일용 추천테마-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-crisp-touch/build/ext-theme-crisp-touch.js"></script> -->
	<!--5.Gray 회색테마-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-gray/build/ext-theme-gray.js"></script> -->
	<!--6.Neptune테마 (Facebook?Window8과 비슷)-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-neptune/build/ext-theme-neptune.js"></script> -->
	<!--7.Neptune터치테마 (Facebook?Window8과 비슷) 모바일용 추천테마-->
	<!-- <script type="text/javascript" src="/extjs/ext-theme-neptune-touch/build/ext-theme-neptune-touch.js"></script> -->
 
	<%--
	<!-- ExtJs Css -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext/resources/css/ext-all.css">
	<!-- 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/frameset/script/css/combos.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/pages/view/web-viewer.css" />
	 -->
	 
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext/ext-all.js"></script>
	<!-- 
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/frameset/script/js/cmn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/pages/cmn/tgcscmn.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/pages/cmn/Exporter-all.js"></script>
	 -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext/examples/shared/examples.css" />
		 --%>
		
<script type="text/javascript" >

Ext.onReady(function() {
	Ext.QuickTips.init();
	
    var viewport = new Ext.Viewport({
    	/*
        layout:'fit',
        items:[
            {
                title:'Fit Panel Example',
		html:'<div style="text-align:center;padding:100px;"></div>',
                collapsible: true
            }
        ]
        */
        layout: 'border',
	    items: [{
	        region: 'north',
	        html: '<h1 class="x-panel-header">RaRa Education</h1>',
	        border: false,
	        margin: '0 0 5 0'
	    }, {
	        region: 'west',
	        collapsible: true,
	        title: '메뉴',
	        width: 200
	        // could use a TreePanel or AccordionLayout for navigational items
	    }, {
	        region: 'south',
	        title: '교육소 정보',
	        collapsible: true,
	        html: 'Information goes here',
	        split: true,
	        height: 100,
	        minHeight: 100
	    }, {
	        region: 'east',
	        title: '검색',
	        collapsible: true,
	        split: true,
	        width: 150
	    }, {
	        region: 'center',
	        xtype: 'tabpanel', // TabPanel itself has no title
	        activeTab: 0,      // First tab active by default
	        items: [{
	            title: 'Default Tab',
	            html: 'The first tab\'s content. Others may be added dynamically'
	        },
	        {
	            title: 'Default 2 Tab',
	            html: 'The first tab\'s content. Others may be added dynamically'
	        }]
	    }]
    });

    this.msg = function(title, msg){
        Ext.Msg.show({
            title: title,
            msg: msg,
            minWidth: 200,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    };
});
</script>

</head>
<body>

<div id="conditions"><div style="float:right;margin:5px;" class="x-small-editor"></div></div>
<div id="body" style="display:none;"></div>
TEST
</body>
</html>