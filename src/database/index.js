import mongoose from "mongoose";

class Database {
  constructor() {
    this.init();
  }

  init() {
    this.mongoConnection = mongoose.connect(
      "mongodb://localhost:27017/node-blog",
      {
        useNewUrlParser: true,
        useFindAndModify: true,
        useUnifiedTopology: true
      }
    );
  }
}

export default new Database();
