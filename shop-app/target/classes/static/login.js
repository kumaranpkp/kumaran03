const form = document.getElementById('loginForm');
const statusEl = document.getElementById('loginStatus');

form.addEventListener('submit', async (event) => {
  event.preventDefault();

  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value.trim();

  const response = await fetch('/api/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });

  const data = await response.json();

  if (!response.ok) {
    statusEl.textContent = data.error || 'Login failed. Please try again.';
    statusEl.className = 'status-error';
    return;
  }

  localStorage.setItem('shopUser', JSON.stringify(data));
  window.location.href = '/';
});
