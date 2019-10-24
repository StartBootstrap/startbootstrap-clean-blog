import mongoose from "mongoose";

const PostSchema = new mongoose.Schema({
  title: String,
  description: String,
  content: String
});

const Post = mongoose.model("Post", PostSchema);

module.exports = Post;
