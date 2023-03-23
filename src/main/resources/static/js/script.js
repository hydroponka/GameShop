const passwordInput = document.querySelector('#floatingPassword');
const confirmPasswordInput = document.querySelector('#floatingPasswordConfirm');

passwordInput.addEventListener('input', () => {
    if (passwordInput.value !== confirmPasswordInput.value) {
        confirmPasswordInput.setCustomValidity('Passwords do not match');
    } else {
        confirmPasswordInput.setCustomValidity('');
    }
});

confirmPasswordInput.addEventListener('input', () => {
    if (passwordInput.value !== confirmPasswordInput.value) {
        confirmPasswordInput.setCustomValidity('Passwords do not match');
    } else {
        confirmPasswordInput.setCustomValidity('');
    }
});


