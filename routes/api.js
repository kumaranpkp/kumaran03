const express = require('express');
const router = express.Router();
const data = require('../data/apiData.json');

router.get('/details', (req, res) => res.json({ success: true, data: data.apiDetails }));
router.get('/functions', (req, res) => res.json({ success: true, data: data.functions }));
router.get('/channels', (req, res) => res.json({ success: true, data: data.channels }));
router.get('/upcoming-projects', (req, res) => res.json({ success: true, data: data.upcomingProjects }));
router.get('/deployed-changes', (req, res) => res.json({ success: true, data: data.deployedChanges }));
router.get('/upcoming-releases', (req, res) => res.json({ success: true, data: data.upcomingReleases }));

module.exports = router;
