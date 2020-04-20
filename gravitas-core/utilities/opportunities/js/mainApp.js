var mainApp = angular.module("mainApp", ["firebase"]);
mainApp.directive('setheight',function ($window) {
  function link(scope, element, attrs) { //scope we are in, element we are bound to, attrs of that element
    scope.$watch(function(){ //watch any changes to our element
      element.css('height', (($window.innerHeight - 35)/2 - 100) +  'px');
      element.css('overflow', 'auto');
    });
  }
  return {
    link: link // the function to link to our element
  };
});