import routes from './routes.js';

function handleRoute() {
  const path = window.location.pathname;
  const route = routes[path] || routes['/404'];
  route();
}

export default { handleRoute };


