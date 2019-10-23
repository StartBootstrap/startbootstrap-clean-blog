import mongoose from "mongoose";

class Database {
  constructor() {
    this.mongo();
  }

  mongo() {
    this.mongoConnection = mongoose
      .connect(
        "mongodb+srv://blogposter:1Dca@dbqogD!@cluster0-29caz.mongodb.net/test?retryWrites=true&w=majority",
        { useNewUrlParser: true, useFindAndModify: true }
      )
      .then(() => "You are now connected to Mongo!")
      .catch(err => console.error("Something went wrong", err));
  }
}

export default new Database();
