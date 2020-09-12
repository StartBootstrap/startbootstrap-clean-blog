(function($) {
	fetch('../data/benchmark-streams-sum-int.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize)(data);

		renderRelativeBySizeChart('streams-sum-int-chart', formattedData, 'Items in list');
		renderTable('streams-sum-int-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-sum-double-calculation.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize)(data);

		renderRelativeBySizeChart('streams-sum-double-calculation-chart', formattedData, 'Items in list');
		renderTable('streams-sum-double-calculation-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-group.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize, applyRelativeByBenchmark)(data);

		renderRelativeBySizeChart('streams-group-chart', formattedData, 'Items in list');
		renderTable('streams-group-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-filter-sort-distinct.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize)(data);

		renderRelativeBySizeChart('streams-filter-sort-distinct-chart', formattedData, 'Items in list');
		renderTable('streams-filter-sort-distinct-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	function tableData(data, limit) {
		return _.flow(_.partialRight(_.take, limit), _.partialRight(_.groupBy, 'benchmark'))(data);
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