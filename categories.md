---
layout: categories
permalink: /categories/
title: Categories
---

{% for category in site.categories %}
  <a href="{{ site.baseurl }}/categories/{{ category | first | slugify }}/">{{ category | first }}</a>
{% endfor %}
