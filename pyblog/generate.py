import re
import yaml


def generate_index_html():
    with open("config.yaml", "r") as file:
        metadata = yaml.safe_load(file)
    with open("templates/index.html.template") as f:
        html_text = f.read()
    for key, value in metadata.items():
        html_text = re.sub(r'\{\s*' + key + r'\s*\}', value, html_text)
    with open("published/index.html", "w") as f:
        f.write(html_text)
