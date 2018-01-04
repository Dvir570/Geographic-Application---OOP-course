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
		var id1Filter = '<input id="id1Filter" type="text" placeholder="device ID"/>';
		var dt1Filter = '<p id="dt1Filter"><input id="sdt1Filter" type="datetime-local" value="2017-06-01T08:30"><input id="edt1Filter" type="datetime-local" value="2017-06-01T08:30"></p>';
		var location1Filter = '<p id="location1Filter"><label>Lat range:</label><input id="slat1Filter" type="number" min="0" step="0.00001"/><input id="elat1Filter" type="number" min="0" step="0.00001"/><label>Lon range:</label><input id="slon1Filter" type="number" min="0" step="0.00001"/><input id="elon1Filter" type="number" min="0" step="0.00001"/></p>';
		var id2Filter = '<input id="id2Filter" type="text" placeholder="device ID"/>';
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
			$("#filterDetails").empty()
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
		$("#applyFilter").click(function() {
			//filter building
			var filter = buildFilter();
			$("#filterDetails").empty().append(filter);
			$.ajax(
				{
					"url": encodeURI("/FilterBy?" + filter)
				}
			).then(function(output) {
					$("div#filterOutput").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRecords?")}).then(function(output) {
					$("#numOfRecords").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRouters?")}).then(function(output) {
					$("#numOfRouters").empty().append(output)
			});
			return false
		})
		$("#saveFilter").click(function() {
			var fileNameFilter = $("input#fileNameFilter").val();
			var filter = buildFilter();
			$.ajax(
				{
					"url": encodeURI("/saveFilter?" + fileNameFilter+"%"+filter)
				}
			).then(function(output) {
					$("div#filterOutput").empty().append(output)
			});
		})
		$("#uploadFilter").click(function() {
			var filterFileName = $("input#fileNameUploadFilter").val();
			$.ajax(
				{
					"url": encodeURI("/uploadFilter?" + filterFileName)
				}
			).then(function(output) {
				if(output.includes("%")){
					msgFilter = output.split("%");
					uploadFilter(msgFilter[1]);
					$("div#filterOutput").empty().append(msgFilter[0])
				}else $("div#filterOutput").empty().append(output)
			});
		})
		$("#restoreDB").click(function() {
			$.ajax(
				{
					"url": encodeURI("/restoreDB?")
				}
			).then(function(output) {
					$("div#filterOutput").empty().append(output)
					$("#filterDetails").empty()
			});
			$.ajax({"url": encodeURI("/numOfRecords?")}).then(function(output) {
					$("#numOfRecords").empty().append(output)
			});
			$.ajax({"url": encodeURI("/numOfRouters?")}).then(function(output) {
					$("#numOfRouters").empty().append(output)
			});
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

function buildFilter(){
	var filter = "";
	var filter1Type = $("#filterType1 option:selected").text();
	var not1 = $("#not1").prop('checked');
	var operation = $("#operation option:selected").text();
	if(operation == "NO OPERATION"){
		if(filter1Type == "ID"){
			if(not1)
				filter = "ID(!(" + $("#id1Filter").val() +"))";
			else filter = "ID(" + $("#id1Filter").val() +")";
		}else if(filter1Type == "TIME"){
			if(not1)
				filter = "Time(!(" + $("#sdt1Filter").val() + "," + $("#edt1Filter").val() +"))";
			else filter = "Time(" + $("#sdt1Filter").val() + "," + $("#edt1Filter").val() +")";
		}else if(filter1Type == "LOCATION"){
			if(not1)
				filter = "Pos(!(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +"))";
			else filter = "Pos(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +")";
		}
	}else{
		var filter2Type = $("#filterType2 option:selected").text();
		var not2 = $("#not2").prop('checked');
		if(filter1Type == "TIME"){
			if(not1)filter = "Time(!(" + $("#sdt1Filter").val() + "," + $("#edt1Filter").val() +"))";
			else filter = "Time(" + $("#sdt1Filter").val() + "," + $("#edt1Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(filter2Type == "ID"){
				if(not2) filter = filter + "ID(!(" + $("#id2Filter").val() +"))";
				else filter = filter + "ID(" + $("#id2Filter").val() +")";
			} else if(filter2Type == "TIME"){
				if(not2) filter = filter + "Time(!(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +"))";
				else filter = filter + "Time(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +")";
			} else if(filter2Type == "LOCATION"){
				if(not2) filter = filter + "Pos(!(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +"))";
				else filter = filter + "Pos(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +")";
			}
		}else if(filter1Type == "ID" && filter2Type != "TIME"){
			if(not1) filter = "ID(!("+$("#id1Filter").val() +"))";
			else filter = "ID("+$("#id1Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(filter2Type == "ID"){
				if(not2) filter = filter + "ID(!(" + $("#id2Filter").val() +"))";
				else filter = filter + "ID(" + $("#id2Filter").val() +")";
			} else if(filter2Type == "LOCATION"){
				if(not2) filter = filter + "Pos(!(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +"))";
				else filter = filter + "Pos(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +")";
			}
		}else if(filter1Type == "LOCATION" && filter2Type == "LOCATION"){
			if(not1) filter = "Pos(!(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +"))";
			else filter = "Pos(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(not2) filter = filter + "Pos(!(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +"))";
			else filter = filter + "Pos(" + $("#slon2Filter").val() + "," + $("#elon2Filter").val() + "," + $("#slat2Filter").val() + "," + $("#elat2Filter").val() +")";
		}else if(filter1Type == "ID" && filter2Type == "TIME"){
			if(not2) filter = "Time(!(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +"))";
			else filter = "Time(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(not1) filter = filter + "ID(!(" + $("#id1Filter").val() +"))";
			else filter = filter + "ID(" + $("#id1Filter").val() +")";
		}else if(filter1Type == "LOCATION" && filter2Type == "TIME"){
			if(not2) filter = "Time(!(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +"))";
			else filter = "Time(" + $("#sdt2Filter").val() + "," + $("#edt2Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(not1) filter = filter + "Pos(!(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +"))";
			else filter = filter + "Pos(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +")";
		}else if(filter1Type == "LOCATION" && filter2Type == "ID"){
			if(not2) filter = "ID(!(" + $("#id2Filter").val() +"))";
			else filter = "ID(" + $("#id2Filter").val() +")";
			if(operation == "AND") filter = filter + "&&";
			else filter = filter + "||";
			if(not1) filter = filter + "Pos(!(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +"))";
			else filter = filter + "Pos(" + $("#slon1Filter").val() + "," + $("#elon1Filter").val() + "," + $("#slat1Filter").val() + "," + $("#elat1Filter").val() +")";
		}				
	}
	return filter;
}

