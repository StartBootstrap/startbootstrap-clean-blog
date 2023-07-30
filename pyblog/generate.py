import os
import yaml

from .page import Page


def generate_index_html():
    with open("config.yaml", "r") as file:
        metadata = yaml.safe_load(file)
    pages_metadata = metadata.pop("pages") if "pages" in metadata else None
    page_texts = [page_metadata["name"] for page_metadata in pages_metadata]
    page_slugs = [page_metadata["id"]["slug"] for page_metadata in pages_metadata]
    site_name = metadata["name"]
    landing_page = Page(
        site_name=site_name,
        metadata=metadata,
        page_texts=page_texts,
        page_slugs=page_slugs,
        template_file_path="templates/index.html",
    )
    landing_page.inject_templates()
    landing_page.generate_html(publish_file_path="published/index.html")
    for page_metadata in pages_metadata:
        id_metadata = page_metadata.pop("id")
        page = Page(
            site_name=site_name,
            metadata=page_metadata,
            page_texts=page_texts,
            page_slugs=page_slugs,
            template_file_path=os.path.join("templates", id_metadata["template"]),
        )
        page.inject_templates()
        page.generate_html(
            publish_file_path=os.path.join("published", id_metadata["slug"] + ".html")
        )
