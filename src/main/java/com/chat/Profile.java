package com.chat;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

@WebServlet("/profile")
public class Profile extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		   PrintWriter out = res.getWriter();
		   res.addHeader("Access-Control-Allow-Origin", "*");
		   res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			
			String userID = req.getParameter("userID");
			String photoURL = req.getParameter("photoURL");
			
			// JDBC Connectivity to NoSQL Database (MongoDB)
			MongoClient mongo = new MongoClient("localhost", 27017);
			//Creating a database Name - ChatApp
			MongoDatabase db = mongo.getDatabase("ChatApp");
			
			MongoCollection<Document> userCollection = db.getCollection("Users");
			System.out.println(userID);
			Bson filter = Filters.eq("_id", new ObjectId(userID));
			Bson update = Updates.set("photoURL", photoURL);
			
			Document updateUser = userCollection.findOneAndUpdate(filter, update, new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
			
			res.setStatus(200);
			res.getWriter().write(updateUser.toJson().toString()); // set error message as response body
				
		   	
	}
}
