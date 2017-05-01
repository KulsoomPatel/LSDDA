/**
 * Created by Kulsoom on 01/05/2017.
 */
angular.module("lsdda.search")
    .factory("PopulateDataFactory", PopulateDataFactory);

function PopulateDataFactory(DomainServiceFactory) {
    return DomainServiceFactory('/data/:action', {action: '@action'},
        {"show": {method: "GET"}},
        {"save": {method: "POST"}},
        {"delete": {method: "DELETE"}}
    );
}