'use strict';

/* Filters */

var appDir = angular.module('ngprodusimpa.filters', []);

appDir.filter('interpolate', ['version', function(version) {
    return function(text) {
      return String(text).replace(/\%VERSION\%/mg, version);
    };
  }]);


appDir.filter('formatDate',function(){
	return function(data){
		var result=formatDate(String(data));
		return result;};  
});

appDir.filter('titlecase', function () {
	return function(input) {
	    if (input != "") {
		var words=input.split(' ');
	    for (var i=0; i<words.length; i++) {
	      words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1).toLowerCase();
	    }
	    return words.join(' ');
	    } 
	else {return ""}
	 }
});

/**
 * AngularJS default filter with the following expression:
 * "person in people | filter: {name: $select.search, age: $select.search}"
 * performs a AND between 'name: $select.search' and 'age: $select.search'.
 * We want to perform a OR.
 */
appDir.filter('propsFilter', function() {
  return function(items, props) {
    var out = [];

    if (angular.isArray(items)) {
      items.forEach(function(item) {
        var itemMatches = false;

        var keys = Object.keys(props);
        for (var i = 0; i < keys.length; i++) {
          var prop = keys[i];
          var text = props[prop].toLowerCase();
          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
            itemMatches = true;
            break;
          }
        }

        if (itemMatches) {
          out.push(item);
        }
      });
    } else {
      // Let the output be the input untouched
      out = items;
    }

    return out;
  }
});