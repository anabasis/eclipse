<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>INDEX</title>
    <!-- GC -->

    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs/shared/include-ext.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs/shared/options-toolbar.js"></script>
    <script type="text/javascript">
    	function hasOption (name) {
            return window.location.search.indexOf(name) >= 0;
        }
        
        Ext.onReady(function(){
            

            //var app = new FeedViewer.App();
        });
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/index_ini.js"></script>
    
</head>
<body>
    <div id="body"></div>    
    <div id="footer">
        <div class="wrap">
            &copy;2014-<span id="this-year"></span> Jonathan Cho.
        </div>
    </div>
    <script>document.getElementById('this-year').innerHTML = (new Date()).getFullYear();</script>
</body>
</html>