(function($) {

	function renderThreadsChart(id, data) {
		var chart = new Taucharts.Chart({
			type : 'line',
			x : 'threads',
			y : 'score',
			color : 'Stream',
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
			plugins : [ Taucharts.api.plugins.get('legend')(), Taucharts.api.plugins.get('tooltip')({
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

	function tableData(data, limit) {
		return _.flow(_.partialRight(_.take, limit), _.partialRight(_.groupBy, 'benchmark'))(data);
	}

	function applyStream(stream) {
		return function(data) {
			return _.map(data, function(i) {
				i.Stream = stream;

				return i;
			});
		};
	}

	function filterSize(size) {
		return function(data) {
			return _.filter(data, function(i) {
				return i.size === size;
			});
		}
	}

	function renderChart(seqSrc, parSrc, size, id) {
		fetch(seqSrc).then(function(resp) {
			return resp.text();
		}).then(function(data) {
			var seqData = _.flow(JSON.parse, flattenJmhResults, filterSize(size), applyStream('Sequential'))(data);

			fetch(parSrc).then(function(resp) {
				return resp.text();
			}).then(function(data) {
				var parData = _.flow(JSON.parse, flattenJmhResults, filterSize(size), applyStream('Parallel'))(data);

				renderThreadsChart(id, _.concat(seqData, parData), 'Threads');
			});
		});
	}

	function renderTripleChart(seqSrc, parSrc, parConSrc, size, id) {
		fetch(seqSrc).then(function(resp) {
			return resp.text();
		}).then(function(data) {
			var seqData = _.flow(JSON.parse, flattenJmhResults, filterSize(size), applyStream('Sequential'))(data);

			fetch(parSrc).then(function(resp) {
				return resp.text();
			}).then(function(data) {
				var parData = _.flow(JSON.parse, flattenJmhResults, filterSize(size), applyStream('Parallel'))(data);

				fetch(parConSrc).then(function(resp) {
					return resp.text();
				}).then(function(data) {
					var parConData = _.flow(JSON.parse, flattenJmhResults, filterSize(size), applyStream('Parallel concurrent'))(data);

					renderThreadsChart(id, _.concat(seqData, parData, parConData), 'Threads');
				});
			});
		});
	}

	renderChart('../data/benchmark-threads-streams-sum-double-calculation-sequential.json',
			'../data/benchmark-threads-streams-sum-double-calculation-parallel.json', 1000, 'sum-double-calculation-chart');
	renderChart('../data/benchmark-threads-streams-sum-double-calculation-sequential.json',
			'../data/benchmark-threads-streams-sum-double-calculation-parallel.json', 100000, 'sum-double-calculation-large-chart');

	renderTripleChart('../data/benchmark-threads-streams-group-sequential.json', '../data/benchmark-threads-streams-group-parallel.json',
			'../data/benchmark-threads-streams-group-parallel-concurrent.json', 1000, 'group-chart');
	renderTripleChart('../data/benchmark-threads-streams-group-sequential.json', '../data/benchmark-threads-streams-group-parallel.json',
			'../data/benchmark-threads-streams-group-parallel-concurrent.json', 100000, 'group-large-chart');

	renderChart('../data/benchmark-threads-streams-filter-sort-distinct-sequential.json',
			'../data/benchmark-threads-streams-filter-sort-distinct-parallel.json', 1000, 'filter-sort-distinct-chart');
	renderChart('../data/benchmark-threads-streams-filter-sort-distinct-sequential.json',
			'../data/benchmark-threads-streams-filter-sort-distinct-parallel.json', 100000, 'filter-sort-distinct-large-chart');

})(jQuery);