(function($) {

	function renderThreadsChart(id, data) {
		var chart = new Taucharts.Chart({
			type : 'bar',
			x : 'threads',
			y : 'score',
			color : 'benchmark',
			data : data,
			guide : {
				x : {
					label : {
						text : 'Threads'
					}
				},
				y : {
					label : {
						text : 'Ops/sec'
					}
				}
			},
			dimensions : {
				threads : {
					type : 'order'
				},
				score : {
					type : 'measure'
				}
			},
			plugins : [ Taucharts.api.plugins.get('tooltip')({
				fields : [ 'score', 'threads' ]
			}) ]
		});
		chart.renderTo(document.getElementById(id));
	}

	function renderThreadsTable(id, data, cols) {
		var maxScore = _.max(_.map(data, function(o) {
			return o[0].score;
		}));
		_.forEach(data, function(v, k) {
			var row = $('<tr/>', {
				'class' : 'selection'
			});
			var cells = [ thead(v[0].threads) ];

			for (var i = 0; i < cols; i++) {
				cells.push(td(formatNumber(v[i].score), v[i].score === maxScore ? 'em' : ''));
			}

			row.append(cells);
			row.appendTo('#' + id);
			row.click(function() {
				$(this).toggleClass('table-active');
			});
		});
	}
	;

	function tableData(data, limit) {
		return _.flow(_.partialRight(_.take, limit), _.partialRight(_.groupBy, 'benchmark'))(data);
	}

	fetch('../data/benchmark-threads-streams-group-sequential.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderThreadsChart('sequential-group-chart', _.take(formattedData, 16), 'Threads');
		renderThreadsTable('sequential-group-table', tableData(formattedData, 16), 1);
	});
	fetch('../data/benchmark-threads-streams-group-parallel.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderThreadsChart('parallel-group-chart', _.take(formattedData, 16), 'Threads');
		renderThreadsTable('parallel-group-table', tableData(formattedData, 16), 1);
	});

	fetch('../data/benchmark-threads-streams-filter-sort-distinct-sequential.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderThreadsChart('sequential-filter-sort-distinct-chart', _.take(formattedData, 16), 'Threads');
		renderThreadsTable('sequential-filter-sort-distinct-table', tableData(formattedData, 16), 1);
	});
	fetch('../data/benchmark-threads-streams-filter-sort-distinct-parallel.json').then(function(resp) {
		return resp.text();
	}).then(function(data) {
		var formattedData = _.flow(JSON.parse, flattenJmhResults, filterData)(data);

		renderThreadsChart('parallel-filter-sort-distinct-chart', _.take(formattedData, 16), 'Threads');
		renderThreadsTable('parallel-filter-sort-distinct-table', tableData(formattedData, 16), 1);
	});

	
})(jQuery);