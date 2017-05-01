/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .controller("SearchController", SearchController);

function SearchController(SearchDataFactory, PopulateDataFactory) {

    var vm = this;
    vm.searchTerms = undefined;
    vm.showTable = false;
    vm.currentPage = 1;
    vm.itemsPerPage = 8;
    vm.displayOptions = false;
    vm.selected = undefined;

    vm.searchData = function () {

        SearchDataFactory.list({action: 'searchData', value: vm.searchTerms}, function (response) {
            vm.results = response;
            vm.showTable = true;
            vm.totalItems = vm.results.length;
        })
    };

    vm.changeOptions = function () {
        if (vm.displayOptions === true) {
            vm.displayOptions = false;
        } else if (vm.displayOptions === false) {
            vm.displayOptions = true;
        }
    };

    PopulateDataFactory.list({action: 'getBBCMediaType'}, function (response) {
        vm.mediaTypes = response
    });

    PopulateDataFactory.list({action: 'getBBCServices'}, function (response) {
        vm.services = response
    });

}