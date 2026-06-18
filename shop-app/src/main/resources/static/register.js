const registerForm = document.getElementById('registerForm');
const registerStatus = document.getElementById('registerStatus');

registerForm.addEventListener('submit', async (event) => {
  event.preventDefault();

  const payload = {
    username: document.getElementById('username').value.trim(),
    password: document.getElementById('password').value.trim(),
    fullName: document.getElementById('fullName').value.trim(),
    email: document.getElementById('email').value.trim(),
    address: document.getElementById('address').value.trim(),
    phone: document.getElementById('phone').value.trim(),
    accountDetails: document.getElementById('accountDetails').value.trim(),
  };

  const response = await fetch('/api/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  const data = await response.json();
  if (response.ok) {
    localStorage.setItem('shopUser', JSON.stringify(data.user));
    window.location.href = '/';
    return;
  }

  registerStatus.textContent = data.message || 'Registration failed. Please try again.';
  registerStatus.className = 'status-error';
});