function uploadFilter(filter){
	if(filter.includes("&&") || filter.includes("||")){
		$("#filter2").removeClass("hideme")
		var id1Filter = '<input id="id1Filter" type="text" placeholder="device ID"/>';
		var dt1Filter = '<p id="dt1Filter"><input id="sdt1Filter" type="datetime-local" value="2017-06-01T08:30"><input id="edt1Filter" type="datetime-local" value="2017-06-01T08:30"></p>';
		var location1Filter = '<p id="location1Filter"><label>Lat range:</label><input id="slat1Filter" type="number" min="0" step="0.00001"/><input id="elat1Filter" type="number" min="0" step="0.00001"/><label>Lon range:</label><input id="slon1Filter" type="number" min="0" step="0.00001"/><input id="elon1Filter" type="number" min="0" step="0.00001"/></p>';
		var id2Filter = '<input id="id2Filter" type="text" placeholder="device ID"/>';
		var dt2Filter = '<p id="dt2Filter"><input id="sdt2Filter" type="datetime-local" value="2017-06-01T08:30"><input id="edt2Filter" type="datetime-local" value="2017-06-01T08:30"></p>';
		var location2Filter = '<p id="location2Filter"><label>Lat range:</label><input id="slat2Filter" type="number" min="0" step="0.00001"/><input id="elat2Filter" type="number" min="0" step="0.00001"/><label>Lon range:</label><input id="slon2Filter" type="number" min="0" step="0.00001"/><input id="elon2Filter" type="number" min="0" step="0.00001"/></p>';
		//$("p#filterInfo2").empty().append(id2Filter)
		var filters;
		if(filter.includes("&&")){
			$("#operation").val("AND");
			filters = filter.split("&&");
		}else {
			$("#operation").val("OR");
			filters = filter.split("||");
		}
		if(filters[0].includes("Time")){
			$("p#filterInfo1").empty().append(dt1Filter)
			$("#filterType1").val("TIME");
			var times = filters[0].split(",");
			if(filters[0].includes("!")){
				$("#not1").prop('checked', true);
				$("#sdt1Filter").val(times[0].substring(7,times[0].length));
				$("#edt1Filter").val(times[1].substring(0,times[1].length-2));
			}else{
				$("#sdt1Filter").val(times[0].substring(5,times[0].length));
				$("#edt1Filter").val(times[1].substring(0,times[1].length-1));
			}
		}else if(filters[0].includes("Pos")){
			$("p#filterInfo1").empty().append(location1Filter)
			$("#filterType1").val("LOCATION");
			var cord = filters[0].split(",");
			if(filters[0].includes("!")){
				$("#not1").prop('checked', true);
				$("#slon1Filter").val(cord[0].substring(6,times[0].length));
				$("#elon1Filter").val(cord[1]);
				$("#slat1Filter").val(cord[2]);
				$("#elat1Filter").val(cord[3].substring(0,cord[3].length-2));
			}else{
				$("#slon1Filter").val(cord[0].substring(4,times[0].length));
				$("#elon1Filter").val(cord[1]);
				$("#slat1Filter").val(cord[2]);
				$("#elat1Filter").val(cord[3].substring(0,cord[3].length-1));
			}
		}else{ //filter1 is ID
			$("p#filterInfo1").empty().append(id1Filter)
			if(filters[0].includes("!")){
				$("#not1").prop('checked', true);
				$("#id1Filter").val(filters[0].substring(5,filters[0].length-2));
			}else{
				$("#id1Filter").val(filters[0].substring(3,filters[0].length-1));
			}
		}
		
		if(filters[1].includes("Time")){
			$("p#filterInfo2").empty().append(dt2Filter)
			$("#filterType2").val("TIME");
			var times = filters[1].split(",");
			if(filters[1].includes("!")){
				$("#not2").prop('checked', true);
				$("#sdt2Filter").val(times[0].substring(7,times[0].length));
				$("#edt2Filter").val(times[1].substring(0,times[1].length-2));
			}else{
				$("#sdt2Filter").val(times[0].substring(5,times[0].length));
				$("#edt2Filter").val(times[1].substring(0,times[1].length-1));
			}
		}else if(filters[1].includes("Pos")){
			$("p#filterInfo2").empty().append(location2Filter)
			$("#filterType2").val("LOCATION");
			var cord = filters[1].split(",");
			if(filters[1].includes("!")){
				$("#not2").prop('checked', true);
				$("#slon2Filter").val(cord[0].substring(6,cord[0].length));
				$("#elon2Filter").val(cord[1]);
				$("#slat2Filter").val(cord[2]);
				$("#elat2Filter").val(cord[3].substring(0,cord[3].length-2));
			}else{
				$("#slon2Filter").val(cord[0].substring(4,cord[0].length));
				$("#elon2Filter").val(cord[1]);
				$("#slat2Filter").val(cord[2]);
				$("#elat2Filter").val(cord[3].substring(0,cord[3].length-1));
			}
		}else{ //filter2 is ID
			$("p#filterInfo2").empty().append(id2Filter)
			if(filters[1].includes("!")){
				$("#not2").prop('checked', true);
				$("#id2Filter").val(filters[1].substring(5,filters[1].length-2));
			}else{
				$("#id2Filter").val(filters[1].substring(3,filters[1].length-1));
			}
		}
	}else{
		$("#operation").val("NO OPERATION");
		if(filter.includes("Time")){
			$("#filterType1").val("TIME");
			var times = filter.split(",");
			if(filter.includes("!")){
				$("#not1").prop('checked', true);
				$("#sdt1Filter").val(times[0].substring(7,times[0].length));
				$("#edt1Filter").val(times[1].substring(0,times[1].length-2));
			}else{
				$("#sdt1Filter").val(times[0].substring(5,times[0].length));
				$("#edt1Filter").val(times[1].substring(0,times[1].length-1));
			}
		}else if(filter.includes("Pos")){
			$("#filterType1").val("LOCATION");
			var cord = filter.split(",");
			if(filter.includes("!")){
				$("#not1").prop('checked', true);
				$("#slon1Filter").val(cord[0].substring(6,times[0].length));
				$("#elon1Filter").val(cord[1]);
				$("#slat1Filter").val(cord[2]);
				$("#elat1Filter").val(cord[3].substring(0,cord[3].length-2));
			}else{
				$("#slon1Filter").val(cord[0].substring(4,times[0].length));
				$("#elon1Filter").val(cord[1]);
				$("#slat1Filter").val(cord[2]);
				$("#elat1Filter").val(cord[3].substring(0,cord[3].length-1));
			}
		}else{ //filter1 is ID
			if(filter.includes("!")){
				$("#not1").prop('checked', true);
				$("#id1Filter").val(filter.substring(5,filter.length-2));
			}else{
				$("#id1Filter").val(filter.substring(3,filter.length-1));
			}
		}
	}
}