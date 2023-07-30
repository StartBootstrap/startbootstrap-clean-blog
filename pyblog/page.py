import os
import re
import markdown2

from .utils import check_image


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

    def inject_head(self):
        with open("templates/head.html", "r") as f:
            head_text = f.read()
        self.html_text = re.sub(r"\{\{\s*head\s*\}\}", head_text, self.html_text)

    def inject_navbar(
        self,
        site_name: str,
        page_slugs: list,
        page_texts: list,
    ):
        with open("templates/navbar.html", "r") as f:
            navbar_text = f.read()
        self.html_text = re.sub(r"\{\{\s*navbar\s*\}\}", navbar_text, self.html_text)
        self.html_text = re.sub(r"\{\s*site-name\s*\}", site_name, self.html_text)
        navbar_list_tags = ""
        for page_slug, page_text in zip(page_slugs, page_texts):
            navbar_list_tags += f'<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="{page_slug}.html">{page_text}</a></li>'
        self.html_text = re.sub(
            r"\{\s*navbar-list\s*\}", navbar_list_tags, self.html_text
        )

    def inject_header(self):
        with open("templates/header.html", "r") as f:
            header_text = f.read()
        self.html_text = re.sub(r"\{\{\s*header\s*\}\}", header_text, self.html_text)

    def inject_markdown_content(self):
        with open("templates/md-content.html", "r") as f:
            md_content_text = f.read()
        self.html_text = re.sub(
            r"\{\{\s*md-content\s*\}\}", md_content_text, self.html_text
        )

    def inject_footer(self):
        with open("templates/footer.html", "r") as f:
            footer_text = f.read()
        self.html_text = re.sub(r"\{\{\s*footer\s*\}\}", footer_text, self.html_text)

    def inject_templates(self):
        self.inject_head()
        self.inject_navbar(
            site_name=self.site_name,
            page_texts=self.page_texts,
            page_slugs=self.page_slugs,
        )
        self.inject_header()
        self.inject_markdown_content()
        self.inject_footer()

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
