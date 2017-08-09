(function(ng, app) {

"use strict";
app.directive("saldoBar", [saldoBar]);
function saldoBar() {
    return {
        restrict: "E",
        scope: {
            value: "@",
        },
        template: "<div class='progress'>" +
        	"<div class='progress-bar' ng-class=\"{'progress-green': value < 50, 'progress-yellow': value >= 50 && value < 75, 'progress-red': value >= 75}\" " +
        	"style='width: {{value}}%'></div><div class='progress-value'>{{value}}%</div></div>",
    };
}

})(angular, appDir);
