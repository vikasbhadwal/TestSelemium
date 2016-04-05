/*!
 * tablesorter pager plugin
*/
;(function($) {
	"use strict";
	var ts = $.tablesorter;

	$.extend({ tablesorterPager: new function() {

		this.defaults = {
			container: null,
     		ajaxUrl: null,
     		customAjaxUrl: function(table, url) { return url; },
    		ajaxObject: {
				dataType: 'json'
			},
			ajaxProcessing: function(ajax){ return [ 0, [], null ]; },
    		output: '{startRow} to {endRow} of {totalRows} rows', // '{page}/{totalPages}'
    		updateArrows: true,
     		page: 0,
     		size: 10,
			savePages: true,
			fixedHeight: false,
			countChildRows: false,
             removeRows: false, // removing rows in larger tables speeds up the sort
             cssFirst: '.first', // go to first page arrow
			cssPrev: '.prev', // previous page arrow
			cssNext: '.next', // next page arrow
			cssLast: '.last', // go to last page arrow
			cssGoto: '.gotoPage', // go to page selector - select dropdown that sets the current page
			cssPageDisplay: '.pagedisplay', // location of where the "output" is displayed
			cssPageSize: '.pagesize', // page size selector - select dropdown that sets the "size" option
			cssErrorRow: 'tablesorter-errorRow', 
			cssDisabled: 'disabled', 
			totalRows: 0,
			totalPages: 0,
			filteredRows: 0,
			filteredPages: 0,
			currentFilters: [],
			startRow: 0,
			endRow: 0,
			$size: null,
			last: {}

		};

		var $this = this,

		// hide arrows at extremes
		pagerArrows = function(p, disable) {
			var a = 'addClass',
			r = 'removeClass',
			d = p.cssDisabled,
			dis = !!disable,
			tp = Math.min( p.totalPages, p.filteredPages );
			if ( p.updateArrows ) {
				p.$container.find(p.cssFirst + ',' + p.cssPrev)[ ( dis || p.page === 0 ) ? a : r ](d);
				p.$container.find(p.cssNext + ',' + p.cssLast)[ ( dis || p.page === tp - 1 ) ? a : r ](d);
			}
		},

		updatePageDisplay = function(table, p, flag) {
			var i, pg, s, out,
				c = table.config,
				f = c.$table.hasClass('hasFilters') && !p.ajaxUrl,
				t = (c.widgetOptions && c.widgetOptions.filter_filteredRow || 'filtered') + ',' + c.selectorRemove,
				sz = p.size || 10; // don't allow dividing by zero
			p.totalPages = Math.ceil( p.totalRows / sz ); // needed for "pageSize" method
			p.filteredRows = (f) ? c.$tbodies.eq(0).children('tr:not(.' + t + ')').length : p.totalRows;
			p.filteredPages = (f) ? Math.ceil( p.filteredRows / sz ) || 1 : p.totalPages;
			if ( Math.min( p.totalPages, p.filteredPages ) >= 0 ) {
				t = (p.size * p.page > p.filteredRows);
				p.startRow = (t) ? 1 : (p.filteredRows === 0 ? 0 : p.size * p.page + 1);
				p.page = (t) ? 0 : p.page;
				p.endRow = Math.min( p.filteredRows, p.totalRows, p.size * ( p.page + 1 ) );
				out = p.$container.find(p.cssPageDisplay);
				// form the output string (can now get a new output string from the server)
				s = ( p.ajaxData && p.ajaxData.output ? p.ajaxData.output || p.output : p.output )
					.replace(/\{page([\-+]\d+)?\}/gi, function(m,n){
						return p.page + (n ? parseInt(n, 10) : 1);
					})
					.replace(/\{\w+(\s*:\s*\w+)?\}/gi, function(m){
						var t = m.replace(/[{}\s]/g,''), a = t.split(':'), d = p.ajaxData;
						return a.length > 1 && d && d[a[0]] ? d[a[0]][a[1]] : p[t] || (d ? d[t] : '') || '';
					});
				if (out.length) {
					out[ (out[0].tagName === 'INPUT') ? 'val' : 'html' ](s);
					if ( p.$goto.length ) {
						t = '';
						pg = Math.min( p.totalPages, p.filteredPages );
						for ( i = 1; i <= pg; i++ ) {
							t += '<option>' + i + '</option>';
						}
						p.$goto.html(t).val( p.page + 1 );
					}
				}
			}
			pagerArrows(p);
			if (p.initialized && flag !== false) {
				c.$table.trigger('pagerComplete', p);
				if (p.savePages && ts.storage) {
					ts.storage(table, 'tablesorter-pager', {
						page : p.page,
						size : p.size
					});
				}
			}
		},

		fixHeight = function(table, p) {
			var d, h,
				c = table.config,
				$b = c.$tbodies.eq(0);
			if (p.fixedHeight) {
				$b.find('tr.pagerSavedHeightSpacer').remove();
				h = $.data(table, 'pagerSavedHeight');
				if (h) {
					d = h - $b.height();
				}
			}
		},

		changeHeight = function(table, p) {
			var $b = table.config.$tbodies.eq(0);
			$b.find('tr.pagerSavedHeightSpacer').remove();
			$.data(table, 'pagerSavedHeight', $b.height());
			fixHeight(table, p);
			$.data(table, 'pagerLastSize', p.size);
		},

		hideRows = function(table, p){
			if (!p.ajaxUrl) {
				var i,
				c = table.config,
				rows = c.$tbodies.eq(0).children(),
				l = rows.length,
				s = ( p.page * p.size ),
				e =  s + p.size,
				f = c.widgetOptions && c.widgetOptions.filter_filteredRow || 'filtered',
				j = 0; // size counter
				for ( i = 0; i < l; i++ ){
					if ( !rows[i].className.match(f) ) {
						rows[i].style.display = ( j >= s && j < e ) ? '' : 'none';
						// don't count child rows
						j += rows[i].className.match(c.cssChildRow + '|' + c.selectorRemove.slice(1)) && !p.countChildRows ? 0 : 1;
					}
				}
			}
		},

		hideRowsSetup = function(table, p){
			p.size = parseInt( p.$size.val(), 10 ) || p.size;
			$.data(table, 'pagerLastSize', p.size);
			pagerArrows(p);
			if ( !p.removeRows ) {
				hideRows(table, p);
				$(table).bind('sortEnd.pager filterEnd.pager', function(){
					hideRows(table, p);
				});
			}
		},

		renderAjax = function(data, table, p, xhr, exception){
			if ( typeof(p.ajaxProcessing) === "function" ) {
				var i, j, hsh, $f, $sh, t, th, d, l, $err, rr_count,
					c = table.config,
					$t = c.$table,
					tds = '',
					result = p.ajaxProcessing(data, table) || [ 0, [] ],
					hl = $t.find('thead th').length;

				$t.find('thead tr.' + p.cssErrorRow).remove(); // Clean up any previous error.

				if ( exception ) {
					if (c.debug) {
						ts.log('Ajax Error', xhr, exception);
					}
					$err = $('<tr class="' + p.cssErrorRow + '"><td style="text-align:center;" colspan="' + hl + '">' + (
						xhr.status === 0 ? 'Not connected, verify Network' :
						xhr.status === 404 ? 'Requested page not found [404]' :
						xhr.status === 500 ? 'Internal Server Error [500]' :
						exception === 'parsererror' ? 'Requested JSON parse failed' :
						exception === 'timeout' ? 'Time out error' :
						exception === 'abort' ? 'Ajax Request aborted' :
						'Uncaught error: ' + xhr.statusText + ' [' + xhr.status + ']' ) + '</td></tr>')
					.click(function(){
						$(this).remove();
					})
					.appendTo( $t.find('thead:first') );
					c.$tbodies.eq(0).empty();
				} else {
					// process ajax object
					if (!$.isArray(result)) {
						p.ajaxData = result;
						p.totalRows = result.total;
						th = result.headers;
						d = result.rows;
					} else {
						t = isNaN(result[0]) && !isNaN(result[1]);
						rr_count = result[t ? 1 : 0];
						p.totalRows = isNaN(rr_count) ? p.totalRows || 0 : rr_count;
						d = result[t ? 0 : 1] || []; // row data
						th = result[2]; // headers
					}
					l = d.length;
					if (d instanceof jQuery) {
						// append jQuery object
						c.$tbodies.eq(0).empty().append(d);
					} else if (l) {
						// build table from array
						for ( i = 0; i < l; i++ ) {
							tds += '<tr>';
							for ( j = 0; j < d[i].length; j++ ) {
								// build tbody cells
								tds += '<td>' + d[i][j] + '</td>';
							}
							tds += '</tr>';
						}
						// add rows to first tbody
						c.$tbodies.eq(0).html( tds );
					}
					// only add new header text if the length matches
					if ( th && th.length === hl ) {
						hsh = $t.hasClass('hasStickyHeaders');
						$sh = hsh ? c.$sticky.children('thead:first').children().children() : '';
						$f = $t.find('tfoot tr:first').children();
						// don't change td headers (may contain pager)
						c.$headers.filter('th').each(function(j){
							var $t = $(this), icn;
							// add new test within the first span it finds, or just in the header
							if ( $t.find('.' + ts.css.icon).length ) {
								icn = $t.find('.' + ts.css.icon).clone(true);
								$t.find('.tablesorter-header-inner').html( th[j] ).append(icn);
								if ( hsh && $sh.length ) {
									icn = $sh.eq(j).find('.' + ts.css.icon).clone(true);
									$sh.eq(j).find('.tablesorter-header-inner').html( th[j] ).append(icn);
								}
							} else {
								$t.find('.tablesorter-header-inner').html( th[j] );
								if (hsh && $sh.length) {
									$sh.eq(j).find('.tablesorter-header-inner').html( th[j] );
								}
							}
							$f.eq(j).html( th[j] );
						});
					}
				}
				if (c.showProcessing) {
					ts.isProcessing(table); // remove loading icon
				}
				p.last.totalPages =  p.totalPages = Math.ceil( p.totalRows / ( p.size || 10 ) );
				p.last.currentFilters = p.currentFilters;
				p.last.sortList = (c.sortList || []).join(',');
				updatePageDisplay(table, p);
				fixHeight(table, p);
				$t.trigger('applyWidgets');
				if (p.initialized) {
					$t.trigger('pagerChange', p);
					$t.trigger('updateComplete');
				} else {
					$t.trigger('update');
				}
			}
			if (!p.initialized) {
				p.initialized = true;
				$(table).trigger('pagerInitialized', p);
			}
		},

		getAjax = function(table, p){
			var url = getAjaxUrl(table, p),
			$doc = $(document),
			c = table.config;
			if ( url !== '' ) {
				if (c.showProcessing) {
					ts.isProcessing(table, true); // show loading icon
				}
				$doc.bind('ajaxError.pager', function(e, xhr, settings, exception) {
					renderAjax(null, table, p, xhr, exception);
					$doc.unbind('ajaxError.pager');
				});
				p.ajaxObject.url = url; // from the ajaxUrl option and modified by customAjaxUrl
				p.ajaxObject.success = function(data) {
					renderAjax(data, table, p);
					$doc.unbind('ajaxError.pager');
					if (typeof p.oldAjaxSuccess === 'function') {
						p.oldAjaxSuccess(data);
					}
				};
				if (c.debug) {
					ts.log('ajax initialized', p.ajaxObject);
				}
				$.ajax(p.ajaxObject);
			}
		},

		getAjaxUrl = function(table, p) {
			var c = table.config,
				url = (p.ajaxUrl) ? p.ajaxUrl
				// allow using "{page+1}" in the url string to switch to a non-zero based index
				.replace(/\{page([\-+]\d+)?\}/, function(s,n){ return p.page + (n ? parseInt(n, 10) : 0); })
				.replace(/\{size\}/g, p.size) : '',
			sl = c.sortList,
			fl = p.currentFilters || $(table).data('lastSearch') || [],
			sortCol = url.match(/\{\s*sort(?:List)?\s*:\s*(\w*)\s*\}/),
			filterCol = url.match(/\{\s*filter(?:List)?\s*:\s*(\w*)\s*\}/),
			arry = [];
			if (sortCol) {
				sortCol = sortCol[1];
				$.each(sl, function(i,v){
					arry.push(sortCol + '[' + v[0] + ']=' + v[1]);
				});
				// if the arry is empty, just add the col parameter... "&{sortList:col}" becomes "&col"
				url = url.replace(/\{\s*sort(?:List)?\s*:\s*(\w*)\s*\}/g, arry.length ? arry.join('&') : sortCol );
				arry = [];
			}
			if (filterCol) {
				filterCol = filterCol[1];
				$.each(fl, function(i,v){
					if (v) {
						arry.push(filterCol + '[' + i + ']=' + encodeURIComponent(v));
					}
				});
				// if the arry is empty, just add the fcol parameter... "&{filterList:fcol}" becomes "&fcol"
				url = url.replace(/\{\s*filter(?:List)?\s*:\s*(\w*)\s*\}/g, arry.length ? arry.join('&') : filterCol );
				p.currentFilters = fl;
			}
			if ( typeof(p.customAjaxUrl) === "function" ) {
				url = p.customAjaxUrl(table, url);
			}
			if (c.debug) {
				ts.log('Pager ajax url: ' + url);
			}
			return url;
		},
		renderTable = function(table, rows, p) {
			var i, $tb,
				l = rows && rows.length || 0, // rows may be undefined
				s = ( p.page * p.size ),
				e = ( s + p.size );
			if ( l < 1 ) { return; } // empty table, abort!
			if ( p.page >= p.totalPages ) {
				moveToLastPage(table, p);
			}
			p.isDisabled = false; // needed because sorting will change the page and re-enable the pager
			if (p.initialized) { $(table).trigger('pagerChange', p); }

			if ( !p.removeRows ) {
				hideRows(table, p);
			} else {
				if ( e > rows.length ) {
					e = rows.length;
				}
				ts.clearTableBody(table);
				$tb = ts.processTbody(table, table.config.$tbodies.eq(0), true);
				for ( i = s; i < e; i++ ) {
					$tb.append(rows[i]);
				}
				ts.processTbody(table, $tb, false);
			}

			updatePageDisplay(table, p);
			if ( !p.isDisabled ) { fixHeight(table, p); }
			$(table).trigger('applyWidgets');
		},

		showAllRows = function(table, p){
			if ( p.ajax ) {
				pagerArrows(p, true);
			} else {
				p.isDisabled = true;
				$.data(table, 'pagerLastPage', p.page);
				$.data(table, 'pagerLastSize', p.size);
				p.page = 0;
				p.size = p.totalRows;
				p.totalPages = 1;
				$(table).addClass('pagerDisabled').find('tr.pagerSavedHeightSpacer').remove();
				renderTable(table, table.config.rowsCopy, p);
				if (table.config.debug) {
					ts.log('pager disabled');
				}
			}
			p.$size.add(p.$goto).each(function(){
				$(this).addClass(p.cssDisabled)[0].disabled = true;
			});
		},

		moveToPage = function(table, p, flag) {
			if ( p.isDisabled ) { return; }
			var c = table.config,
				l = p.last,
				pg = Math.min( p.totalPages, p.filteredPages );
			if ( p.page < 0 ) { p.page = 0; }
			if ( p.page > ( pg - 1 ) && pg !== 0 ) { p.page = pg - 1; }
			// don't allow rendering multiple times on the same page/size/totalpages/filters/sorts
			if ( l.page === p.page && l.size === p.size && l.totalPages === p.totalPages &&
				(l.currentFilters || []).join(',') === (p.currentFilters || []).join(',') &&
				l.sortList === (c.sortList || []).join(',') ) { return; }
			if (c.debug) {
				ts.log('Pager changing to page ' + p.page);
			}
			p.last = {
				page : p.page,
				size : p.size,
				sortList : (c.sortList || []).join(','),
				totalPages : p.totalPages,
				currentFilters : p.currentFilters || []
			};
			if (p.ajax) {
				getAjax(table, p);
			} else if (!p.ajax) {
				renderTable(table, table.config.rowsCopy, p);
			}
			$.data(table, 'pagerLastPage', p.page);
			if (p.initialized && flag !== false) {
				c.$table.trigger('pageMoved', p);
				c.$table.trigger('applyWidgets');
			}
		},

		setPageSize = function(table, size, p) {
			p.size = size || p.size || 10;
			p.$size.val(p.size);
			$.data(table, 'pagerLastPage', p.page);
			$.data(table, 'pagerLastSize', p.size);
			p.totalPages = Math.ceil( p.totalRows / p.size );
			moveToPage(table, p);
		},

		moveToFirstPage = function(table, p) {
			p.page = 0;
			moveToPage(table, p);
		},

		moveToLastPage = function(table, p) {
			p.page = ( Math.min( p.totalPages, p.filteredPages ) - 1 );
			moveToPage(table, p);
		},

		moveToNextPage = function(table, p) {
			p.page++;
			if ( p.page >= ( Math.min( p.totalPages, p.filteredPages ) - 1 ) ) {
				p.page = ( Math.min( p.totalPages, p.filteredPages ) - 1 );
			}
			moveToPage(table, p);
		},

		moveToPrevPage = function(table, p) {
			p.page--;
			if ( p.page <= 0 ) {
				p.page = 0;
			}
			moveToPage(table, p);
		},

		destroyPager = function(table, p){
			showAllRows(table, p);
			p.$container.hide(); // hide pager
			table.config.appender = null; // remove pager appender function
			p.initialized = false;
			$(table).unbind('destroy.pager sortEnd.pager filterEnd.pager enable.pager disable.pager');
			if (ts.storage) {
				ts.storage(table, 'tablesorter-pager', '');
			}
		},

		enablePager = function(table, p, triggered){
			var pg = p.$size.removeClass(p.cssDisabled).removeAttr('disabled');
			p.$goto.removeClass(p.cssDisabled).removeAttr('disabled');
			p.isDisabled = false;
			p.page = $.data(table, 'pagerLastPage') || p.page || 0;
			p.size = $.data(table, 'pagerLastSize') || parseInt(pg.find('option[selected]').val(), 10) || p.size || 10;
			pg.val(p.size); // set page size
			p.totalPages = Math.ceil( Math.min( p.totalPages, p.filteredPages ) / p.size );
			if ( triggered ) {
				$(table).trigger('update');
				setPageSize(table, p.size, p);
				hideRowsSetup(table, p);
				fixHeight(table, p);
				if (table.config.debug) {
					ts.log('pager enabled');
				}
			}
		};

		$this.appender = function(table, rows) {
			var c = table.config,
				p = c.pager;
			if ( !p.ajax ) {
				c.rowsCopy = rows;
				p.totalRows = p.countChildRows ? c.$tbodies.eq(0).children().length : rows.length;
				p.size = $.data(table, 'pagerLastSize') || p.size || 10;
				p.totalPages = Math.ceil( p.totalRows / p.size );
				renderTable(table, rows, p);
			}
		};

		$this.construct = function(settings) {
			return this.each(function() {
				if (!(this.config && this.hasInitialized)) { return; }
				var t, ctrls, fxn,
					table = this,
					c = table.config,
					p = c.pager = $.extend( {}, $.tablesorterPager.defaults, settings ),
					$t = c.$table,
					pager = p.$container = $(p.container).addClass('tablesorter-pager').show();
				if (c.debug) {
					ts.log('Pager initializing');
				}
				p.oldAjaxSuccess = p.oldAjaxSuccess || p.ajaxObject.success;
				c.appender = $this.appender;
				if (ts.filter && $.inArray('filter', c.widgets) >= 0) {
					p.currentFilters = c.$table.data('lastSearch') || ts.filter.setDefaults(table, c, c.widgetOptions) || [];
					ts.setFilters(table, p.currentFilters, false);
				}
				if (p.savePages && ts.storage) {
					t = ts.storage(table, 'tablesorter-pager') || {}; // fixes #387
					p.page = isNaN(t.page) ? p.page : t.page;
					p.size = ( isNaN(t.size) ? p.size : t.size ) || 10;
					$.data(table, 'pagerLastSize', p.size);
				}

				$t.unbind('filterStart filterEnd sortEnd disable enable destroy update pageSize '.split(' ').join('.pager '))
					.bind('filterStart.pager', function(e, filters) {
						p.currentFilters = filters;
					})
					.bind('filterEnd.pager sortEnd.pager', function() {
						if (p.initialized) {
							moveToPage(table, p, false);
							updatePageDisplay(table, p, false);
							fixHeight(table, p);
						}
					})
					.bind('disable.pager', function(e){
						e.stopPropagation();
						showAllRows(table, p);
					})
					.bind('enable.pager', function(e){
						e.stopPropagation();
						enablePager(table, p, true);
					})
					.bind('destroy.pager', function(e){
						e.stopPropagation();
						destroyPager(table, p);
					})
					.bind('update.pager', function(e){
						e.stopPropagation();
						hideRows(table, p);
					})
					.bind('pageSize.pager', function(e,v){
						e.stopPropagation();
						setPageSize(table, parseInt(v, 10) || 10, p);
						hideRows(table, p);
						updatePageDisplay(table, p, false);
						if (p.$size.length) { p.$size.val(p.size); } // twice?
					})
					.bind('pageSet.pager', function(e,v){
						e.stopPropagation();
						p.page = (parseInt(v, 10) || 1) - 1;
						if (p.$goto.length) { p.$goto.val(p.size); } // twice?
						moveToPage(table, p);
						updatePageDisplay(table, p, false);
					});
				ctrls = [ p.cssFirst, p.cssPrev, p.cssNext, p.cssLast ];
				fxn = [ moveToFirstPage, moveToPrevPage, moveToNextPage, moveToLastPage ];
				pager.find(ctrls.join(','))
					.unbind('click.pager')
					.bind('click.pager', function(e){
						e.stopPropagation();
						var i, $t = $(this), l = ctrls.length;
						if ( !$t.hasClass(p.cssDisabled) ) {
							for (i = 0; i < l; i++) {
								if ($t.is(ctrls[i])) {
									fxn[i](table, p);
									break;
								}
							}
						}
					});
				p.$goto = pager.find(p.cssGoto);
				if ( p.$goto.length ) {
					p.$goto
						.unbind('change')
						.bind('change', function(){
							p.page = $(this).val() - 1;
							moveToPage(table, p);
							updatePageDisplay(table, p, false);
						});
				}
				p.$size = pager.find(p.cssPageSize);
				if ( p.$size.length ) {
					p.$size.unbind('change.pager').bind('change.pager', function() {
						p.$size.val( $(this).val() ); // in case there are more than one pagers
						if ( !$(this).hasClass(p.cssDisabled) ) {
							setPageSize(table, parseInt( $(this).val(), 10 ), p);
							changeHeight(table, p);
						}
						return false;
					});
				}
                p.initialized = false;
				$t.trigger('pagerBeforeInitialized', p);
				enablePager(table, p, false);
				if ( typeof(p.ajaxUrl) === 'string' ) {
					p.ajax = true;
					c.widgetOptions.filter_serversideFiltering = true;
					c.serverSideSorting = true;
					moveToPage(table, p);
				} else {
					p.ajax = false;
					// Regular pager; all rows stored in memory
					$(this).trigger("appendCache", true);
					hideRowsSetup(table, p);
				}

				changeHeight(table, p);
				if (!p.ajax) {
					p.initialized = true;
					$(table).trigger('pagerInitialized', p);
				}
			});
		};

	}()
});

$.fn.extend({
	tablesorterPager: $.tablesorterPager.construct
});

})(jQuery);
