$(function()
{
	var pagerOptions = {
		container: $(".pager"),
		ajaxUrl: null,
	customAjaxUrl: function(table, url) { return url; },
	ajaxProcessing: function(ajax){
	if (ajax && ajax.hasOwnProperty('data')) {
				return [ ajax.total_rows, ajax.data ];
			}
		},

	output: '{startRow} to {endRow} ({totalRows})',
	updateArrows: true,
	page: 0,
	size: 3,
	savePages : true,
	fixedHeight: true,
	removeRows: false,
	cssNext: '.next', // next page arrow
	cssPrev: '.prev', // previous page arrow
	cssFirst: '.first', // go to first page arrow
	cssLast: '.last', // go to last page arrow
	cssGoto: '.gotoPage', // select dropdown to allow choosing a page
	cssPageDisplay: '.pagedisplay', // location of where the "output" is displayed
	cssPageSize: '.pagesize', 
	cssDisabled: 'disabled', // Note there is no period "." in front of this class name
	cssErrorRow: 'tablesorter-errorRow' // ajax error information row
	};
	$("table").tablesorter({
			theme: 'blue',
			widthFixed: true,
			widgets: ['zebra']
		}).bind('pagerChange pagerComplete pagerInitialized pageMoved', function(e, c){
			var msg = '"</span> event triggered, ' + (e.type === 'pagerChange' ? 'going to' : 'now on') +
				' page <span class="typ">' + (c.page + 1) + '/' + c.totalPages + '</span>';
			$('#display').append('<li><span class="str">"' + e.type + msg + '</li>').find('li:first').remove();
		}).tablesorterPager(pagerOptions);
		var r, $row, num = 15,
	
row = '<tr><td>Student{i}</td><td>{m}</td><td>{g}</td><td>{r}</td><td>{r}</td><td>{r}</td><td>{r}</td></tr><tr><td>Student{j}</td><td>{m}</td><td>{g}</td><td>{r}</td><td>{r}</td><td>{r}</td><td>{r}</td></tr>';

	
	
});
	$(document).ready(function() {
		$('table').filterTable({});
	});
