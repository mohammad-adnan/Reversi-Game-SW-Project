package com.my.project.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.my.project.message.Message;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("Reversi")
public interface ReversiService extends RemoteService {
	Message validateActionAndGetNewBoard(Message message);
}
