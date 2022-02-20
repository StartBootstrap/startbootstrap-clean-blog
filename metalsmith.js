const Metalsmith = require('metalsmith')
const drafts = require('@metalsmith/drafts')
const layouts = require('@metalsmith/layouts')
const markdown = require('@metalsmith/markdown')
const sass = require('@metalsmith/sass')
const collections = require('@metalsmith/collections')
const postcss = require('@metalsmith/postcss')
const dev = process.env.NODE_ENV === 'development'

const sitedata = {
  generator: {
    name: 'Metalsmith'
  },
  site: {
    base: dev ? 'http://localhost:5000' : 'https://startbootstrap.github.io/startbootstrap-clean-blog/',
    subject: 'Start Bootstrap Theme',
    author: 'Start Bootstrap'
  },
  nav: [
    {path: 'index.html', label: 'Home'},
    {path: 'about.html', label: 'About'},
    {path: 'contact.html', label: 'Contact'}
  ],
  socials: {
    twitter: 'https://twitter.com/johndoe',
    facebook: 'https://facebook.com/johndoe',
    github: 'https://github.com/johndoe'
  }
}

function formatDate(value, format) {
  const dt = new Date(value);
  if (format === 'iso') {
    return dt.toISOString();
  }
  const utc = dt.toUTCString().match(/(\d{1,2}) (.*) (\d{4})/)
  return `${utc[2]} ${utc[1]}, ${utc[3]}`
}

function formattedDatesForPosts(files, metalsmith) {
  metalsmith.metadata().collections.posts.forEach(post => {
    if (post.date) {
      post.displayDate = formatDate(post.date)
      post.isoDate = formatDate(post.date, 'iso')
    }
  })
}

function noop() {}

Metalsmith(__dirname)
  .source('src/content')
  .destination('dist')
  .metadata(sitedata)
  .use(dev ? noop : drafts())
  .use(markdown())
  .use(collections({
    posts: {
      pattern: 'posts/**/*.html',
      reverse: true,
      sortBy: 'date'
    }
  }))
  .use(formattedDatesForPosts)
  .use(layouts({
    directory: 'src/pug',
    default: 'default.pug'
  }))
  .use(sass({
    entries: {
      'src/scss/styles.scss' : 'css/styles.css'
    }
  }))
  .use(dev ? postcss({
    plugins: [
      'autoprefixer',
      'cssnano'
    ],
    map: true
  }) : noop)
  .build((error, files) => {
    if (error) throw error
    console.log('Build finished')
  })