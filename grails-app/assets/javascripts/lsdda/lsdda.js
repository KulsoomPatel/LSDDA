//= wrapped
//= require /angular/angular
//= require /angular/angular-route
//= require /angular/angular-touch
//= require /angular/angular-animate
//= require /angular/ui-bootstrap-tpls
//= require /lsdda/core/lsdda.core
//= require /lsdda/index/lsdda.index

angular.module("lsdda", [
    "lsdda.core",
    "lsdda.index",
    "ui.bootstrap",
    "ngAnimate",
    "ngTouch",
    "ngRoute"
]);
