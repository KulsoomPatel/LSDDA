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
    vm.mediaType = undefined;
    vm.isClip = undefined;
    vm.service = undefined;
    vm.currentPage = 1;
    vm.itemsPerPage = 8;
    vm.displayOptions = false;
    vm.selectedCats = [];
    vm.selectedTags = [];
    vm.catSettings = {
        enableSearch: true,
        template: '{{option}}',
        checkBoxes: true,
        scrollableHeight: '300px',
        scrollable: true,
        selectedToTop: true
    };

    vm.searchData = function () {

        if (vm.displayOptions === true) {

            SearchDataFactory.list({
                action: 'advancedSearch',
                value: vm.searchTerms,
                is_clip: vm.isClip,
                media_type: vm.mediaType,
                service: vm.service,
                start_time: vm.dt1.getTime() / 1000,
                end_time: vm.dt2.getTime() / 1000,
                tags: vm.selectedTags
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

    PopulateDataFactory.list({action: 'getBBCTags'}, function (response) {
        vm.tags = response;
    });


    /*The Date stuff*/
    vm.format = 'dd-MMMM-yyyy';
    vm.dt1 = new Date();
    vm.dt2 = new Date();
    vm.open1 = function () {
        vm.popup1.opened = true;
    };

    vm.open2 = function () {
        vm.popup2.opened = true;
    };

    vm.popup1 = {
        opened: false
    };

    vm.popup2 = {
        opened: false
    };

    vm.dateOptions = {
        formatYear: 'yy',
        maxDate: new Date(),
        startingDay: 1
    };


}