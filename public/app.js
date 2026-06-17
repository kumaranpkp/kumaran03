async function fetchJson(path) {
  const res = await fetch(path);
  return res.json();
}

function renderList(title, items, el) {
  const h = document.createElement('h2');
  h.textContent = title;
  el.appendChild(h);
  const ul = document.createElement('ul');
  items.forEach(it => {
    const li = document.createElement('li');
    if (typeof it === 'string') li.textContent = it;
    else li.textContent = JSON.stringify(it);
    ul.appendChild(li);
  });
  el.appendChild(ul);
}

async function init() {
  const [details, functionsRes, channelsRes, projectsRes, changesRes, releasesRes] = await Promise.all([
    fetchJson('/api/details'),
    fetchJson('/api/functions'),
    fetchJson('/api/channels'),
    fetchJson('/api/upcoming-projects'),
    fetchJson('/api/deployed-changes'),
    fetchJson('/api/upcoming-releases')
  ]);

  const summaryEl = document.getElementById('summary');
  const detailsEl = document.getElementById('details');
  const projectsEl = document.getElementById('projects');
  const changesEl = document.getElementById('changes');

  summaryEl.innerHTML = `<h2>Summary</h2><p>APIs: ${details.data.length}</p><p>Functions: ${functionsRes.data.length}</p><p>Channels: ${channelsRes.data.length}</p>`;

  renderList('API Endpoints', details.data.map(d => `${d.method} ${d.path} — ${d.description}`), detailsEl);
  renderList('Upcoming Projects', projectsRes.data.map(p => `${p.name} — ${p.eta}`), projectsEl);
  renderList('Deployed Changes / Releases', [...changesRes.data.map(c => `${c.id} — ${c.summary}`), ...releasesRes.data.map(r => `${r.version} — ${r.notes}`)], changesEl);
}

window.addEventListener('DOMContentLoaded', init);
