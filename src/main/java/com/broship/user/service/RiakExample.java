package com.broship.user.service;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;

public class RiakExample {

	public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient("127.0.0.1");
//		Namespace ns = new Namespace("default", "my_bucket");
//		Location location = new Location(ns, "my_key");
//		RiakObject riakObject = new RiakObject();
//		riakObject.setValue(BinaryValue.create("my_value"));
//		StoreValue store = new StoreValue.Builder(riakObject).withLocation(location).build();
//		client.execute(store);
		blabla(client);
	}

	public static void blabla(RiakClient client) throws ExecutionException, InterruptedException {
		System.out.println("blabla");
		Namespace ns = new Namespace("default", "my_bucket");
		Location location = new Location(ns, "my_key");
		FetchValue fv = new FetchValue.Builder(location).build();
		FetchValue.Response response = client.execute(fv);
		RiakObject obj = response.getValue(RiakObject.class);
		System.out.println(obj.getValue().toString());
	}

}
