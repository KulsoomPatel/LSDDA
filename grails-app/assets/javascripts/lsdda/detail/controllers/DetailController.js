/**
 * Created by Kulsoom on 08/05/2017.
 */
angular.module("lsdda.detail")
    .controller("DetailController", ["DetailServiceFactory", "$routeParams", "$location", DetailController]);

function DetailController(DetailServiceFactory, $routeParams, $location) {
    var vm = this;
    vm.clipType = undefined;
    vm.currentPage = 1;
    vm.itemsPerPage = 4;
    vm.showOther = false;
    vm.resultSize = 0;

    DetailServiceFactory.show({action: 'detailedProgramme', pid: $routeParams.pid}, function (response) {
        vm.result = response;
        vm.checkClip(vm.result.is_clip);
        vm.otherProgrammes();

    });

    vm.otherProgrammes = function () {
        DetailServiceFactory.list({
            action: 'getRelatedProgrammes',
            title: vm.result.complete_title.name
        }, function (response) {
            vm.programmeResults = response;
            vm.totalItems = vm.programmeResults.length;

            if (vm.totalItems > 1) {

                vm.showOther = true;
            }
        });
    };

    vm.checkClip = function (clip) {
        if (clip === 1) {

            vm.clipType = "Yes";

        } else if (clip === 0) {

            vm.clipType = "No";
        }

    };

    vm.detailedView = function (pid) {

        $location.path("/" + pid);
    };

    vm.checkProgramme = function (pid) {
        if (pid === $routeParams.pid) {
            return true;
        }
        else {
            return false;
        }
    };
}