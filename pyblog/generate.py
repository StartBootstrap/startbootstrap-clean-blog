import os
import re
import yaml
import markdown2
from PIL import Image


def check_img(filename):
    try:
        im = Image.open(filename)
        im.verify()
        im.close()
        im = Image.open(filename)
        im.transpose(Image.FLIP_LEFT_RIGHT)
        im.close()
        return True
    except Exception:
        return False


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


def generate_index_html():
    with open("config.yaml", "r") as file:
        metadata = yaml.safe_load(file)
    with open("templates/index.html") as f:
        html_text = f.read()
    html_text = inject_head(html_text)
    html_text = inject_navbar(html_text)
    html_text = inject_header(html_text)
    html_text = inject_markdown_content(html_text)
    html_text = inject_footer(html_text)
    for key, value in metadata.items():
        if check_img(os.path.join("./assets/images", value)):
            value = os.path.join("../assets/images", value)
        elif os.path.isfile(os.path.join("./markdowns", value)):
            value = markdown2.markdown_path(os.path.join("./markdowns", value))
        html_text = re.sub(r"\{\s*" + str(key) + r"\s*\}", value, html_text)
    with open("published/index.html", "w") as f:
        f.write(html_text)
