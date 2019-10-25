import express from "express";
import path from "path";
import { config, engine } from "express-edge";
import bodyParser from "body-parser";
import fileUpload from "express-fileupload";

import routes from "./routes";

import "./database";

class App {
  constructor() {
    this.server = express();

    this.middlewares();
    this.routes();
  }

  middlewares() {
    this.server.use(express.json());
    this.server.use(express.static("public"));
    this.server.use(engine);
    this.server.set("views", path.resolve(__dirname, "..", "views"));
    this.server.use(bodyParser.json());
    this.server.use(
      bodyParser.urlencoded({
        extended: true
      })
    );
    this.server.use(fileUpload());
  }

  routes() {
    this.server.use(routes);
  }
}

export default new App().server;
