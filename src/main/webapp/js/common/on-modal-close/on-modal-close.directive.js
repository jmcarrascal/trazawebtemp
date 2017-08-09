(function() {
    'use strict';

    angular
        .module('CommonModule')
        .directive('onModalClose', OnModalCloseDirective);

    OnModalCloseDirective.$inject = [];
    function OnModalCloseDirective() {
        var directive = {
            link: link,
            restrict: 'A',
            scope: {
            	onModalClose: "&"
            }
        };
        return directive;
        
        function link(scope, element, attrs) {
            $(element).on('hidden.bs.modal', function (e) {
                scope.onModalClose()();
            });
        }
    }
})();