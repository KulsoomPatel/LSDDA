/**
 * Created by Kulsoom on 27/04/2017.
 */
angular.module("lsdda.search")
    .factory("SearchDataFactory", SearchDataFactory);

function SearchDataFactory(DomainServiceFactory) {
    return DomainServiceFactory('/search/:action', {action: '@action'},
        {"show": {method: "GET"}},
        {"save": {method: "POST"}},
        {"delete": {method: "DELETE"}}
    );
}