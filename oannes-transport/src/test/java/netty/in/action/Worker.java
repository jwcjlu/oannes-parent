
package netty.in.action;

public class Worker {
	public void doWorker(){
		MyFetcher fetch=new MyFetcher(new Data(1,0));
		FetcherCallback callback=new FetcherCallback() {

			@Override
			public void onData(Data d) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println(" Data received:"+d);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("An error accour"+t.getMessage());
				
			}
			
			
		};
		
		fetch.fetchData(callback);
	}
public static void main(String[] args) {
	new Worker().doWorker();;
}
}
