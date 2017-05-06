/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .controller("SearchController", SearchController);

function SearchController(SearchDataFactory, PopulateDataFactory) {

    var vm = this;
    vm.searchTerms = undefined;
    vm.searchText = "Search";
    vm.optionsText = "Advanced Search";
    vm.showTable = false;
    vm.media_type = undefined;
    vm.is_clip = undefined;
    vm.service = undefined;
    vm.currentPage = 1;
    vm.itemsPerPage = 8;
    vm.displayOptions = false;
    vm.selectedCats = [];
    vm.catSettings = {
        enableSearch: true,
        template: '{{option}}',
        checkBoxes: true,
        scrollableHeight: '300px',
        scrollable: true,
        selectedToTop: true,
        selectionLimit: 4
    };

    vm.searchData = function () {

        if (vm.displayOptions === true) {

            SearchDataFactory.list({
                action: 'advancedSearch',
                is_clip: vm.is_clip,
                media_type: vm.media_type,
                service: vm.service
            }, function (response) {
                vm.results = response;
                vm.showTable = true;
                vm.totalItems = vm.results.length;
            })

        } else if (vm.displayOptions === false) {

            SearchDataFactory.list({action: 'searchData', value: vm.searchTerms}, function (response) {
                vm.results = response;
                vm.showTable = true;
                vm.totalItems = vm.results.length;
            })

        }


    };

    vm.changeOptions = function () {
        if (vm.displayOptions === true) {
            vm.displayOptions = false;
            vm.searchText = "Search";
            vm.optionsText = "Advanced Options"
        } else if (vm.displayOptions === false) {
            vm.displayOptions = true;
            vm.searchText = "Advanced Search";
            vm.optionsText = "Hide Options";
        }
    };

    PopulateDataFactory.list({action: 'getBBCMediaType'}, function (response) {
        vm.mediaTypes = response;
    });

    PopulateDataFactory.list({action: 'getBBCServices'}, function (response) {
        vm.services = response;
    });

    PopulateDataFactory.list({action: 'getTheBBCCategories'}, function (response) {
        vm.options = response;
    });


}