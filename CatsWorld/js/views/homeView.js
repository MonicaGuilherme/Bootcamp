function render(facts, images) {
  const container = document.getElementById('app');

  container.innerHTML = `
   <div class="banner-container">
      <img id="banner" class="banner" src="/resources/catimg.webp" alt="Cat Banner" />
    </div>
    <h1 class="text-center text-white mt-3 mb-4">Fun facts about them!</h1>

    <div id="carousel-container" class="d-flex justify-content-center align-items-center gap-3">
      <button id="prev-btn" class="btn btn-outline-light btn-lg">&#8592;</button>

      <div id="cat-card" class="card bg-dark text-white d-flex flex-row align-items-center" style="max-width: 700px; padding: 1rem;">
        <!-- Image and text -->
      </div>

      <button id="next-btn" class="btn btn-outline-light btn-lg">&#8594;</button>
    </div>
  `;

  let currentIndex = 0;
  const catCard = document.getElementById('cat-card');
  const prevBtn = document.getElementById('prev-btn');
  const nextBtn = document.getElementById('next-btn');

  function updateCard() {
  catCard.innerHTML = `
    <img src="${images[currentIndex]}" alt="Cat Image" />
    <p><strong>Fact:</strong> ${facts[currentIndex]}</p>
  `;
}

  prevBtn.addEventListener('click', () => {
    currentIndex = (currentIndex - 1 + facts.length) % facts.length;
    updateCard();
  });

  nextBtn.addEventListener('click', () => {
    currentIndex = (currentIndex + 1) % facts.length;
    updateCard();
  });

  updateCard();
}

export default { render };




