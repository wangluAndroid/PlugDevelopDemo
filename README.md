# PlugDevelopDemo
插件化开发学习

##### 一、项目结构说明
> app 是宿主
>
> bjjj 是插件
>
> plugstandard 是用来定义插件开发的规范---也就是为了给插件传递生命周期函数；说白了，就是因为插件没有被安装，没有上下文对象，只能寄生于宿主，通过宿主的生命周期管理插件的生命周期

##### 1.实现activity跳转
