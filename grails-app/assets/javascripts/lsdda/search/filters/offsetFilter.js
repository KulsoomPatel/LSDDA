/**
 * Created by Kulsoom on 30/04/2017.
 */
angular.module("lsdda.search")
    .filter("offset", function () {
        return function (input, start) {
            return input.slice(start);
        };
    });