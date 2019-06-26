# SQL工具集

源码网址: `https://github.com/Iamttp/SQLUtil`

### SQL QBE生成sql语句软件

通过设置表格，自动生成SQL语句

大概使用见:

`https://blog.csdn.net/Jaihk662/article/details/80081717`

![avatar](src/Picture/2.PNG)

![avatar](src/Picture/3.PNG)

![avatar](src/Picture/4.PNG)

基本功能0.2(如下图 版本0.2完成 (1)):

![avatar](src/Picture/1.PNG)

功能1.0（加入多张表，笛卡尔积 版本1.0完成 (1)(2)(3)）

![avatar](src/Picture/5.PNG)

TODO 小问题：

1. 界面不够友好
2. 符号>,<,=后面没有加引号
   
TODO 待解决问题 ：

1. 输出格式存在空格问题（有时会没有空格隔开）
2. 没有考虑或运算（OR）(这个。。。)

### SQL 关系模型设计工具集

完成了最小依赖集求解, 候选集

![avatar](src/Picture/6.png)

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
> 
> 候选键 BD

// 该情况已解决
> A->B,B->C,AB->C
> 
> 最小依赖集最终结果：
> 
> [A->B, B->C]
> 
> 候选键 A

// 该情况已解决
> A->BC,CD->E,B->D,E->A
> 
> 候选键 A,CD,BC,E

// **左冗余** 已解决 影响到3NF求解
> A->C,AC->D
> 
> 最小依赖集最终结果：
> 
> [A->C, AC->D]
>
> 候选键 A

// AB->DE,B->DE,C->C
> 最小依赖集最终结果：
> 
> [B->D, B->E]
> 
> 候选键最终结果：
> 
> [ABC]
> 
> 分解为3NF模式集为：[BED, ABC]

// A->B,C->D,E->E
> 最小依赖集最终结果：
> 
> [A->B, C->D]
> 
> 候选键最终结果：
> 
> [ACE]
> 
> 分解为3NF模式集为：[AB, CD, ACE]

![avatar](src/Picture/7.png)

### SQL 快速测试SQL工具

优点：

        内置了java的内存型数据库 hsqldb
        快速，方便，不需要安装配置数据库
        将初始化数据尽可能做到了全自动
        适合数据库新手快速练习SQL
        适合老手快速测试SQL
            
缺点：
        
        不够稳定，支持类型较少，没考虑NULL
        执行SQL时，没有完美支持UPDATE\INSERT语句测试（一种方法是直接采用下方第三步,可视化修改）

使用方法

1. 修改上方表格的第一行用于定义表的列，规则为列名+空格+类型名
    当前支持类型为` CHAR VARCHAR BIT FLOAT INT `;
    
2. 点击创建表并生成随机数据则直接根据类型生成测试的随机数据

3. （可选） 直接在上方表格修改数据，会自动提交

4. 点击修改下方文本框执行SQL, 暂时较完美支持SELECT，并显示在下方表格

![avatar](src/Picture/8.png)

// TODO 添加按钮完成非SELECT语句的执行和表格显示