# SQL工具集

### SQL QBE生成sql语句软件

通过设置表格，自动生成SQL语句

大概使用见:

`https://blog.csdn.net/Jaihk662/article/details/80081717`

![avatar](src/2.PNG)

![avatar](src/3.PNG)

![avatar](src/4.PNG)

基本功能0.2(如下图 版本0.2完成 (1)):

![avatar](src/1.PNG)

功能1.0（加入多张表，笛卡尔积 版本1.0完成 (1)(2)(3)）

![avatar](src/5.PNG)

TODO 小问题：

1. 界面不够友好
2. 符号>,<,=后面没有加引号
   
TODO 待解决问题 ：

1. 输出格式存在空格问题（有时会没有空格隔开）
2. 没有考虑或运算（OR）(这个。。。)

### SQL 关系模型设计工具集

#### 完成了最小依赖集求解

![avatar](src/6.png)

正则表达式在线测试：
`http://tool.oschina.net/regex/`

// 冗余及时清除已解决
> A->C,C->A,B->A,B->C,D->A,D->C,BD->A
> 
> 单属性去重为：[A->C, C->A, B->A, B->C, D->A, D->C, BD->A]
> 
> 最小依赖集最终结果：
> 
> [A->C, C->A, B->C, D->C]

// 该情况已解决
> A->B,B->C,AB->C
> 
> 最小依赖集最终结果：
> 
> [A->B, B->C]

// TODO **左冗余** 暂未解决 
> A->C,AC->D
> 
> 最小依赖集最终结果：
> 
> [A->C, AC->D]

#### TODO 完成候选键

A->BC,CD->E,B->D,E->A

A,CD,BC,E