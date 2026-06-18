document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const username = document.getElementById('username').value;
  // In this demo we don't authenticate - just save a fake token
  localStorage.setItem('api_user', username);
  alert('Signed in as ' + username);
  window.location.href = '/';
});
