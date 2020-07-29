### BeautyWeather
BeautyWeather 是一款天气及 Android 资讯应用。使用了聚合数据和玩 Android 开源 API。从原型图设计(Axure),项目排期（OmniPlan）, 架构图设计（OmniGraffle）,代码落地由个人独立完成。

### 业务架构图
1. 使用 Github 作为代码托管工具，采用 dev 分支开发 ，master 进行发布，通过 pull request 将代码合并到 master 分支
2. 使用 聚合数据免费 API 完成天气功能
3. 项目打包发布到 APP 商城 （待完成）
![bus-arch](https://github.com/guo842917077/BeautyWeather/blob/master/architecture/business-architecture.png)

### 代码架构图
1. 采用组件化的工程架构模式，将 APP 作为壳工程
2. Comm-permission 权限申请模块下沉为组件，通用的权限由该模块统一申请，模块独有或者危险的模块由各 APP 管理。防止其他模块删除权限，导致另一个模块无法使用的问题
3. Comm-config 是配置模块，管理所有配置以及业务跳转路径
4. Comm-network 是通用的网络框架，采用 Retrofit2.0 以及 Okhttp3.0，重写了 OkHttp 的 Call 方法，绑定了 Android Lifecycle 控件。
5. Database 下沉为基础组件，供其他模块在测试是调用。
6. BaseLibrary 基础组件，包含了流量监控，线程控制，日志打印，Md5 加密等常用工具类以及通用组件
![bus-arch](https://github.com/guo842917077/BeautyWeather/blob/master/architecture/Code%20Framework.png)

### 项目排期
![plan](https://github.com/guo842917077/BeautyWeather/blob/master/architecture/WeatherPlan.png)

