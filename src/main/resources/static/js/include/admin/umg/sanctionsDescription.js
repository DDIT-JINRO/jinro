openNewPenaltyModalBtn = document.getElementById('openNewPenaltyModalBtn');
cancelBtn = document.getElementById('cancelBtn');
penaltyModal = document.getElementById('penaltyModal');

function openModal() {
	if (penaltyModal) {
		penaltyModal.classList.add('visible');
	}
}

function closeModal() {
	if (penaltyModal) {
		penaltyModal.classList.remove('visible');
	}
}

openNewPenaltyModalBtn.addEventListener('click', openModal);
cancelBtn.addEventListener('click', closeModal);