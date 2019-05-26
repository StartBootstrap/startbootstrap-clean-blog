(function($) {
	'use strict';

	function isPackage(s) {
		return _.startsWith(s, 'package');
	}

	function isClass(s) {
		return _.startsWith(s, 'class ') || _.startsWith(s, 'public class ') || _.startsWith(s, 'public interface ') || _.startsWith(s, 'interface ');
	}

	function isImport(s) {
		return _.startsWith(s, 'import ');
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

	function include(start, end) {
		return function(s) {
			var lines = s.split('\n');

			return _.take(_.drop(lines, start - 1), end - start + 1).join('\n');
		};
	}

	function setContent(e, f) {
		return function(c) {
			e.text(f(c));
		};
	}

	function resolveFormatter(f) {
		if (f !== undefined) {
			if (f === 'skip-class') {
				return _.flow(skipBy(isPackage), skipBy(isImport), skipBy(isClass), skipLast);
			}
			if (f.startsWith('lines ')) {
				var lines = f.substring(6).split(',');

				return include(lines[0], lines[1]);
			}
			if (f === 'full') {
				return _.identity;
			}
			if (f === 'skip-package') {
				return skipBy(isPackage);
			}
		}

		return _.flow(skipBy(isPackage), skipBy(isImport));
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

})(jQuery, Prism);