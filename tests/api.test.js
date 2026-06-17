const request = require('supertest');
const app = require('../app');

describe('API listing endpoints', () => {
  test('GET /api/details returns api details', async () => {
    const res = await request(app).get('/api/details');
    expect(res.statusCode).toBe(200);
    expect(res.body).toHaveProperty('success', true);
    expect(Array.isArray(res.body.data)).toBe(true);
  });

  test('GET /api/upcoming-projects returns array', async () => {
    const res = await request(app).get('/api/upcoming-projects');
    expect(res.statusCode).toBe(200);
    expect(res.body.success).toBe(true);
    expect(Array.isArray(res.body.data)).toBe(true);
  });
});
