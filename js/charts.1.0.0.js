(function($) {
	function flattenJmhResults(data) {
		return _.map(data, function(i) {
			return {
				benchmark : _.last(_.get(i, 'benchmark').split('.')),
				size : _.parseInt(_.get(i, 'params.size')),
				score : _.get(i, 'primaryMetric.score')
			};
		});
	}

	function filterData(data) {
		return _.filter(data, function(i) {
			return true;
		});
	}

	function renderSeriesChart(id, data, xlabel, scale) {
		var chart = new Taucharts.Chart({
			type : 'bar',
			x : 'size',
			y : 'score',
			color : 'benchmark',
			data : data,
			guide : {
				x : {
					label : {
						text : xlabel
					}
				},
				y : {
					label : {
						text : 'Number of operations per second'
					}
				}
			},
			dimensions : {
				size : {
					type : 'order'
				},
				score : {
					type : 'measure',
					scale : scale
				}
			},
			plugins : [ Taucharts.api.plugins.get('legend')(), Taucharts.api.plugins.get('tooltip')({
				fields : [ 'score', 'benchmark' ]
			}) ]
		});
		chart.renderTo(document.getElementById(id));
	}

	function renderChart(id, data) {
		var chart = new Taucharts.Chart({
			type : 'bar',
			x : 'benchmark',
			y : 'score',
			color : 'benchmark',
			data : data,
			guide : {
				x : {
					label : {
						text : 'Benchmark'
					}
				},
				y : {
					label : {
						text : 'Time in microseconds'
					}
				}
			},
			dimensions : {
				benchmark : {
					type : 'order'
				},
				score : {
					type : 'measure'
				}
			},
			plugins : [ Taucharts.api.plugins.get('tooltip')({
				fields : [ 'score', 'benchmark' ]
			}) ]
		});
		chart.renderTo(document.getElementById(id));
	}

	function thead(text) {
		return '<th>' + text + '</th>';
	}

	function td(text) {
		return '<td class="text-right">' + text + '</td>';
	}

	function formatNumber(number) {
		return numeral(number).format('0,000.00');
	}

	function renderTable(id, data, cols) {
		_.forEach(data, function(v, k) {
			var row = $('<tr/>', {
				'class' : 'selection'
			});
			var cells = [ thead(k) ];

			for (var i = 0; i < cols; i++) {
				cells.push(td(formatNumber(v[i].score)));
			}

			row.append(cells);
			row.appendTo('#' + id);
			row.click(function() {
				$(this).toggleClass('table-active');
			});
		});
	}

	fetch('../data/benchmark-streams-sum-int.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderSeriesChart('streams-sum-int-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-sum-int-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-sum-double-calculation.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderSeriesChart('streams-sum-double-calculation-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-sum-double-calculation-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-filter-group.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderSeriesChart('streams-filter-group-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-filter-group-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-wfc.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderChart('wfc-chart', _.take(formattedData, 2), 'Benchmark');
		renderTable('wfc-table', _.groupBy(_.take(formattedData, 2), 'benchmark'), 1);

		renderChart('wfc-chart1', _.take(formattedData, 3), 'Benchmark');
		renderTable('wfc-table1', _.groupBy(_.take(formattedData, 3), 'benchmark'), 1);

		renderChart('wfc-chart2', _.take(formattedData, 4), 'Benchmark');
		renderTable('wfc-table2', _.groupBy(_.take(formattedData, 4), 'benchmark'), 1);

		renderChart('wfc-chart3', _.take(formattedData, 5), 'Benchmark');
		renderTable('wfc-table3', _.groupBy(_.take(formattedData, 5), 'benchmark'), 1);

		renderChart('wfc-chart4', _.take(formattedData, 6), 'Benchmark');
		renderTable('wfc-table4', _.groupBy(_.take(formattedData, 6), 'benchmark'), 1);

	});

})(jQuery);
