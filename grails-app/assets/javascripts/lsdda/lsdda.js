//= wrapped
//= require /angular/angular
//= require /angular/angular-route
//= require /angular/angular-touch
//= require /angular/angular-animate
//= require /angular/ui-bootstrap-tpls
//= require /angular/loading-bar
//= require /lsdda/core/lsdda.core
//= require /lsdda/search/lsdda.search
//= require /lsdda/detail/lsdda.detail


angular.module("lsdda", [
    "lsdda.core",
    "ui.bootstrap",
    "ngAnimate",
    "ngTouch",
    "ngRoute",
    "lsdda.search",
    "angular-loading-bar",
    "lsdda.detail"

]);
