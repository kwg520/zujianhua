# zujianhua
自己写的一个组件化架构
1.首先创建注解和注解生成器，
2.创建一个arouter 中间类，让主app和所有其他m0udle 全部依赖它，
3.通过注解在编译时 将 注解的activity 注册到arouter类中的，
4.通过调用通过key 到注册表中寻找注册过的activity 通过中间类进行路由
5.这样就实现了主app 和各个moule之间的通信。(本质上是activity的静态跳转，解决了通过全包名书写较为麻烦不方便管理，书写较为繁琐等诸多问题)
