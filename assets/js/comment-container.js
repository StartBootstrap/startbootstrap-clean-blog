const container = document.getElementById('container');
const threshold = 50;
let startY = 0;
let endY = 0;
let isRefreshing = false;

window.addEventListener('touchstart', (event) => {
  if (event.touches[0].clientY > window.innerHeight - threshold) {
    startY = event.touches[0].clientY;
    isRefreshing = false;
  }
});

window.addEventListener('touchmove', (event) => {
  if (isRefreshing) {
    return;
  }
  
  if (event.changedTouches[0].clientY > window.innerHeight - threshold) {
    endY = event.changedTouches[0].clientY;
    const distance = startY - endY;

    if (distance > threshold) {
      isRefreshing = true;
      container.style.display = 'block';
      // Call your refresh function here
      console.log('Refresh triggered!');
    }
  }
});

window.addEventListener('touchend', () => {
  if (!isRefreshing) {
    return;
  }
  
  isRefreshing = false;
  container.style.display = 'none';
});
