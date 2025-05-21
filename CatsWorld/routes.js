import homeController from './js/controllers/homeController.js';
import catController from './js/controllers/catController.js';

const routes = {
  '/': () => homeController.init(),
  '/cats': () => catController.init(),
  '/404': () => {
    document.getElementById('app').innerHTML = '<h1>404 - Page Not Found</h1>';
  }
};

export default routes;


