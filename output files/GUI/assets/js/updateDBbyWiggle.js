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
	$.ajax(
		{"url": encodeURI("/numOfRecords?")}).then(
			function(output) {
				$("#numOfRecords").append(output)
			});
	$.ajax(
		{"url": encodeURI("/numOfRouters?")}).then(
			function(output) {
				$("#numOfRouters").append(output)
			});
	/*$.ajax(
		{"url": encodeURI("/filterDetails?")}).then(
			function(output) {
				$("#filterDetails").append(output)
			});*/
			
	$("#UpdateWiggleCsv").click(function() {
			var input = $("input#WiggleFileUpload").val()
			$.ajax(
				{
					"url": encodeURI("/WiggleUpdate?" +input)
				}
			).then(
				function(output) {
					$("div#output").empty().append("<div>"+output+"</div>")
					$("input#WiggleFileUpload").val("")
				}
			);
			$.ajax({"url": encodeURI("/numOfRecords?")}).then(function(output) {
				$("#numOfRecords").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRouters?")}).then(function(output) {
				$("#numOfRouters").empty().append(output)
			});
			return false
		})
		$("#DBclear").click(function() {
			$.ajax(
				{
					"url": encodeURI("/DBclear?")
				}
			).then(
				function(output) {
					$("div#output").empty().append("<div>"+output+"</div>")
				}
			);
			$.ajax({"url": encodeURI("/numOfRecords?")}).then(function(output) {
				$("#numOfRecords").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRouters?")}).then(function(output) {
				$("#numOfRouters").empty().append(output)
			});
			return false
		})
		$("#DBsaveCSV").click(function() {
				$.ajax(
					{
						"url": encodeURI("/DBsaveCSV?")
					}
				).then(
					function(output) {
						$("div#output").empty().append("<div>"+output+"</div>")
					}
				);
				return false
			})
			$("#DBsaveKML").click(function() {
				$.ajax(
					{
						"url": encodeURI("/DBsaveKML?")
					}
				).then(
					function(output) {
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