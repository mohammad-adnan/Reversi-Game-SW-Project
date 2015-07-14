package com.my.project.client.service;

import com.my.project.message.Message;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The client side stub for the RPC service.
 */
public interface ReversiServiceAsync {
	void validateActionAndGetNewBoard(Message message, AsyncCallback<Message> callback);
}
