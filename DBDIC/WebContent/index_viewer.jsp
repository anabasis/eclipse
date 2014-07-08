<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Feed Viewer</title>
    <link rel="stylesheet" type="text/css" href="/DBDIC/web/css/viewer/Feed-Viewer.css">

    <!-- GC -->

<style type="text/css">
.x-menu-item div.preview-right, .preview-right {
    background-image: url(/DBDIC/web/images/viewer/preview-right.gif);
}
.x-menu-item div.preview-bottom, .preview-bottom {
    background-image: url(/DBDIC/web/images/viewer/preview-bottom.gif);
}
.x-menu-item div.preview-hide, .preview-hide {
    background-image: url(/DBDIC/web/images/viewer/preview-hide.gif);
}

#reading-menu .x-menu-item-checked {
    border: 1px dotted #a3bae9 !important;
    background: #DFE8F6;
    padding: 0;
    margin: 0;
}
</style>
    <script type="text/javascript" src="/DBDIC/extjs/examples/shared/include-ext.js"></script>
    <script type="text/javascript" src="/DBDIC/extjs/examples/shared/options-toolbar.js"></script>
    <script type="text/javascript">
        function hasOption (name) {
            return window.location.search.indexOf(name) >= 0;
        }

        Ext.Loader.setConfig({enabled: true});
        Ext.Loader.setPath('Ext.ux', '/DBDIC/extjs/examples/ux');
        Ext.require([
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.Action',
            'Ext.tab.*',
            'Ext.button.*',
            'Ext.form.*',
            'Ext.layout.container.Card',
            'Ext.layout.container.Border',
            'Ext.ux.ajax.SimManager',
            'Ext.ux.PreviewPlugin'
        ]);
        Ext.onReady(function(){
            if (hasOption('simjax')) {
                initAjaxSim();
            }

            var app = new FeedViewer.App();
        });
    </script>

    <script type="text/javascript" src="/DBDIC/web/viewer/FeedPost.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedDetail.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedGrid.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedInfo.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedPanel.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedViewer.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/FeedWindow.js"></script>
    <script type="text/javascript" src="/DBDIC/web/viewer/Sim.js"></script>
</head>
<body>
</body>
</html>