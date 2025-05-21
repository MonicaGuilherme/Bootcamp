const API_KEY = 'live_fc13trzsco3nqABrLmPuQk8dj2UBIsj003fsYUD66iGIamCm8ANMrEi77m8gJYBv';

const headers = {
  'x-api-key': API_KEY
};

async function getFacts() {
  const response = await fetch('https://meowfacts.herokuapp.com/?count=5');
  const data = await response.json();
  return data.data;
}

async function getCatImages() {
  const response = await fetch('https://api.thecatapi.com/v1/images/search?limit=5', { headers });
  const data = await response.json();
  return data.map(img => img.url);
}

async function getCats() {
  const response = await fetch('https://api.thecatapi.com/v1/breeds', { headers });
  const data = await response.json();
  return data;
}

export default { getFacts, getCatImages, getCats };
