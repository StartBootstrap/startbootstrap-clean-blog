(function($) {
	fetch('../data/benchmark-streams-sum-int-parallel.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize)(data);

		renderRelativeBySizeChart('streams-sum-int-chart', formattedData, 'Items in list');
		renderTable('streams-sum-int-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

	fetch('../data/benchmark-streams-sum-double-calculation-parallel.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData, applyRelativeBySize)(data);

		renderRelativeBySizeChart('streams-sum-double-calculation-chart', formattedData, 'Items in list');
		renderTable('streams-sum-double-calculation-table', _.groupBy(formattedData, 'benchmark'), 4);
	});

})(jQuery);