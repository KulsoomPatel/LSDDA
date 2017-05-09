/**
 * Created by Kulsoom on 08/05/2017.
 */
//= wrapped
//= require /angular/angular
//= require /angular/angular-route
//= require /angular/angular-touch
//= require /angular/angular-animate
//= require /angular/ui-bootstrap-tpls
//= require /lsdda/core/lsdda.core
//= require_self
//= require routes
//= require_tree services
//= require_tree controllers
//= require_tree domain
//= require_tree templates
//= require_tree filters

angular.module("lsdda.detail", [
    "lsdda.core",
    "ui.bootstrap",
    "ngAnimate",
    "ngTouch",
    "ngRoute"

]);