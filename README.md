# 一、工具类库简介

## 1、当前存在的问题

当前每个项目都维护着自己的工具类，甚至每个模块都有自己的工具类，造成工具类冗余。

## 2、意义

1、规范化工具类的使用；

2、让项目代码清晰明了；

3、方便开发同学的使用，提高开发效率；

## 3、规划

1、梳理工具类库的代码，编辑工具类使用文档；

2、将各个项目的工具类统一到工具类库；

3、添加常用的工具类到工具类库；

4、新增扩展函数类；

5、难以理解的方法添加使用demo；

6、最后形成易阅读易使用的工具类库及文档。

## 4、文档编写注意

1、当前工具类文档可能有纰漏，使用需要验证是否有bug；

2、有的类写了不属于这个类的方法，尽量不要使用；

3、新增方法，尽量归到对应的类中，添加好注释；

4、使用第三方库的，尽量使用代理模式封装，方便替换三方库。

5、工具类和方法见名知意。

# 二、ChangeLog

|版本|新增功能|Contributor|
|:----|:----|:----:|:----|
|V1.0.0|    |周鑫|

# 三、扩展函数

按照字母顺序进行文档编辑

----------------------------------------------------------

### AnimExt

```plain
startTweenAnimation  ： 加载补间动画
```
### BigDecimalExt

```plain
getNumber  ： 获取小数，并至少保留一位小数
```
### DateExt

```plain
getDateYMD：          日期格式化
dateYMD2TimeStamp：   日期格式化字符串转成时间戳
```
### EditTextExt

```plain
afterTextChanged：            EditText输入完之后监听
onActionSearchListener：      action监听
setTextAndSelectEnd：         设置选择的最后一位
```
### RecyclerViewExt

```plain
 setOnItemClickListener:  设置item点击事件
 setOnItemClickListener:  item的子控件的点击事件
```
### ViewExt

```plain
visible：
invisible：
gone：
reverseVisibility：
changeVisible：
isVisible：
isInvisible：
isGone：
setPadding：
postDelayed：
toBitmap
createBitmapSafely
onGlobalLayout
afterMeasured
```
# 四、常用工具类

按照字母顺序进行文档编辑

----------------------------------------------------------

## A

### ActivityManagerUtils

```plain
addActivity          Activity入栈
finishActivity       Activity出栈
currentActivity      获取当前栈顶Activity
finishAllActivity    清理栈
exitApp              退出应用程序
```
### AppUtils

```plain
getVersion               API版本号(如:14)
getOsVersion             系统版本号(如:4.0)
getPhoneModel            手机型号(如:Galaxy Nexus)
getAppVersionName        获取当前应用的VersionName
getVersionCode           获取当前应用的VersionCod
getProcessName           获取当前进程名称
isAppInstalled           检测某个应用是否安装
launchOrDownloadApp      下载或者打开App
goToMarket               去市场下载页面
getInputMethodManager    获取 InputMethodManager
```
## B

### BarUtils

```plain
getStatusBarHeight       : 获取状态栏高度（px）
isStatusBarVisible       : 判断状态栏是否可见
setStatusBarLightMode    : 设置状态栏是否为浅色模式
isStatusBarLightMode     : 判断状态栏是否为浅色模式
setStatusBarColor        : 设置状态栏颜色
getActionBarHeight       : 获取 ActionBar 高度
setStatusBarVisibility   : 设置状态栏是否可见
```
### BigDecimalUtils

```plain
add              : 提供精确的加法运算
substract        : 提供精确的减法运算
multiply         : 提供精确的乘法运算
divide           : 提供（相对）精确的除法运算.当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入.
round            : 提供精确的小数位四舍五入处理
remainder        : 取余数
formatMoney      : 金额分割，四舍五人金额
compareBigDecimal: 比较大小
adjustDouble     : 获取自己想要的数据格式
```
### BitmapUtil

```plain
saveJpeg        bitmap生成jpeg
scaleBitmap     缩放bitmap
```
## C

### ClipboardUtils

```plain
copyText    复制文本到剪贴板
getText     获取剪贴板的文本
copyUri     复制uri到剪贴板
getUri      获取剪贴板的uri
copyIntent  复制意图到剪贴板
getIntent   获取剪贴板的意图
```
### CloneUtils

