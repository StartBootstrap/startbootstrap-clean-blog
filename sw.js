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
        // The network failed. Show offline message.
        const offlineMessage = new Response('<h1>You are currently offline</h1>', {
          headers: {'Content-Type': 'text/html'}
        });
        return offlineMessage;
      }
    }
  })());
});

self.addEventListener('activate', event => {
  event.waitUntil((async () => {
    const keys = await caches.keys();
    // Delete old cache versions.
    keys.filter(key => key !== CACHE_NAME).map(key => caches.delete(key));
  })());
});

// Show/hide offline message
function showOfflineMessage() {
  const offlineDiv = document.createElement('div');
  offlineDiv.innerHTML = '<h1>You are currently offline</h1>';
  offlineDiv.id = 'offline-message';
  document.body.appendChild(offlineDiv);
}

function hideOfflineMessage() {
  const offlineDiv = document.getElementById('offline-message');
  if (offlineDiv) {
    offlineDiv.remove();
  }
}

window.addEventListener('load', () => {
  if (!navigator.onLine) {
    showOfflineMessage();
  }
});

window.addEventListener('online', () => {
  hideOfflineMessage();
});

window.addEventListener('offline', () => {
  showOfflineMessage();
});
