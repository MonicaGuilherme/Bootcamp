import homeView from '../views/homeView.js';
import catService from '../service/catService.js';

async function init() {
  try {
    const facts = await catService.getFacts();
    const images = await catService.getCatImages();
    homeView.render(facts, images);
  } catch (err) {
    document.getElementById('app').innerHTML = `<p>Error loading data: ${err.message}</p>`;
  }
}

export default { init };

