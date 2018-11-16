# assistant_app
1. 项目主要是用来熟练整个项目从框架搭建到业务开发流程
2. 业务采用mvvm模式来实现，通过LiveData+ViewModel来处理数据，在View中，通过观察data的变化来实现视图更新
3. 网络模块采用RxJava + Retrofit + okHttp，okHttp主要是执行网络到真实请求，通过OkHttp基本配置我们可以设置网络超时、读写超时，通过设置Interceptor可以设置请求头等信息
