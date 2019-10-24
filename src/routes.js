import { Router } from "express";
import path from "path";

import PostController from "./app/controllers/PostController";
import Post from "./app/models/Post";

const routes = new Router();

routes.get("/", (req, res) => {
  res.render("index");
});

routes.get("/posts/new", (req, res) => {
  res.render("create");
});

routes.post("/posts/store", PostController.store);

export default routes;
