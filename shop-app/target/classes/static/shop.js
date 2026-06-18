const apiPath = '/api/products';
const cart = new Map();
let chart;

function getStoredUser() {
  try {
    return JSON.parse(localStorage.getItem('shopUser')) || null;
  } catch (error) {
    return null;
  }
}

function setStoredUser(user) {
  localStorage.setItem('shopUser', JSON.stringify(user));
}

function clearStoredUser() {
  localStorage.removeItem('shopUser');
}

async function fetchProducts() {
  const response = await fetch(apiPath);
  return response.json();
}

function formatPrice(value) {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value);
}

function renderProducts(products) {
  const grid = document.getElementById('productGrid');
  grid.innerHTML = '';

  products.forEach(product => {
    const card = document.createElement('article');
    card.className = 'product-card';
    card.innerHTML = `
      <img src="${product.image}" alt="${product.name}" />
      <div class="product-info">
        <h3>${product.name}</h3>
        <p class="product-category">${product.category}</p>
        <p class="product-desc">${product.description}</p>
        <div class="product-footer">
          <span class="product-price">${formatPrice(product.price)}</span>
          <button data-product-id="${product.id}">Add to cart</button>
        </div>
      </div>
    `;
    card.querySelector('button').addEventListener('click', () => addToCart(product));
    grid.appendChild(card);
  });
}

function addToCart(product) {
  const existing = cart.get(product.id);
  cart.set(product.id, {
    ...product,
    quantity: existing ? existing.quantity + 1 : 1
  });
  updateCart();
}

function removeFromCart(productId) {
  cart.delete(productId);
  updateCart();
}

function clearCart() {
  cart.clear();
  updateCart();
}

function renderCart() {
  const cartItems = document.getElementById('cartItems');
  const cartSummary = document.getElementById('cartSummary');
  cartItems.innerHTML = '';

  const items = Array.from(cart.values());
  const total = items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const count = items.reduce((sum, item) => sum + item.quantity, 0);

  cartSummary.innerHTML = `
    <p>${count} item(s) in cart</p>
    <p>Total: <strong>${formatPrice(total)}</strong></p>
  `;

  if (items.length === 0) {
    cartItems.innerHTML = '<li>Your cart is empty.</li>';
    return;
  }

  items.forEach(item => {
    const li = document.createElement('li');
    li.innerHTML = `
      <div>
        <strong>${item.name}</strong>
        <p>${item.quantity} × ${formatPrice(item.price)}</p>
      </div>
      <button data-remove-id="${item.id}">Remove</button>
    `;
    li.querySelector('button').addEventListener('click', () => removeFromCart(item.id));
    cartItems.appendChild(li);
  });
}

function buildChartData() {
  const categoryCounts = {};
  cart.forEach(item => {
    categoryCounts[item.category] = (categoryCounts[item.category] || 0) + item.quantity;
  });
  return {
    labels: Object.keys(categoryCounts),
    values: Object.values(categoryCounts)
  };
}

function updateChart() {
  const data = buildChartData();
  if (!chart) {
    const ctx = document.getElementById('cartChart').getContext('2d');
    chart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: data.labels,
        datasets: [{
          data: data.values,
          backgroundColor: ['#4f46e5', '#ec4899', '#f59e0b', '#10b981', '#3b82f6', '#14b8a6'],
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'bottom' },
          title: {
            display: true,
            text: 'Cart Items by Category'
          }
        }
      }
    });
  } else {
    chart.data.labels = data.labels;
    chart.data.datasets[0].data = data.values;
    chart.update();
  }
}

function updateUserHeader() {
  const user = getStoredUser();
  const userStatus = document.getElementById('userStatus');
  const loginLink = document.getElementById('loginLink');
  const logoutButton = document.getElementById('logoutButton');
  const rolePanel = document.getElementById('rolePanel');

  if (user) {
    userStatus.textContent = `Signed in as ${user.fullName} (${user.role})`;
    loginLink.hidden = true;
    logoutButton.hidden = false;
    rolePanel.hidden = false;
    rolePanel.innerHTML = `
      <p><strong>Role access:</strong> ${user.role}</p>
      <p>${user.role === 'manager' ? 'Manager access enabled: you can review site performance and manage orders.' : 'Customer access enabled: you may place orders and save shipment details.'}</p>
    `;
  } else {
    userStatus.textContent = 'Not signed in';
    loginLink.hidden = false;
    logoutButton.hidden = true;
    rolePanel.hidden = true;
    rolePanel.innerHTML = '';
  }
}

async function handleCheckout(event) {
  event.preventDefault();
  const user = getStoredUser();
  const resultEl = document.getElementById('checkoutResult');

  if (!user) {
    resultEl.textContent = 'Please login before completing checkout.';
    resultEl.className = 'checkout-result status-error';
    return;
  }

  const address = document.getElementById('shippingAddress').value.trim();
  const paymentMethod = document.getElementById('paymentMethod').value;
  const total = Array.from(cart.values()).reduce((sum, item) => sum + item.price * item.quantity, 0);

  if (!address) {
    resultEl.textContent = 'Enter your shipping address to continue.';
    resultEl.className = 'checkout-result status-error';
    return;
  }
  if (cart.size === 0) {
    resultEl.textContent = 'Add products to your cart before checkout.';
    resultEl.className = 'checkout-result status-error';
    return;
  }

  const response = await fetch('/api/checkout', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      username: user.username,
      paymentMethod,
      shippingAddress: address,
      total
    })
  });
  const data = await response.json();

  if (response.ok && data.success) {
    resultEl.textContent = `Success: ${data.paymentDetail} Shipment to ${data.shippingAddress}. Total ${formatPrice(data.orderTotal)}`;
    resultEl.className = 'checkout-result status-success';
    clearCart();
    document.getElementById('shippingAddress').value = '';
  } else {
    resultEl.textContent = data.message || 'Checkout failed. Please try again.';
    resultEl.className = 'checkout-result status-error';
  }
}

function setupLogout() {
  document.getElementById('logoutButton').addEventListener('click', () => {
    clearStoredUser();
    updateUserHeader();
  });
}

function updateCart() {
  renderCart();
  updateChart();
}

async function init() {
  const products = await fetchProducts();
  renderProducts(products);
  document.getElementById('clearCart').addEventListener('click', clearCart);
  document.getElementById('checkoutForm').addEventListener('submit', handleCheckout);
  setupLogout();
  updateUserHeader();
  updateCart();
}

window.addEventListener('DOMContentLoaded', init);