```plain
deepClone    深拷贝
```
## D

### DateUtils

```plain
getDateNow                  获取当前日期的字符串 ( yyyy-MM-dd HH:mm:ss )
formatTime                 将时间戳转换日期字符串
formatDate                 将 Date 转换日期字符串
parseDate                  将时间戳转换成 Date
parseLong                  解析时间字符串转换为 long 毫秒 ( 默认表示 time 属于 yyyy-MM-dd HH:mm:ss 格式 )
parseToString              转换时间为指定字符串
getTimeDiffMinute          获取时间差 ( 分钟 )
getTimeDiffHour            获取时间差 ( 小时 )
getTimeDiffDay             获取时间差 ( 天 )
getTimeDiff                获取时间差 ( 传入时间 - 当前时间 )
getYear                    获取年
getMonth                   获取月 ( 0 - 11 ) + 1
getDay                     获取日
getWeek                    获取日期是星期几
get24Hour                  获取时 ( 24 )
get12Hour                  获取时 ( 12 )
getMinute                  获取分
getSecond                  获取秒
timeAddZero                时间补 0 处理 ( 小于 10, 则自动补充 0x )
isLeapYear                 判断是否闰年
getMonthDayNumberAll       根据年份、月份, 获取对应的天数 ( 完整天数, 无判断是否属于未来日期 )
getYearMonthNumber         根据年份, 获取对应的月份
getMonthDayNumber          根据年份、月份, 获取对应的天数
getArrayToHH               生成 HH 按时间排序数组
getListToHH                生成 HH 按时间排序集合
getArrayToMM               生成 MM 按时间排序数组
getListToMM                生成 MM 按时间排序集合
getArrayToHHMM             生成 HH:mm 按间隔时间排序数组
getListToHHMM              生成 HH:mm 按间隔时间排序集合
getListToHHMMPosition      获取 HH:mm 按间隔时间排序的集合中, 指定时间所在索引
secToTimeRetain            传入时间, 获取时间 ( 00:00:00 格式, 不处理大于一天 )
convertTimeArys            传入时间, 时间参数 ( 小时、分钟、秒 )
millisToFitTimeSpan        转换时间
millisToTimeArys           转换时间为数组
isInTimeHHmm               判断时间是否在 [startTime, endTime] 区间, 注意时间格式要一致
isInTimeHHmmss             判断时间是否在 [startTime, endTime] 区间, 注意时间格式要一致
isInTime                   判断时间是否在 [startTime, endTime] 区间, 注意时间格式要一致
isInDate                   判断时间是否在 [startTime, endTime] 区间, 注意时间格式要一致
getEndTimeDiffHHmm         获取指定时间距离该时间第二天的指定时段的时间 ( 判断凌晨情况 )
getEndTimeDiff             获取指定时间距离该时间第二天的指定时段的时间差 ( 判断凌晨情况 )
getZodiac                  获取生肖
getConstellation           获取星座
getConstellationDate       获取星座日期
```
## E

### EncodeUtils

```plain
urlEncode          : URL 编码
urlDecode          : URL 解码
base64Encode       : Base64 编码
base64Encode2String: Base64 编码
base64Decode       : Base64 解码
htmlEncode         : Html 编码
htmlDecode         : Html 解码
binaryEncode       : 二进制编码
binaryDecode       : 二进制解码
```
### EncryptUtils

