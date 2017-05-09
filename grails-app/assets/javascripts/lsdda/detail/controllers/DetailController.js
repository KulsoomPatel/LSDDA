/**
 * Created by Kulsoom on 08/05/2017.
 */
angular.module("lsdda.detail")
    .controller("DetailController", ["DetailServiceFactory", "$routeParams", "$location", DetailController]);

function DetailController(DetailServiceFactory, $routeParams, $location) {
    var vm = this;
    vm.clipType = undefined;

    DetailServiceFactory.show({action: 'detailedProgramme', pid: $routeParams.pid}, function (response) {
        vm.result = response;
        vm.checkClip(vm.result.is_clip)
    });


    vm.checkClip = function (clip) {
        if (clip === 1) {

            vm.clipType = "Yes";

        } else if (clip === 0) {

            vm.clipType = "No";
        }

    }
}