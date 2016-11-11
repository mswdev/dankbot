package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 10/14/2016.
 */
public class Cache {

	private Object raw;

	public Cache(Object raw) {
		this.raw = raw;
	}

	public Object getCacheNode() {
		return Reflection.value("Cache#getCacheNode", raw);
	}

	public HashTable getHashTable() {
		return new HashTable(Reflection.value("Cache#getHashTable", raw));
	}

	public Queue getQueue() {
		return new Queue(Reflection.value("Cache#getQueue", raw));
	}

	public Object getRemaining() {
		return Reflection.value("Cache#getRemaining", raw);
	}

	public Object getSize() {
		return Reflection.value("Cache#getSize", raw);
	}


}
