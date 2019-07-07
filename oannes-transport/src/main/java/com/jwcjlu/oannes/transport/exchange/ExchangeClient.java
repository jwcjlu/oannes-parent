package com.jwcjlu.oannes.transport.exchange;

import com.jwcjlu.oannes.transport.RemoteException;
import com.oannes.common.Invocation;
import com.oannes.common.RpcResponse;
import com.oannes.common.URL;

public interface ExchangeClient {
	 void connect(URL url) throws RemoteException;
	Object request(Invocation  invocation) throws Exception;

}