```plain
encryptMD2, encryptMD2ToString                        : MD2 加密
encryptMD5, encryptMD5ToString                        : MD5 加密
encryptMD5File, encryptMD5File2String                 : MD5 加密文件
encryptSHA1, encryptSHA1ToString                      : SHA1 加密
encryptSHA224, encryptSHA224ToString                  : SHA224 加密
encryptSHA256, encryptSHA256ToString                  : SHA256 加密
encryptSHA384, encryptSHA384ToString                  : SHA384 加密
encryptSHA512, encryptSHA512ToString                  : SHA512 加密
encryptHmacMD5, encryptHmacMD5ToString                : HmacMD5 加密
encryptHmacSHA1, encryptHmacSHA1ToString              : HmacSHA1 加密
encryptHmacSHA224, encryptHmacSHA224ToString          : HmacSHA224 加密
encryptHmacSHA256, encryptHmacSHA256ToString          : HmacSHA256 加密
encryptHmacSHA384, encryptHmacSHA384ToString          : HmacSHA384 加密
encryptHmacSHA512, encryptHmacSHA512ToString          : HmacSHA512 加密
encryptDES, encryptDES2HexString, encryptDES2Base64   : DES 加密
decryptDES, decryptHexStringDES, decryptBase64DES     : DES 解密
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES 加密
decrypt3DES, decryptHexString3DES, decryptBase64_3DES : 3DES 解密
encryptAES, encryptAES2HexString, encryptAES2Base64   : AES 加密
decryptAES, decryptHexStringAES, decryptBase64AES     : AES 解密
encryptRSA, encryptRSA2HexString, encryptRSA2Base64   : RSA 加密
decryptRSA, decryptHexStringRSA, decryptBase64RSA     : RSA 解密
rc4                                                   : rc4 加解密
```
## F

### FileUtil

```plain
isSDCardExist    检查sdcard是否挂载
writeStr         写文件
readStr          读文件
saveBitmap       保存图片到本地
base64File       文件base64转码
streamToFile     保存流到本地
getFileBytes     文件转换为byte数组
deleteDirectory  删除目录下的所有文件
deleteFile       删除文件
isFileExit       文件是否存在
copyFile           复制单个文件
```
## I

### ImageUtils

```plain
bitmap2Bytes, bytes2Bitmap      : bitmap 与 bytes 互转
drawable2Bitmap, bitmap2Drawable: drawable 与 bitmap 互转
drawable2Bytes, bytes2Drawable  : drawable 与 bytes 互转
view2Bitmap                     : view 转 bitmap
getBitmap                       : 获取 bitmap
scale                           : 缩放图片
clip                            : 裁剪图片
skew                            : 倾斜图片
rotate                          : 旋转图片
getRotateDegree                 : 获取图片旋转角度
toRound                         : 转为圆形图片
toRoundCorner                   : 转为圆角图片
addCornerBorder                 : 添加圆角边框
addCircleBorder                 : 添加圆形边框
addReflection                   : 添加倒影
addTextWatermark                : 添加文字水印
addImageWatermark               : 添加图片水印
toAlpha                         : 转为 alpha 位图
toGray                          : 转为灰度图片
fastBlur                        : 快速模糊
renderScriptBlur                : renderScript 模糊图片
stackBlur                       : stack 模糊图片
save                            : 保存图片
save2Album                      : 保存图片到相册
isImage                         : 根据文件名判断文件是否为图片
getImageType                    : 获取图片类型
compressByScale                 : 按缩放压缩
compressByQuality               : 按质量压缩
compressBySampleSize            : 按采样大小压缩
getSize                         : 获取图片尺寸
```
## J

### JsonUtils

```plain
toJson            : 对象转json
fromJson          : json转对象
map2Json          : Map转为JSONObject
collection2Json   : 集合转换为JSONArray
object2Json       : Object对象转换为JSONArray
string2JSONObject : json字符串生成JSONObject对象
object2json       : 对象转换为Json
list2json         : List集合转换为Json
array2json        : 对象数组转换为Json
set2json          : Set集合转为Json
string2json       : 字符串转换为Json
```
## K

### KeyboardUtils

```plain
setDelayMillis                      设置延迟时间
setSoftInputMode                       设置 Window 软键盘是否显示
judgeView                          设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
isSoftInputVisible                 判断软键盘是否可见
registerSoftInputChangedListener       注册软键盘改变监听
registerSoftInputChangedListener2  注册软键盘改变监听
fixSoftInputLeaks                  修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
toggleKeyboard                     自动切换键盘状态, 如果键盘显示则隐藏反之显示
openKeyboard                           打开软键盘
openKeyboardDelay                  延时打开软键盘
closeKeyboard                      关闭软键盘
closeKeyBoardSpecial                   关闭软键盘
closeKeyBoardSpecialDelay          延时关闭软键盘
closeKeyboardDelay                 延时关闭软键盘
```
## L

