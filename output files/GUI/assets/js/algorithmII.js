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
		
		$("#GetAppreciateLocation").click(function(){
			if($("#string-radio").is(':checked')){
				if(!$("#sampleStringInput").val()){
					$("div#output").empty().append("Input is empty")
					return;
				}
				var string = $("#sampleStringInput").val();
				$.ajax({"url": encodeURI("/algorithmII?S%" + string)}).then(function(output) {
					$("div#output").empty().append(output)
				});
			}else if($("#macs-signals-radio").is(':checked')){
				if(!$("#mac1").val()){
					$("div#output").empty().append("You must enter at least one mac address")
					return;
				}
				var pairs;
				if(!$("#mac1").val())
					pairs += $("#mac1").val() + "," + $("#signal1").val();
				if(!$("#mac2").val())
					pairs += "," + $("#mac2").val() + "," + $("#signal2").val();
				if(!$("#mac3").val())
					pairs += "," + $("#mac3").val() + "," + $("#signal3").val();
				$.ajax({"url": encodeURI("/algorithmII?M%" + pairs)}).then(function(output) {
					$("div#output").empty().append(output)
				});
			}
			else{
				$("div#output").empty().append("Input is empty")
			}
		})
		$('input[type=radio][name=radio-input-choose]').change(function(){
			if(this.value == 'string'){
				$("#macsAndSignals").addClass("hideme")
				$("#sampleString").removeClass("hideme")
			}else if(this.value == 'macs-signals'){
				$("#macsAndSignals").removeClass("hideme")
				$("#sampleString").addClass("hideme")
			}
		})
		$("#macsAndSignals").addClass("hideme")
		$("#sampleString").addClass("hideme")
		//Requests
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
					$("div#output").empty().append("<div>"+output+"</div>")
				}
			);
			return false
		})
		$("#DBsaveKML").click(function() {
			$.ajax({"url": encodeURI("/DBsaveKML?")}).then(function(output) {
					$("div#output").empty().append("<div>"+output+"</div>")
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
var src = 'https://127.0.0.1:8001/home/result.kml';
function initMap() {
		map = new google.maps.Map(document.getElementById('map'), {
		  center: new google.maps.LatLng(-19.257753, 146.823688),
		  zoom: 2,
		  mapTypeId: 'terrain'
		});

		var kmlLayer = new google.maps.KmlLayer(src, {
		  suppressInfoWindows: true,
		  preserveViewport: false,
		  map: map
		});
}