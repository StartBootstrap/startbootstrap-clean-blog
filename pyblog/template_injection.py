import re


def inject_head(html_text: str):
    with open("templates/head.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*head\s*\}\}", head_text, html_text)


def inject_navbar(html_text: str):
    with open("templates/navbar.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*navbar\s*\}\}", head_text, html_text)


def inject_header(html_text: str):
    with open("templates/header.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*header\s*\}\}", head_text, html_text)


def inject_markdown_content(html_text: str):
    with open("templates/md-content.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*md-content\s*\}\}", head_text, html_text)


def inject_footer(html_text: str):
    with open("templates/footer.html", "r") as f:
        head_text = f.read()
    return re.sub(r"\{\{\s*footer\s*\}\}", head_text, html_text)
