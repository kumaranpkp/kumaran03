const express = require('express');
const apiRouter = require('./routes/api');
const data = require('./data/apiData.json');
const path = require('path');

const app = express();
app.use(express.json());

// Serve UI at root
app.use('/', express.static(path.join(__dirname, 'public')));

// Serve login page explicitly (so /login works)
app.get('/login', (req, res) => {
	res.sendFile(path.join(__dirname, 'public', 'login.html'));
});

// Redirect /shop to the Spring Boot shop app running on port 8081
app.get('/shop', (req, res) => {
	res.redirect('http://localhost:8081');
});

// Optional JSON info route (kept for API clients)
app.get('/info', (req, res) => {
	res.json({
		service: 'API listing service',
		endpoints: [
			'/api/details',
			'/api/functions',
			'/api/channels',
			'/api/upcoming-projects',
			'/api/deployed-changes',
			'/api/upcoming-releases'
		],
		summary: {
			apis: data.apiDetails.length,
			functions: data.functions.length,
			channels: data.channels.length
		}
	});
});

app.use('/api', apiRouter);

// Serve UI under /ui
app.use('/ui', express.static(path.join(__dirname, 'public')));

module.exports = app;
