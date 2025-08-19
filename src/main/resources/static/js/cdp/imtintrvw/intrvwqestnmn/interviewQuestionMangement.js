/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	const toggle = document.getElementById('search-filter-toggle');
	const panel = document.getElementById('search-filter-panel');

	if (toggle && panel) {
		toggle.addEventListener('click', function() {
			const isOpen = panel.classList.contains('is-open');

			if (isOpen) {
				panel.classList.remove('is-open');
				toggle.classList.remove('is-active');
			} else {
				panel.classList.add('is-open');
				toggle.classList.add('is-active');
			}
		});
	}
});