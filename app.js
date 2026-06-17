const express = require('express');
const apiRouter = require('./routes/api');
const data = require('./data/apiData.json');
const path = require('path');

const app = express();
app.use(express.json());

app.get('/', (req, res) => {
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
