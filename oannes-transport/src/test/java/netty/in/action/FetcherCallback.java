package netty.in.action;

public interface FetcherCallback {
 void onData(Data d) throws Throwable;
 void onError(Throwable  t);
}
