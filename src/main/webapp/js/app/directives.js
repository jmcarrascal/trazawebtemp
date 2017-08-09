'use strict';

/* Directives */

var appDir = angular.module('ngprodusimpa.directives', []);

appDir.directive('myRefresh', function($location, $route) {
    return function(scope, element, attrs) {
        element.bind('click', function() {
            if (element[0] && element[0].href && element[0].href === $location.absUrl()) {
                $route.reload();
            }
        });
    };
});

appDir.directive('appVersion', [
    'version', function(version) {
        return function(scope, elm, attrs) {
            elm.text(version);
        };
    }
]);

app.directive('ngEnter', function() {
    return function(scope, element, attrs) {
        element.bind("keydown keypress", function(event) {
            if (event.which === 13) {
                scope.$apply(function() {
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});

app.directive('ngF4', function() {
    return function(scope, element, attrs) {
        element.bind("keydown keypress", function(event) {
            if (event.which === 115) {
                scope.$apply(function() {
                    scope.$eval(attrs.ngF4);
                });

                event.preventDefault();
            }
        });
    };
});

app.directive('focusOnCondition', [
    '$timeout', function($timeout) {
        var checkDirectivePrerequisites = function(attrs) {
            if (!attrs.focusOnCondition && attrs.focusOnCondition != "") { throw "FocusOnCondition missing attribute to evaluate"; }
        }

        return { restrict : "A", link : function(scope, element, attrs, ctrls) {
            checkDirectivePrerequisites(attrs);

            scope.$watch(attrs.focusOnCondition, function(currentValue, lastValue) {
                if (currentValue == true) {
                    $timeout(function() {
                        element.focus();
                    });
                }
            });
        } };
    }
]);

appDir.directive('dateTimePicker', function() {
    return { restrict : 'E', replace : true, scope : { recipient : '=' }, template : '<div>' + '<input type="text" readonly data-date-format="yyyy-mm-dd hh:ii" name="recipientDateTime" data-date-time required>' + '</div>', link : function(scope, element, attrs, ngModel) {
        var input = element.find('input');

        input.datetimepicker({ format : "mm/dd/yyyy hh:ii", showMeridian : true, autoclose : true, todayBtn : true, todayHighlight : true });

        element.bind('blur keyup change', function() {
            scope.recipient.datetime = input.val();
        });
    } }
});

appDir.directive('alertMessage', function() {
    return { restrict : 'E', replace : true, template : '<div class="alert alert-success" ng-show="message != null">' + '<a class="close" data-dismiss="alert">×</a>  ' + '<strong>{{message}}</strong>' + '</div>'

    };
});

appDir.directive('alertError', function() {
    return { restrict : 'E', replace : true, template : '<div class="alert alert-danger" ng-show="error != null">' + '<a class="close" data-dismiss="alert">×</a>  ' + '<strong>{{error}}</strong>' + '</div>' };
});


appDir.directive("closeMenu", function($route) {
    "ngInject";
    return function(scope, element, attrs) {
        $(element).on('click', function(e) {
            if (!$(element).hasClass('md-closed')) {
                $(element).addClass('md-closed');
            }
        }) 
    }
})


/**
 * Checklist-model
 * AngularJS directive for list of checkboxes
 * https://github.com/vitalets/checklist-model
 * License: MIT http://opensource.org/licenses/MIT
 */

app.directive('checklistModel', ['$parse', '$compile', function($parse, $compile) {
  // contains
  function contains(arr, item, comparator) {
    if (angular.isArray(arr)) {
      for (var i = arr.length; i--;) {
        if (comparator(arr[i], item)) {
          return true;
        }
      }
    }
    return false;
  }

  // add
  function add(arr, item, comparator) {
    arr = angular.isArray(arr) ? arr : [];
      if(!contains(arr, item, comparator)) {
          arr.push(item);
      }
    return arr;
  }  

  // remove
  function remove(arr, item, comparator) {
    if (angular.isArray(arr)) {
      for (var i = arr.length; i--;) {
        if (comparator(arr[i], item)) {
          arr.splice(i, 1);
          break;
        }
      }
    }
    return arr;
  }

  // http://stackoverflow.com/a/19228302/1458162
  function postLinkFn(scope, elem, attrs) {
     // exclude recursion, but still keep the model
    var checklistModel = attrs.checklistModel;
    attrs.$set("checklistModel", null);
    // compile with `ng-model` pointing to `checked`
    $compile(elem)(scope);
    attrs.$set("checklistModel", checklistModel);

    // getter / setter for original model
    var getter = $parse(checklistModel);
    var setter = getter.assign;
    var checklistChange = $parse(attrs.checklistChange);
    var checklistBeforeChange = $parse(attrs.checklistBeforeChange);

    // value added to list
    var value = attrs.checklistValue ? $parse(attrs.checklistValue)(scope.$parent) : attrs.value;


    var comparator = angular.equals;

    if (attrs.hasOwnProperty('checklistComparator')){
      if (attrs.checklistComparator[0] == '.') {
        var comparatorExpression = attrs.checklistComparator.substring(1);
        comparator = function (a, b) {
          return a[comparatorExpression] === b[comparatorExpression];
        };
        
      } else {
        comparator = $parse(attrs.checklistComparator)(scope.$parent);
      }
    }

    // watch UI checked change
    scope.$watch(attrs.ngModel, function(newValue, oldValue) {
      if (newValue === oldValue) { 
        return;
      } 

      if (checklistBeforeChange && (checklistBeforeChange(scope) === false)) {
        scope[attrs.ngModel] = contains(getter(scope.$parent), value, comparator);
        return;
      }

      setValueInChecklistModel(value, newValue);

      if (checklistChange) {
        checklistChange(scope);
      }
    });

    function setValueInChecklistModel(value, checked) {
      var current = getter(scope.$parent);
      if (angular.isFunction(setter)) {
        if (checked === true) {
          setter(scope.$parent, add(current, value, comparator));
        } else {
          setter(scope.$parent, remove(current, value, comparator));
        }
      }
      
    }

    // declare one function to be used for both $watch functions
    function setChecked(newArr, oldArr) {
      if (checklistBeforeChange && (checklistBeforeChange(scope) === false)) {
        setValueInChecklistModel(value, scope[attrs.ngModel]);
        return;
      }
      scope[attrs.ngModel] = contains(newArr, value, comparator);
    }

    // watch original model change
    // use the faster $watchCollection method if it's available
    if (angular.isFunction(scope.$parent.$watchCollection)) {
        scope.$parent.$watchCollection(checklistModel, setChecked);
    } else {
        scope.$parent.$watch(checklistModel, setChecked, true);
    }
  }

  return {
    restrict: 'A',
    priority: 1000,
    terminal: true,
    scope: true,
    compile: function(tElement, tAttrs) {
      if ((tElement[0].tagName !== 'INPUT' || tAttrs.type !== 'checkbox') && (tElement[0].tagName !== 'MD-CHECKBOX') && (!tAttrs.btnCheckbox)) {
        throw 'checklist-model should be applied to `input[type="checkbox"]` or `md-checkbox`.';
      }

      if (!tAttrs.checklistValue && !tAttrs.value) {
        throw 'You should provide `value` or `checklist-value`.';
      }

      // by default ngModel is 'checked', so we set it if not specified
      if (!tAttrs.ngModel) {
        // local scope var storing individual checkbox model
        tAttrs.$set("ngModel", "checked");
      }

      return postLinkFn;
    }
  };

}]);
