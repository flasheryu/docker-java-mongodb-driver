package org.example;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.util.Configration;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public final class TestMongo 
{
	private TestMongo()
	{
		
	}
	
	public static void main(String[] args)
	{
		Configration.initialize();
		
		String user = Configration.get("catfish.mongo.username");
		String database = Configration.get("catfish.mongo.databasename");
		String pwd = Configration.get("catfish.mongo.password");
		String server = Configration.get("catfish.mongo.ip");
		String port = Configration.get("catfish.mongo.port");
		
		try {
			MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(user, database,
						pwd.toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(
					server, Integer.valueOf(port)),
					Arrays.asList(mongoCredential));

		MongoDatabase db = mongoClient.getDatabase("myNewDB");
		System.out.println("Connect my NewDB");
		MongoCollection<Document> table = db.getCollection("myNewCollection1");
		System.out.println(table);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("x", 1);
		FindIterable<Document> cursor = table.find(searchQuery);
		MongoCursor<Document> ite= cursor.iterator();
		while (ite.hasNext()) {
			System.out.println(ite.next());
		}
		
//		Document document = new Document();
//		document.put("name", "mkyong");
//		document.put("age", 30);
//		document.put("createdDate", new Date());
//		table.insertOne(document);
		
		FindIterable<Document> dv = db.getCollection("myNewCollection1").find();
		MongoCursor<Document> ite1 = dv.iterator();
		while (ite1.hasNext()) {
			System.out.println(ite1.next());
		}
		
		mongoClient.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}