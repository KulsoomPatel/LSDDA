<!doctype html>
<html>
<head>
    <asset:stylesheet src="bootstrap/bootstrap.css"/>
    <asset:stylesheet src="custom.css"/>
    <asset:stylesheet src="stylesheet/loading-bar.min.css"/>
    <asset:stylesheet src="stylesheet/angular-bootstrap-calendar.css"/>
    <title>Search Programmes</title>
</head>

<body>

<div class="container" ng-app="lsdda">
    <a href="/#/">

        <div class="row headerBlock">
            <div class="col-md-12">
                <asset:image src="bbc-logo.jpg" class="img-responsive center-block"/>
            </div>

        </div>
    </a>
</br>

    <div ng-view>

    </div>

    <div id="footer">
        &nbsp;
    </div>
</div>

<asset:javascript src="jquery/jquery.js"/>
<asset:javascript src="lsdda/lsdda"/>
<asset:javascript src="angular/ui-bootstrap-tpls.js"/>
<asset:javascript src="angular/loading-bar.min.js"/>
<asset:javascript src="angular/angularjs-dropdown-multiselect.min.js"/>
<asset:javascript src="moment/moment.js"/>
<asset:javascript src="angular/angular-bootstrap-calendar-tpls.js"/>

</body>
</html>