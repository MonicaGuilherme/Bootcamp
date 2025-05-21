import router from './router.js';

window.addEventListener('DOMContentLoaded', router.handleRoute);
window.addEventListener('popstate', router.handleRoute);

document.body.addEventListener('click', e => {
  // Só intercepta links internos com data-link para SPA navigation
  if (e.target.matches('a[data-link]')) {
    e.preventDefault();
    history.pushState(null, '', e.target.href);
    router.handleRoute();
  }
});




