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

	function applyRelative(data) {
		var maxBySize = _.chain(data).groupBy('size').map(function(v, k) {
			var max = _.maxBy(v, 'score').score;

			return {
				size : k,
				maxScore : max

			};
		}).reduce(function(a, v, k) {
			a[v.size] = v.maxScore;

			return a;
		}, {}).value();

		return _.map(data, function(i) {
			i.relativeScore = (i.score / maxBySize[i.size]) * 100;

			return i;
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

	function renderRelativeChart(id, data, xlabel, scale) {
		var chart = new Taucharts.Chart({
			type : 'bar',
			x : 'size',
			y : 'relativeScore',
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
						text : '% score'
					}
				}
			},
			dimensions : {
				size : {
					type : 'order'
				},
				relativeScore : {
					type : 'measure'
				}
			},
			plugins : [ Taucharts.api.plugins.get('legend')(), Taucharts.api.plugins.get('tooltip')({
				fields : [ 'relativeScore', 'benchmark' ]
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
						text : 'Ops/sec'
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

	function td(text, cls) {
		return '<td class="text-right ' + cls + '">' + text + '</td>';
	}

	function formatNumber(number) {
		return numeral(number).format('0,000.00');
	}

	function formatPercent(number) {
		return numeral(number).format('0.00 %');
	}

	function renderTable(id, data, cols) {
		_.forEach(data, function(v, k) {
			var row = $('<tr/>', {
				'class' : 'selection'
			});
			var cells = [ thead(k) ];

			for (var i = 0; i < cols; i++) {
				cells.push(td(formatNumber(v[i].score), v[i].relativeScore === 100 ? 'em' : ''));
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
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelative)(data);

		renderRelativeChart('streams-sum-int-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-sum-int-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-sum-double-calculation.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelative)(data);

		renderRelativeChart('streams-sum-double-calculation-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-sum-double-calculation-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-group.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelative)(data);

		renderRelativeChart('streams-group-chart', formattedData, 'Items in list', 'logarithmic');
		renderTable('streams-group-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	function tableData(data, limit) {
		return _.flow(_.partialRight(_.take, limit), applyRelative, _.partialRight(_.groupBy, 'benchmark'))(data);
	}

	fetch('../data/benchmark-wfc.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderChart('wfc-chart', _.take(formattedData, 2), 'Benchmark');
		renderTable('wfc-table', tableData(formattedData, 2), 1);

		renderChart('wfc-chart1', _.take(formattedData, 3), 'Benchmark');
		renderTable('wfc-table1', tableData(formattedData, 3), 1);

		renderChart('wfc-chart2', _.take(formattedData, 4), 'Benchmark');
		renderTable('wfc-table2', tableData(formattedData, 4), 1);

		renderChart('wfc-chart3', _.take(formattedData, 5), 'Benchmark');
		renderTable('wfc-table3', tableData(formattedData, 5), 1);

		renderChart('wfc-chart4', _.take(formattedData, 6), 'Benchmark');
		renderTable('wfc-table4', tableData(formattedData, 6), 1);

	});

})(jQuery);
