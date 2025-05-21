function render(cats) {
  const container = document.getElementById('app');
  container.innerHTML = `
    <h1 class="text-center text-white mt-4 mb-4">It's a Cats world!!</h1>
    <div class="text-center mb-4">
      <input id="search-name" type="text" placeholder="Search by name..." class="form-control w-50 mx-auto mb-2">
      <input id="search-origin" type="text" placeholder="Search by origin..." class="form-control w-50 mx-auto">
    </div>
    <div id="cat-list" class="d-flex flex-wrap justify-content-center gap-4"></div>
  `;

  const catList = document.getElementById('cat-list');
  const searchName = document.getElementById('search-name');
  const searchOrigin = document.getElementById('search-origin');

  function displayCats(filteredCats) {
    catList.innerHTML = '';

    filteredCats.forEach(cat => {
      const { name, origin, temperament, life_span, wikipedia_url, image, description } = cat;

      const card = document.createElement('div');
      card.className = 'cat-card card mb-3';
      card.style.maxWidth = '18rem';

      card.innerHTML = `
        <div class="card-header">${name}</div>
        <img src="${image?.url || '/resources/catimg.webp'}" class="card-img-top cat-img" alt="${name}">
        <div class="card-body">
          <h5 class="card-title">Origin: ${origin}</h5>
          <p class="card-text">
            <strong>Description:</strong> ${description || 'No description'}<br>
            <strong>Temperament:</strong> ${temperament}<br>
            <strong>Life Span:</strong> ${life_span} years<br>
            <a href="${wikipedia_url}" class="wiki-link" target="_blank" rel="noopener noreferrer">Wikipedia</a>
          </p>
        </div>
      `;

      catList.appendChild(card);
    });
  }

  function filterCats() {
    const nameTerm = searchName.value.toLowerCase();
    const originTerm = searchOrigin.value.toLowerCase();

    const filtered = cats.filter(cat =>
      cat.name.toLowerCase().includes(nameTerm) &&
      cat.origin.toLowerCase().includes(originTerm)
    );

    displayCats(filtered);
  }

  displayCats(cats);

  searchName.addEventListener('input', filterCats);
  searchOrigin.addEventListener('input', filterCats);
}

export default { render };



