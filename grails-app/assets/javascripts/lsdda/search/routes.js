/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: "/lsdda/search/searchBar.html",
                controller: "SearchController as ctrl"
            })
    });