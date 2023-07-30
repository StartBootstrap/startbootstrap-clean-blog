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


def generate_index_html():
    with open("config.yaml", "r") as file:
        metadata = yaml.safe_load(file)
    with open("templates/index.html.template") as f:
        html_text = f.read()
    for key, value in metadata.items():
        if check_img(os.path.join("./assets/images", value)):
            value = os.path.join("../assets/images", value)
        elif os.path.isfile(os.path.join("./markdowns", value)):
            value = markdown2.markdown_path(os.path.join("./markdowns", value))
        html_text = re.sub(r'\{\s*' + key + r'\s*\}', value, html_text)
    with open("published/index.html", "w") as f:
        f.write(html_text)
