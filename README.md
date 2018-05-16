# PlugDevelopDemo
插件化开发学习

##### 一、项目结构说明
> app 是宿主
>
> bjjj 是插件module，用来生成插件apk的
>
> plugstandard 是用来定义插件开发的规范---也就是为了给插件传递生命周期函数；说白了，就是因为插件没有被安装，没有上下文对象，只能寄生于宿主，通过宿主的生命周期管理插件的生命周期

##### 二、功能实现
* 1.实现宿主到插件activity的跳转及Toast显示；
* 2.实现插件activityA到插件activityB的跳转；
* 3.实现插件Service启动；
* 4.实现插件BroadcastReceiver动态注册，并在插件中发送广播，然后插件中进行接收广播；

##### 三、实现原理
* 3.1 宿主activity跳转到插件activity的原理
> 宿主activity跳转到插件activity中，表面上是这样的；其实不是这样的，内部是宿主activity跳转到宿主中的proxyActivity，proxyActivity加载插件中的activity中的生命周期方法（用在自己的生命周期中调用插件中的activity中的生命周期）；
>
> 那为什么要用宿主的activity启动宿主的proxyActivity，而不是直接启动插件中的activity？
>
> 原因是：插件中的activity没有被安装（插件apk不是被手机正常安装，只是拷贝到data/data/包名/xxx），没有上下文Contenxt对象，所以插件中的activity只是一个'寄生'，必须依附于proxyActivity，通过proxyActivity的生命周期调用插件activity中的生命周期，
>           看似是插件自己的页面被加载，其实都是通过proxyActiviy来加载的；说白了就是：将proxyActivity对象的上下文Context传递到插件Activity中，调用proxyActiviy的上下文Context对象的方法，进行加载；

