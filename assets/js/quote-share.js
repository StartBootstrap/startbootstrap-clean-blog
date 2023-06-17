// sleeping so the quote has time to load. Once the quote loads, we can get the text and share it through the link.
// sleep time expects milliseconds
function sleep(time) {
   return new Promise((resolve) => setTimeout(resolve, time));
}

// Usage!
sleep(5000).then(() => {
   // Do something after the sleep!
   let text = document.getElementById("quote").textContent;

   const shareData = {
      title: 'Quote',
      subject: 'Quote',
      text: text,
      url: 'https://love-of-god-and-of-all-men.github.io'
   }

   const link = document.querySelector('#quote-share-link');
   const successMessage = document.querySelector('#quote-share-link');
   const shareLinkText = document.querySelector('#quote-share-link');

   shareLinkText.textContent = 'Share Quote'

   // Share must be triggered by "user activation"
   link.addEventListener('click', async () => {
      try {
         await navigator.share(shareData)
         successMessage.textContent = 'Shared successfully!'
      } catch (err) {
         successMessage.textContent = 'Error: ' + err
      }
   });
});