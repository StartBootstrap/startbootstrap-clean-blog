import os
import re
import markdown2

from .utils import check_image
from .template_injection import (
    inject_head,
    inject_navbar,
    inject_header,
    inject_markdown_content,
    inject_footer,
)


class Page:
    def __init__(
        self,
        site_name: str,
        metadata: dict,
        page_slugs: list,
        page_texts: list,
        template_file_path: str,
    ) -> None:
        self.site_name = site_name
        self.metadata = metadata
        self.page_slugs = page_slugs
        self.page_texts = page_texts
        with open(template_file_path, "r") as f:
            self.html_text = f.read()

    def inject_templates(self):
        self.html_text = inject_head(self.html_text)
        self.html_text = inject_navbar(
            self.html_text,
            site_name=self.site_name,
            page_texts=self.page_texts,
            page_slugs=self.page_slugs,
        )
        self.html_text = inject_header(self.html_text)
        self.html_text = inject_markdown_content(self.html_text)
        self.html_text = inject_footer(self.html_text)

    def generate_html(self, publish_file_path: str):
        for key, value in self.metadata.items():
            if value != "None":
                if check_image(os.path.join("./assets/images", value)):
                    value = os.path.join("../assets/images", value)
                    if key == "portfolio-image":
                        value = f'<img src="{value}">'
                elif os.path.isfile(os.path.join("./markdowns", value)):
                    value = markdown2.markdown_path(os.path.join("./markdowns", value))
                self.html_text = re.sub(
                    r"\{\s*" + str(key) + r"\s*\}", value, self.html_text
                )
        self.html_text = re.sub(r"\{([^}]*)\}", "", self.html_text)
        with open(publish_file_path, "w") as f:
            f.write(self.html_text)
