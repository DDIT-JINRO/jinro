function openCounselingPopup(url) {
    // 팝업 창의 크기와 위치를 설정합니다.
    const width = 1200;
    const height = 800;
    const left = (screen.width / 2) - (width / 2);
    const top = (screen.height / 2) - (height / 2);

    // 새 팝업 창을 엽니다.
    window.open(url, 'counselingPopup', `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`);
}

document.addEventListener('DOMContentLoaded', function() {
    const toggleButton = document.getElementById('search-filter-toggle');
    
    const panel = document.getElementById('search-filter-panel');

    if (toggleButton && panel) {
        toggleButton.addEventListener('click', function() {
            const isActive = this.classList.contains('is-active');

            if (isActive) {
                this.classList.remove('is-active');
                panel.classList.remove('is-open');
            } else {
                this.classList.add('is-active');
                panel.classList.add('is-open');
            }
        });
    }
});