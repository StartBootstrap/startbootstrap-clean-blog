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
		return _.sortBy(_.filter(data, function(i) {
			return true;
		}), 'score');
	}

	function renderChart(id, data) {
		new Taucharts.Chart({
			type : 'bar',
			x : 'size',
			y : 'score',
			color : 'benchmark',
			data : data,
			guide : {
				x : {
					label : {
						text : 'Items in list'
					}
				},
				y : {
					label : {
						text : 'Time in microseconds'
					}
				}
			},
			dimensions : {
				size : {
					type : 'order'
				},
				score : {
					type : 'measure',
					scale : 'logarithmic'
				}
			},
			plugins : [ Taucharts.api.plugins.get('legend')(), Taucharts.api.plugins.get('tooltip')({
				fields : [ 'score', 'benchmark' ]
			}) ]
		}).renderTo(document.getElementById(id));
	}

	function thead(text) {
		return '<th>' + text + '</th>';
	}

	function td(text) {
		return '<td class="text-right">' + text + '</td>';
	}

	function formatNumber(number) {
		return numeral(number).format('0,0.00');
	}

	function renderTable(id, data) {
		_.forEach(data, function(v, k) {
			$('#' + id).append(
					'<tr>' + thead(k) + td(formatNumber(v[0].score)) + td(formatNumber(v[1].score)) + td(formatNumber(v[2].score))
							+ td(formatNumber(v[3].score)) + '</tr>');
		});
	}

	fetch('../data/benchmark-streams-sum-int.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderChart('streams-sum-int-chart', formattedData);
		renderTable('streams-sum-int-table', _.groupBy(formattedData, 'benchmark'));
	});

	fetch('../data/benchmark-streams-sum-double-calculation.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderChart('streams-sum-double-calculation-chart', formattedData);
		renderTable('streams-sum-double-calculation-table', _.groupBy(formattedData, 'benchmark'));
	});

	fetch('../data/benchmark-streams-filter-group.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderChart('streams-filter-group-chart', formattedData);
		renderTable('streams-filter-group-table', _.groupBy(formattedData, 'benchmark'));
	});

})(jQuery);
