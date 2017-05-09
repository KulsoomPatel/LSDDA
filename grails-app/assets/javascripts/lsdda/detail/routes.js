/**
 * Created by Kulsoom on 08/05/2017.
 */
angular.module("lsdda.detail")
    .config(function ($routeProvider) {
        $routeProvider
            .when('/:pid', {
                templateUrl: "/lsdda/detail/programmeDetails.html",
                controller: "DetailController as ctrl"
            })
    });