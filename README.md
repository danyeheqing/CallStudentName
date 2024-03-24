# 带界面的随机点名器

## 1，所需要的技术点

​	集合，IO，多线程，带权重的随机等

## 2，业务解释

### 界面一：

​	无特殊业务逻辑，显示一张图片，下面有一个go按钮，当点击按钮之后，跳转到第二个界面

​     ![登录界面](./data/img/1.png)

### 界面二：

​	作弊界面，有三个输入框需要输入，如果不输入，则不需要作弊。

业务要求：

​	1，第一个输入框中，输入不会被点到的人的名单，名字之间用逗号隔开

​	2，第二个输入框中，输入数字表示次数

​	3，第三个输入框中，输入名字

需要注意的细节：

​	1，获取第一个输入框中姓名的时候，由于多个名字是逗号隔开的，所以我们要切割，考虑到用户输入逗号时可能是英文的，也有可能是中文的，所以切割的代码要这么写：

```java
String s = "获取输入框中的名单";
//表示按照中文的逗号或者英文的逗号进行切割
String[] arr = s.split(",|，");
```

​	2，获取第二个输入框中只能是数字，不能是其他，如果是其他需要有弹框提示

​	3，第三个输入框中，不能有逗号了，如果有逗号视为多个姓名，需要有弹框提示

​	4，点击确定时，进行以上的检查，满足要求跳转第三个界面

 ![登录界面](./data/img/2.png)

### 界面三：

1，点击开始后，倒计时开始变化5,4,3,2,1,0。

2，点击开始后，按钮中的提示文字变为：随机抽取中。此时按钮无法再次点击。

3，点击开始后，中奖选手后面的名字也在不断的跳动

4，点击开始后，下方的照片也在不断的跳动，选手的名字和选手的照片需要对应起来。

5，当倒计时为0时，抽奖结束。按钮文字变为：开始。可继续再此抽取。

​	抽取时，需要满足界面二中作弊的逻辑。

6，如果随机多次，每次被点到的学生，再次被点到的概率在原先的基础上降低一半。

​	举例：80个学生，点名5次，每次都点到小A，概率变化情况如下：

  	第一次每人概率：1.25%。

  	第二次小A概率：0.625%。  其他学生概率：1.2579%  

  	第三次小A概率：0.3125%。  其他学生概率：1.261867%  

  	第四次小A概率：0.15625%。  其他学生概率：1.2638449%  

  	第五次小A概率：0.078125%。  其他学生概率：1.26483386%  



 ![登录界面](./data/img/3.png)



































