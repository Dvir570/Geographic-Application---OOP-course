/*
	Intensify by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
*/

(function($) {

	skel.breakpoints({
		xlarge:	'(max-width: 1680px)',
		large:	'(max-width: 1280px)',
		medium:	'(max-width: 980px)',
		small:	'(max-width: 736px)',
		xsmall:	'(max-width: 480px)'
	});
	$(document).ready(function () {	
		//Requests
		$("#getAppreciateLocation").click(function(){
			if(!$("#macAddress").val()){
				$("div#output").empty().append("Input is empty")
				return;
			}
			var mac = $("#macAddress").val();
			$.ajax({"url": encodeURI("/algorithmI?" + mac)}).then(function(output) {
				var coord = output.split(",");
					avgLon = parseFloat(coord[0]);
					avgLat = parseFloat(coord[1]);
					initMap();
					$("div#output").empty().append(output)
			});
		})
		$("#DBclear").click(function() {
			$.ajax({"url": encodeURI("/DBclear?")}).then(function(output) {
					$("div#output").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRecords?")}).then(function(output) {
				$("#numOfRecords").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRouters?")}).then(function(output) {
				$("#numOfRouters").empty().append(output)
			});
			return false
		})
		$("#DBsaveCSV").click(function() {
			$.ajax({"url": encodeURI("/DBsaveCSV?")}).then(function(output) {
					$("div#output").empty().append(output)
				}
			);
			return false
		})
		$("#DBsaveKML").click(function() {
			$.ajax({"url": encodeURI("/DBsaveKML?")}).then(function(output) {
					$("div#output").empty().append(output)
				}
			);
			return false
		})
	});

	$(function() {

		var	$window = $(window),
			$body = $('body'),
			$header = $('#header');

		// Disable animations/transitions until the page has loaded.
			$body.addClass('is-loading');

			$window.on('load', function() {
				window.setTimeout(function() {
					$body.removeClass('is-loading');
				}, 100);
			});

		// Fix: Placeholder polyfill.
			$('form').placeholder();

		// Prioritize "important" elements on medium.
			skel.on('+medium -medium', function() {
				$.prioritize(
					'.important\\28 medium\\29',
					skel.breakpoint('medium').active
				);
			});

		// Scrolly.
			$('.scrolly').scrolly({
				offset: function() {
					return $header.height();
				}
			});

		// Menu.
			$('#menu')
				.append('<a href="#menu" class="close"></a>')
				.appendTo($body)
				.panel({
					delay: 500,
					hideOnClick: true,
					hideOnSwipe: true,
					resetScroll: true,
					resetForms: true,
					side: 'right'
				});

	});

})(jQuery);
var map;
var avgLon = 0, avgLat = 0;
function initMap() {
        var uluru = {lat: avgLat, lng: avgLon };
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 7,
          center: uluru
        });
        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });
}