import express from "express";
import path from "path";
import expressEdge from "express-edge";
import routes from "./routes";

class App {
  constructor() {
    this.server = express();

    this.middlewares();
    this.routes();
  }

  middlewares() {
    this.server.use(express.json());
    this.server.use(express.static("public"));
    this.server.use(expressEdge);
  }

  routes() {
    this.server.use(routes);
  }
}

export default new App().server;