### ListUtils

```plain
getSize  数据源长度
isEmpty  数据源是否为空
```
### LocationUtils

```plain
isLocServiceEnable   手机是否开启位置服务
```
## N

### NumberFormatUtil

```plain
parseDouble      四舍五入转换成整型
parseBigDecimal  四舍五入转换成整型
changeOneDecimal 保留一位小数
parseDouble      四舍五入转换成整型
```
### NetUtils

```plain
isNetworkConnected       判断是否有网络
getNetworkType           获取当前网络类型 
```
### NotificationUtil

```plain
isOpenNotify        当前应用是否开启了通知权限
```
## P

### PermissionUtils

```plain
checkPermission  检查权限
```
## R

### ReflectUtils

```plain
reflect    : 设置要反射的类
newInstance: 实例化反射对象
field      : 设置反射的字段
method     : 设置反射的方法
get        : 获取反射想要获取的
```
 
### RegexUtils

```plain
isMobileSimple                           : 简单验证手机号
isMobileExact                            : 精确验证手机号
isTel                                    : 验证电话号码
isIDCard15                               : 验证身份证号码 15 位
isIDCard18                               : 简单验证身份证号码 18 位
isIDCard18Exact                          : 精确验证身份证号码 18 位
isEmail                                  : 验证邮箱
isURL                                    : 验证 URL
isZh                                     : 验证汉字
isUsername                               : 验证用户名
isDate                                   : 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
isIP                                     : 验证 IP 地址
isMatch                                  : 判断是否匹配正则
getMatches                               : 获取正则匹配的部分
getSplits                                : 获取正则匹配分组
getReplaceFirst                          : 替换正则匹配的第一部分
getReplaceAll                            : 替换所有正则匹配的部分
RegexConstants.REGEX_DOUBLE_BYTE_CHAR    : 双字节
RegexConstants.REGEX_BLANK_LINE          : 空行
RegexConstants.REGEX_QQ_NUM              : QQ 号
RegexConstants.REGEX_CHINA_POSTAL_CODE   : 邮编
RegexConstants.REGEX_INTEGER             : 整数
RegexConstants.REGEX_POSITIVE_INTEGER    : 正整数
RegexConstants.REGEX_NEGATIVE_INTEGER    : 负整数
RegexConstants.REGEX_NOT_NEGATIVE_INTEGER: 非负整数
RegexConstants.REGEX_NOT_POSITIVE_INTEGER: 非正整数
RegexConstants.REGEX_FLOAT               : 浮点数
RegexConstants.REGEX_POSITIVE_FLOAT      : 正浮点数
RegexConstants.REGEX_NEGATIVE_FLOAT      : 负浮点数
RegexConstants.REGEX_NOT_NEGATIVE_FLOAT  : 非负浮点数
RegexConstants.REGEX_NOT_POSITIVE_FLOAT  : 非正浮点数
```
## S

### SizeUtils

```plain
dp2px, px2dp     : dp 与 px 转换
sp2px, px2sp     : sp 与 px 转换
applyDimension   : 各种单位转换
forceGetViewSize : 在 onCreate 中获取视图的尺寸
measureView      : 测量视图尺寸
getMeasuredWidth : 获取测量视图宽度
getMeasuredHeight: 获取测量视图高度
```
### **StorageUtils**

```plain
getTotalSize         根存储路径获取内存空间总大小
getAvailableSize     根据存储路径获取剩余空间大小
```
### **SystemUtils**

```plain
getSystemVersion     获取当前手机系统版本号
getSystemModel       获取手机型号
isOpenNotify         当前应用是否开启了通知权限
getSystem         获取手机系统
getDeviceBrand     获取手机厂商
```
## V

### VibrateUtils

```plain
vibrate: 震动
cancel : 取消
```
 
## Z

### ZipUtils

```plain
zipFiles          : 批量压缩文件
zipFile           : 压缩文件
unzipFile         : 解压文件
unzipFileByKeyword: 解压带有关键字的文件
getFilesPath      : 获取压缩文件中的文件路径链表
getComments       : 获取压缩文件中的注释链表
```
 
