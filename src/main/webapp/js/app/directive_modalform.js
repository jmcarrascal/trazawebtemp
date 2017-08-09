(function(ng, app) {

"use strict";
app.directive("modalForm", ["$compile", "$templateRequest", "$timeout", modalFormDirective]);
function modalFormDirective($compile, $templateRequest, $timeout) {
    var id_count = 1;

    return {
        restrict: "E",
        scope: {
            formscope: "=data",
            formUrl: "@",
        },
        link: function(scope, element, attrs, ctrl, transclude) {
            var modalId = "";
            var callbackSuccess = null;
            var callbackCancel = null;
            var form_name = "";

            $templateRequest(scope.formUrl).then(function(html) {
                modalId = "divModalForm_" + id_count;
                var str = "<div id=\"" + modalId + "\" class=\"modal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalFormLabel\" aria-hidden=\"true\">" 
                + "<div class=\"modal-header\">"
                + "<span class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></span>"
                + "</div>"
                + "<div class=\"modal-body\">" 
                + html 
                + "</div></div>";
                id_count++;
                element.html(str);
                $compile(element.contents())(scope);
                var form = element.find("form");
                form_name = form.attr("name");
            });

            scope.formSubmit = function() {
                var form_submit = scope[form_name];
                if (form_submit) {
                    if (form_submit.$valid) {
                        var hide_modal = true;
                        if (scope.formscope.onsubmit) {
                        	scope.formscope.onsubmit(function(success) {
                        		if (success) {
        	                        if (callbackSuccess) {
        	                            if (callbackSuccess() === false) {
        	                                hide_modal = false;
        	                            }
        	                        }
        	                        if (hide_modal === true) {
        	                            scope.formscope.hide();
        	                        }
                        		}
                        	});
                        }
                        else {
	                        if (callbackSuccess) {
	                            if (callbackSuccess() === false) {
	                                hide_modal = false;
	                            }
	                        }
	                        if (hide_modal === true) {
	                            scope.formscope.hide();
	                        }
                        }
                    }
                    else {
                        var element_error = null;
                        for (var index in form_submit.$error) {
                            for (var i = 0; i < form_submit.$error[index].length; i++) {
                                element_error = form_submit.$error[index][i].$name;
                                break;
                            }
                            if (element_error !== null) {
                                break;
                            }
                        }

                        if (element_error !== null) {
                            $timeout(function() {
                                $('form[name="' + form_name + '"] [name="' + element_error + '"]').focus();
                            });
                        }
                        return;
                    }
                }
            };
            scope.formCancel = function() {
                if (callbackCancel) {
                    callbackCancel();
                }
                scope.formscope.hide();
            };

            scope.$watch("formscope", function() {
                if (scope.formscope) {
                    callbackSuccess = null;
                    callbackCancel = null;

                    scope.formscope.show = function(success, cancel) {
                        if (scope[form_name]) {
                            scope.$broadcast("form-validation:reset", scope[form_name]);
                        }
                        callbackSuccess = success;
                        callbackCancel = cancel;

                        $("#" + modalId).modal("show");
                        if (scope.formscope.onshow) {
                        	scope.formscope.onshow(scope.formscope);
                        }
                    };

                    scope.formscope.hide = function() {
                        $("#" + modalId).modal("hide");
                    };
                }
            });
        }
    };
}

})(angular, appDir);
