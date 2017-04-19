package netty.in.action;

public class MyFetcher implements Fetcher {
	final Data data;
	public MyFetcher(Data data){
		this.data=data;
	}

	@Override
	public void fetchData(FetcherCallback callBack) {
		// TODO Auto-generated method stub
			try {
				callBack.onData(data);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				callBack.onError(e);
			}
	}

}
