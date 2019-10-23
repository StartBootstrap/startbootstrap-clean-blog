import { Router } from "express";
import path from "path";

const routes = new Router();

routes.get("/", (req, res) => {
  res.render("index");
});

routes.get("/posts/new", (req, res) => {
  res.render("create");
});

routes.post("/posts/store", (req, res) => {
  console.log(req.body);
  res.redirect("/");
});

routes.get("/sobre", (req, res) => {
  return res.sendFile(path.resolve(__dirname, "..", "public", "about.html"));
});

routes.get("/posts", (req, res) => {
  return res.sendFile(path.resolve(__dirname, "..", "public", "post.html"));
});

routes.get("/contato", (req, res) => {
  return res.sendFile(path.resolve(__dirname, "..", "public", "contact.html"));
});

export default routes;
