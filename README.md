# oannes-parent
oannes是一款rpc框架，通信采用netty框架，实现了
# feature
#### 1：框架里很多地方用到了spi（负载均衡，协议），这样可以自由定制选择相应的负载均衡，协议等等
#### 2：过滤链，可以自定义自己的过滤器，比如黑白名单等等
#### 3：与spring无缝对接
# 运行实例
#### 1：下载工程到idea
#### 2:编译工程
#### 3:打开工程oannes-demo下面有如下过程，分别启动demo-service和demo-consumer