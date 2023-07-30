import os
import yaml

from .page import Page


def generate_index_html():
    with open("config.yaml", "r") as file:
        metadata = yaml.safe_load(file)
    pages_metadata = metadata.pop("pages") if "pages" in metadata else None
    landing_page = Page(metadata=metadata, template_file_path="templates/index.html")
    landing_page.inject_templates()
    landing_page.generate_html(publish_file_path="published/index.html")
    for page_metadata in pages_metadata:
        id_metadata = page_metadata.pop("id")
        page = Page(
            metadata=page_metadata,
            template_file_path=os.path.join("templates", id_metadata["template"]),
        )
        page.inject_templates()
        page.generate_html(
            publish_file_path=os.path.join("published", id_metadata["slug"] + ".html")
        )
