/**
 * Created by Kulsoom on 08/05/2017.
 */
angular.module("lsdda.detail")
    .controller("DetailController", ["DetailServiceFactory", "$routeParams", "$location", DetailController]);

function DetailController(DetailServiceFactory, $routeParams, $location) {
    var vm = this;

    DetailServiceFactory.show({action: 'detailedProgramme', pid: $routeParams.pid}, function (response) {
        vm.result = response;
        vm.hello = "Hello World"
    });
}