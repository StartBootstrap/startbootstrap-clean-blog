import re


def inject_head(html_text: str):
    with open("templates/head.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*head\s*\}\}", head_text, html_text)


def inject_navbar(
    html_text: str,
    site_name: str,
    page_slugs: list,
    page_texts: list,
):
    with open("templates/navbar.html", "r") as f:
        navbar_text = f.read()
    html_text = re.sub(r"\{\{\s*navbar\s*\}\}", navbar_text, html_text)
    html_text = re.sub(r"\{\s*site-name\s*\}", site_name, html_text)
    navbar_list_tags = ""
    for page_slug, page_text in zip(page_slugs, page_texts):
        navbar_list_tags += f'<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="{page_slug}.html">{page_text}</a></li>'
    return re.sub(r"\{\s*navbar-list\s*\}", navbar_list_tags, html_text)


def inject_header(html_text: str):
    with open("templates/header.html", "r") as f:
        header_text = f.read()
    return re.sub(r"\{\{\s*header\s*\}\}", header_text, html_text)


def inject_markdown_content(html_text: str):
    with open("templates/md-content.html", "r") as f:
        md_content_text = f.read()
    return re.sub(r"\{\{\s*md-content\s*\}\}", md_content_text, html_text)


def inject_footer(html_text: str):
    with open("templates/footer.html", "r") as f:
        footer_text = f.read()
    return re.sub(r"\{\{\s*footer\s*\}\}", footer_text, html_text)
