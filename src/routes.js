import { Router } from "express";
import path from "path";

const routes = new Router();

routes.set("views", __dirname + "/views");

routes.get("/", (req, res) => {
  return res.sendFile(path.resolve(__dirname, "..", "public", "index.html"));
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
