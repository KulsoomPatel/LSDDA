/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .controller("SearchController", SearchController)
    .filter("offset", offset);


function offset() {
    return function (input, start) {
        return input.slice(start);
    };
}


function SearchController(SearchDataFactory) {

    var vm = this;
    vm.searchTerms = undefined;
    vm.showTable = false;
    vm.currentPage = 1;
    vm.itemsPerPage = 8;
    
    vm.searchData = function () {

        SearchDataFactory.list({action: 'searchData', value: vm.searchTerms}, function (response) {
            vm.results = response;
            vm.showTable = true;
            vm.totalItems = vm.results.length;
        })
    }
}