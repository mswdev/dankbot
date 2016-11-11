package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 10/14/2016.
 */
public class CacheNode extends Node {

	public CacheNode(Object raw) {
		super(raw);
	}

	@Override
	public Object getNext() {
		return Reflection.value("CacheNode#getNext", raw);
	}

	@Override
	public Object getPrevious() {
		return Reflection.value("CacheNode#getPrevious", raw);
	}


}
