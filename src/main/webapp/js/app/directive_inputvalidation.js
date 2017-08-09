(function(ng, app) {

"use strict";
app.directive("inputValidationForm", inputValidationForm);
function inputValidationForm() {
    return {
        restrict: "A",
        require: "form",
        link: function(scope, element, attrs, form) {
            element.on("submit", function() {
                scope.$broadcast("form:submit", form);
            });
        }
    };
}

app.directive("inputValidation", ["$compile", modalFormDirective]);
function modalFormDirective($compile) {
    return {
        restrict: "A",
        require: "^form",
        link: function(scope, element, attrs, form) {
            var form_name = form.$name;
            var input = $(element).find("[name]");
            if (input.length === 0 || input.length > 1) {
                console.error("Invalid input fo validator");
                return;
            }

            // Get all the possibles messages
            var required_message = $(element).find("[input-validation-required]");
            required_message.addClass("help-block");
            required_message.hide();
            var float_message = $(element).find("[input-validation-float]");
            float_message.addClass("help-block");
            float_message.hide();

            // Set events for the input
            input.change(validateInput);
            input.blur(validateInput);
            input.keyup(validateInput);
            scope.$on("form:submit", function(event, sender_form) {
                if (form == sender_form) {
                    validateInput();
                }
            });
            scope.$on("form-validation:reset", function(event, sender_form) {
                if (form == sender_form) {
                    var input_name = input.attr("name");

                    form.$submitted = false;
                    if (form[input_name]) {
	                    form[input_name].$setPristine();
	                    form[input_name].$setUntouched();
                    }
                    $(element).removeClass("has-success");
                    $(element).removeClass("has-error");
                    validateInput();
                }
            });
            scope.$on("form-validation:refresh", function(event) {
                var input_name = input.attr("name");

                $(element).removeClass("has-success");
                $(element).removeClass("has-error");
                validateInput();
            });
            scope.validate = function() {
                scope.$broadcast("form-validation:refresh");
            };

            function validateInput() {
                var input_name = input.attr("name");
                if (!form[input_name]) {
                	return;
                }

                if (form[input_name].$invalid && (!form[input_name].$pristine || form.$submitted)) {
                    $(element).removeClass("has-success");
                    $(element).addClass("has-error");

                    if (form[input_name].$error.required) {
                        required_message.show();
                    }
                    if (form[input_name].$error.validationFloat) {
                    	float_message.show();
                    }
                }
                else {
                    // No errors, hide all messages
                    required_message.hide();
                    float_message.hide();
                }

                if (form[input_name].$valid && !form[input_name].$pristine) {
                    $(element).addClass("has-success");
                    $(element).removeClass("has-error");
                } 
            }
        }
    };
}

app.directive("validationFloat", function() {
    return {
        require: "ngModel",
        restrict: "A",
        scope: {
        },
        link: function(scope, element, attributes, ngModel) {
            ngModel.$validators.validationFloat = function(modelValue) {
            	if (modelValue == "") {
            		return true;
            	}
                return (!isNaN(parseFloat(modelValue)) && isFinite(modelValue));
            };
        }
    };
});

})(angular, appDir);
