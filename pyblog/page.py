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
    def __init__(self, metadata: dict, template_file_path: str) -> None:
        self.metadata = metadata
        with open(template_file_path, "r") as f:
            self.html_text = f.read()

    def inject_templates(self):
        self.html_text = inject_head(self.html_text)
        self.html_text = inject_navbar(self.html_text)
        self.html_text = inject_header(self.html_text)
        self.html_text = inject_markdown_content(self.html_text)
        self.html_text = inject_footer(self.html_text)

    def generate_html(self):
        for key, value in self.metadata.items():
            if check_image(os.path.join("./assets/images", value)):
                value = os.path.join("../assets/images", value)
            elif os.path.isfile(os.path.join("./markdowns", value)):
                value = markdown2.markdown_path(os.path.join("./markdowns", value))
            self.html_text = re.sub(
                r"\{\s*" + str(key) + r"\s*\}", value, self.html_text
            )
        with open("published/index.html", "w") as f:
            f.write(self.html_text)
