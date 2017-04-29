/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .controller("SearchController", SearchController);


function SearchController(SearchDataFactory) {

    var vm = this;
    vm.searchTerms = undefined;
    vm.showTable = false;


    vm.searchData = function () {

        SearchDataFactory.list({action: 'searchData', value: vm.searchTerms}, function (response) {
            vm.results = response;
            vm.showTable = true;
        })
    }
}