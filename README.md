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

