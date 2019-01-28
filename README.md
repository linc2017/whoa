# whoa

网络不好时，加载数据会比较慢。实际项目中，启动app后，可开启后台下载任务，下载数据到本地，然后UI侧从本地读取数据。

备注：server端不提供图片宽高，则app端无论什么解决方案都不完美。并且即使提供图片宽高，瀑布流这种设计本身就吃内存，性能再优化也不会特别好，故而当前很少有主流app采用这种技术。
除非小清新、文艺范等类型的app，认为瀑布流很有必要，然后实现时针对其做大量的专门优化，否则建议谨慎采用瀑布流。

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174319.jpg)

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174328.jpg)

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174341.jpg)
