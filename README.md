# whoa

本项目将于近期内删除。

2019-1-29更新：已将瀑布流改为普通相册浏览模式。
下方第二张图片为新加，分辨率明显比第三张低。显然由于一开始就有了图片宽高，所以可以很方便地进行性能优化。仔细看微信相册主界面，很多小图所在的页面，比点进去查看大图的分辨率低，滑动时自然也不卡。分辨率具体调到多少，要看结合相册框具体大小、适配的客户群体手机状况等综合考量。
项目根目录waterfall-backup文件夹，是原来的瀑布流设计，将其替换项目里同名文件，即可查看原来的瀑布流设计方案。
第二、三张图片内容不一致，是由于网站图片内容发生了变化。

瀑布流：
网络不好时，加载数据会比较慢。实际项目中，启动app后，可开启后台下载任务，（预先）下载（部分）数据到本地，然后UI侧从本地读取数据。
备注：server端不提供图片宽高，则app端无论什么解决方案都不完美。并且即使提供图片宽高，瀑布流这种设计本身就吃内存，性能再优化也不会特别好，故而当前很少有主流app采用这种技术。
除非小清新、文艺范等类型的app，认为瀑布流很有必要，然后实现时针对其做大量的专门优化，否则实际项目中建议谨慎采用瀑布流。

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174319.jpg)

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190129-102037.jpg)

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174328.jpg)

![image](https://github.com/linc2017/whoa/blob/master/Screenshot_20190126-174341.jpg)
