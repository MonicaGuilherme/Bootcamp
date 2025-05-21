import catView from '../views/catView.js';
import catService from '../service/catService.js';

async function init() {
  try {
    const cats = await catService.getCats();
    catView.render(cats);
  } catch (err) {
    document.getElementById('app').innerHTML = `<p>Error loading cats: ${err.message}</p>`;
  }
}

export default { init };
