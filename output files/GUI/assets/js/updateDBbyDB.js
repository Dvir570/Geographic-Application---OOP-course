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
		
	/* filters */
	var id1Filter = '<input id="id1Filter" type="text"/>';
	var dt1Filter = '<p id="dt1Filter"><input id="sdt1Filter" type="datetime-local" value="2017-06-01T08:30"><input id="edt1Filter" type="datetime-local" value="2017-06-01T08:30"></p>';
	var location1Filter = '<p id="location1Filter"><label>Lat range:</label><input id="slat1Filter" type="number" min="0" step="0.00001"/><input id="elat1Filter" type="number" min="0" step="0.00001"/><label>Lon range:</label><input id="slon1Filter" type="number" min="0" step="0.00001"/><input id="elon1Filter" type="number" min="0" step="0.00001"/></p>';
	var id2Filter = '<input id="id2Filter" type="text"/>';
	var dt2Filter = '<p id="dt2Filter"><input id="sdt2Filter" type="datetime-local" value="2017-06-01T08:30"><input id="edt2Filter" type="datetime-local" value="2017-06-01T08:30"></p>';
	var location2Filter = '<p id="location2Filter"><label>Lat range:</label><input id="slat2Filter" type="number" min="0" step="0.00001"/><input id="elat2Filter" type="number" min="0" step="0.00001"/><label>Lon range:</label><input id="slon2Filter" type="number" min="0" step="0.00001"/><input id="elon2Filter" type="number" min="0" step="0.00001"/></p>';	$("p#filterInfo1").empty()
	$("p#filterInfo1").append(id1Filter)
	$("select#filterType1").change(function() {
		var sel = $("#filterType1 option:selected");
		if(sel.text() == "ID"){
			$("p#filterInfo1").empty()
			$("p#filterInfo1").append(id1Filter)
		}
		if(sel.text() == "TIME"){
			$("p#filterInfo1").empty()
			$("p#filterInfo1").append(dt1Filter)
		}
		if(sel.text() == "LOCATION"){
			$("p#filterInfo1").empty()
			$("p#filterInfo1").append(location1Filter)
		}
	})
	$("select#operation").change(function() {
		var operation = $("#operation option:selected");
		var filter2 = '<p><select id="filterType2" class="soflow"><option value="ID">ID</option><option value="TIME">TIME</option><option value="LOCATION">LOCATION</option></select></p><p><input type="checkbox" id="not2"><label for="not2">Not</label></p><p id="filterInfo2"></p>';
		if(operation.text() != "NO OPERATION"){
			$("#filter2").removeClass("hideme")
			$("p#filterInfo2").empty().append(id2Filter)
		}else{
			$("#filter2").addClass("hideme")
		}
	})
	$("#filterType2").change(function() {
		var sel = $("#filterType2 option:selected");
		if(sel.text() == "ID"){
			$("p#filterInfo2").empty()
			$("p#filterInfo2").append(id2Filter)
		}
		if(sel.text() == "TIME"){
			$("p#filterInfo2").empty()
			$("p#filterInfo2").append(dt2Filter)
		}
		if(sel.text() == "LOCATION"){
			$("p#filterInfo2").empty()
			$("p#filterInfo2").append(location2Filter)
		}
	})
	$("#filter2").addClass("hideme")
	/* requests */
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
	$("#UpdateDBcsv").click(function() {
				var input = $("input#DBfileUpload").val()
				$.ajax(
					{
						"url": encodeURI("/DBupdate?" +input)

					}
				).then(
					function(output) {
						$("div#output").empty().append("<div>"+output+"</div>")
						$("input#DBfileUpload").val("")
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
				).then(function(output) {
						$("div#output").empty().append("<div>"+output+"</div>")
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