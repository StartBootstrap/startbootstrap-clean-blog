(function($) {
	'use strict';

	var defLineHeight = 20;
	var editorOptions = {
		readOnly : true,
		printMargin : false,
		fontSize : 16
	};

	function lineHeight(v) {
		return (v * defLineHeight) + 'px';
	}

	$('.editor').each(function() {
		var e = ace.edit($(this).attr('id'));

		e.setTheme('ace/theme/github');
		e.session.setMode('ace/mode/java');
		e.setOptions(editorOptions);
		$(this).css('display', 'block').css('height', lineHeight($(this).attr('data-lines')));
	});
})(jQuery);