import { Router } from "express";
import path from "path";

import PostController from "./app/controllers/PostController";
import GetPostController from "./app/controllers/GetPostController";

const routes = new Router();

routes.get("/", PostController.index);

/*routes.get("/", (req, res) => {
  res.render("index");
});*/

routes.get("/posts/new", (req, res) => {
  res.render("create");
});

routes.post("/posts/store", PostController.store);

routes.get("/about", (req, res) => {
  res.render("about");
});

routes.get("/contact", (req, res) => {
  res.render("contact");
});

routes.get("/post/:id", GetPostController.index);

export default routes;
