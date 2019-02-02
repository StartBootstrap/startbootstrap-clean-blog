(function($) {
	'use strict';

	function isPackage(s) {
		return _.startsWith(s, 'package');
	}

	function isClass(s) {
		return _.startsWith(s, 'public class');
	}

	function skipBy(p) {
		return function(s) {
			return _.trimStart(_.filter(s.split('\n'), _.negate(p)).join('\n'), '\n');
		};
	}

	function skipLast(s) {
		var lines = s.split('\n');

		return _.take(lines, lines.length - 2).join('\n');
	}

	function setContent(e, f) {
		return function(c) {
			e.text(f(c));
		};
	}

	function resolveFormatter(f) {
		if (f === 'skip-class') {
			return _.flow(skipBy(isPackage), skipBy(isClass), skipLast);
		}

		return skipBy(isPackage);
	}

	$('pre code').each(function() {
		var e = $(this);

		if (e.attr('data-src')) {
			fetch(e.attr('data-src')).then(function(resp) {
				return resp.text();
			}).then(setContent(e, resolveFormatter(e.attr('data-format')))).then(function() {
				Prism.highlightElement(e[0]);
			});
		}
	});

})(jQuery);