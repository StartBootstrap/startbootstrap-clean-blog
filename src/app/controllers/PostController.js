//import * as Yup from "yup";
import Post from "../models/Post";

class PostController {
  async store(req, res) {
    await Post.create(req.body);

    return res.json(req.body);
  }
}

export default new PostController();
