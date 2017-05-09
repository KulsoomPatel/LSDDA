/**
 * Created by Kulsoom on 08/05/2017.
 */
//= wrapped

angular
    .module("lsdda.detail")
    .factory("DetailServiceFactory", DetailServiceFactory);

function DetailServiceFactory(DomainServiceFactory) {
    return DomainServiceFactory('/detail/:action/:programmeId', {programmeId: '@id', action: '@action'},
        {"show": {method: "GET"}},
        {"save": {method: "POST"}},
        {"delete": {method: "DELETE"}}
    );
}