const CACHE_NAME = `blog-v1`;

// Use the install event to pre-cache all initial resources.
self.addEventListener('install', event => {
  event.waitUntil((async () => {
    const cache = await caches.open(CACHE_NAME);
    cache.addAll([
      '/',
      '/assets/css/accordion.css',
      '/assets/css/clean-blog.min.css',
      '/assets/css/menu.css',
      '/assets/css/styles.scss'
    ]);
  })());
});

self.addEventListener('fetch', event => {
  event.respondWith((async () => {
    const cache = await caches.open(CACHE_NAME);

    // Get the resource from the cache.
    const cachedResponse = await cache.match(event.request);
    if (cachedResponse) {
      return cachedResponse;
    } else {
      try {
        // If the resource was not in the cache, try the network.
        const fetchResponse = await fetch(event.request);

        // Save the resource in the cache and return it.
        cache.put(event.request, fetchResponse.clone());
        return fetchResponse;
      } catch (e) {
        // The network failed.
      }
    }
  })());
});

// Listen for the "offline" event
window.addEventListener('offline', function (event) {
  const offlineMessage = document.createElement('div');
  offlineMessage.textContent = 'You are currently offline. Please check your internet connection.';
  offlineMessage.className = 'offline-message'; // Add class name to the element
  offlineMessage.style.position = 'fixed';
  offlineMessage.style.bottom = 0;
  offlineMessage.style.width = '100%';
  offlineMessage.style.backgroundColor = 'red';
  offlineMessage.style.color = 'white';
  offlineMessage.style.padding = '10px';
  offlineMessage.style.textAlign = 'center';
  document.body.appendChild(offlineMessage);
});

// Listen for the "online" event
window.addEventListener('online', function (event) {
  const offlineMessage = document.querySelector('.offline-message');
  if (offlineMessage) {
    offlineMessage.remove();
  }
});