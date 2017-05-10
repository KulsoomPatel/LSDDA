/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .controller("SearchController", ["SearchDataFactory", "PopulateDataFactory", "$routeParams", "$location", SearchController]);

function SearchController(SearchDataFactory, PopulateDataFactory, $routeParams, $location) {

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
                tags: vm.selectedTags,
                cats: vm.selectedCats
            }, function (response) {
                vm.results = response;
                vm.showTable = true;
                vm.totalItems = vm.results.length;
            })

        } else if (vm.displayOptions === false) {

            SearchDataFactory.list({action: 'searchData', value: vm.searchTerms}, function (response) {
                vm.showTable = true;
                vm.results = response;
                vm.totalItems = vm.results.length;
                vm.hello = "Hello"
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

    vm.detailedView = function (pid) {

        $location.path("/" + pid);
    };

    //CALENDER STUFF
    vm.cellIsOpen = true;
    vm.calendarView = 'month';
    vm.viewDate = new Date();
    vm.events = [
        {
            title: 'My event title', // The title of the event
/*            startsAt: new Date(2013, 5, 1, 1), // A javascript date object for when the event starts
            endsAt: new Date(2014, 8, 26, 15), // Optional - a javascript date object for when the event ends*/
            color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
                primary: '#e3bc08', // the primary event color (should be darker than secondary)
                secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
            },
            actions: [{ // an array of actions that will be displayed next to the event title
                label: '<i class=\'glyphicon glyphicon-pencil\'></i>', // the label of the action
                cssClass: 'edit-action' // a CSS class that will be added to the action element so you can implement custom styling

            }],
            incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
            cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
            allDay: false // set to true to display the event as an all day event on the day view
        }
    ];

    vm.calenderTitle = "Schedule of Programmes";

    vm.timespanClicked = function(date, cell) {

        if (vm.calendarView === 'month') {
            if ((vm.cellIsOpen && moment(date).startOf('day').isSame(moment(vm.viewDate).startOf('day'))) || cell.events.length === 0 || !cell.inMonth) {
                vm.cellIsOpen = false;
            } else {
                vm.cellIsOpen = true;
                vm.viewDate = date;
            }
        } else if (vm.calendarView === 'year') {
            if ((vm.cellIsOpen && moment(date).startOf('month').isSame(moment(vm.viewDate).startOf('month'))) || cell.events.length === 0) {
                vm.cellIsOpen = false;
            } else {
                vm.cellIsOpen = true;
                vm.viewDate = date;
            }
        }

    };


}