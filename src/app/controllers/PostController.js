//import * as Yup from "yup";
import Post from "../models/Post";

class PostController {
  async store(req, res) {
    await Post.create(req.body);

    return res.redirect("/");
  }

  async index(req, res) {
    const posts = await Post.find({});

    return res.render("index", { posts });
  }
}

export default new PostController();
