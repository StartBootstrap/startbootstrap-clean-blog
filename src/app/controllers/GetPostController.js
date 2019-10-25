import Post from "../models/Post";

class GetPostController {
  async index(req, res) {
    const post = await Post.findById(req.params.id);

    return res.render("post", { post });
  }
}

export default new GetPostController();
