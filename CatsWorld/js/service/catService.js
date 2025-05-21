const API_KEY = /* API */;

const headers = {
  'x-api-key': API_KEY
};

async function getFacts() {
  const response = await fetch(/* URL */);
  const data = await response.json();
  return data.data;
}

async function getCatImages() {
  const response = await fetch(/* URL */, { headers });
  const data = await response.json();
  return data.map(img => img.url);
}

async function getCats() {
  const response = await fetch(/* URL */, { headers });
  const data = await response.json();
  return data;
}

export default { getFacts, getCatImages, getCats };
