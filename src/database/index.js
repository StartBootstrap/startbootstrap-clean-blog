import mongoose from "mongoose";

class Database {
  constructor() {
    this.init();
  }

  init() {
    this.mongoConnection = mongoose.connect(
      "mongodb+srv://blogposter:1DcaDbqogD@cluster0-29caz.mongodb.net/test?retryWrites=true&w=majority",
      {
        useNewUrlParser: true,
        useFindAndModify: true,
        useUnifiedTopology: true
      }
    );
  }
}

export default new Database();
