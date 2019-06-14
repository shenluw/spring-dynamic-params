# spring-dynamic-params

spring boot mvc参数类型转换，支持转换为子类对象访问以及参数验证

### 使用示例：

#### 添加依赖

~~~groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}	
dependencies {
        // 低版本gradle使用
        // compile 'com.gitee.luw1683:spring-dynamic-params:Tag'
        // 版本号查看仓库tag
        implementation 'com.gitee.luw1683:spring-dynamic-params:0.1.3'
}
~~~

#### 方法一
- 对实体对象添加@Sldp注解
~~~java
@Sldp
public class Animal {
    private String name;
    private int age;
    ...
}
public class Dog extends Animal {
    private String face;
    private String speed;
    ...
}
~~~

- 创建添加访问接口
~~~java
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    @RequestMapping("/1")
    public void test1(Animal a) {
        log.info("test1: {}", a);
    }
}
~~~

- 测试接口

request:

    http://xxxxx/1?name=test&age=12&speed=120&sldp=top.shenluw.sldp.Dog

response: 
    
    a 实际类型为 Dog


#### 方法二
为方法添加@Sldp注解
删除Animal上的注解
~~~java
public class Animal {
    private String name;
    private int age;
    ...
}
~~~

- 修改访问接口

~~~java
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    @RequestMapping("/1")
    public void test1(@Sldp Animal a) {
        log.info("test1: {}", a);
    }
}
~~~

- 测试接口

request:

    http://xxxxx/1?name=test&age=12&speed=120&sldp=top.shenluw.sldp.Dog
response: 
    
    a 实际类型为 Dog
    

#### 方法三
实体对象实现DynamicModel接口
删除Animal上的注解
~~~java
public class Animal implements DynamicModel {
    private String name;
    private int age;
    ...
}
~~~

- 修改访问接口

~~~java
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    @RequestMapping("/1")
    public void test1(Animal a) {
        log.info("test1: {}", a);
    }
}
~~~

- 测试接口

request:

    http://xxxxx/1?name=test&age=12&speed=120&sldp=top.shenluw.sldp.Dog
response: 
    
    a 实际类型为 Dog
    
#### 使用json对象请求

接口可以实现参数为json格式的请求并转换为对应的格式

修改访问接口，设置注解参数为Json类型即可支持
~~~java
@RestController
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    @RequestMapping("/1")
    public void test1(@Sldp(type = ModelType.Json) Animal a) {
        log.info("test1: {}", a);
    }
    
}
~~~

修改请求参数，添加json数据对应的字段即可实现

    http://xxxxx/1?sldpJson={"name":"test name","age":12}&sldp=top.shenluw.sldp.Dog
    
#### 支持多个对象参数

修改访问接口，设置注解参数别名即可支持
~~~java
@RestController
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    @RequestMapping("/1")
    public void test1(@Sldp(name = "a") Animal a, @Sldp(name = "b") Animal b) {
        log.info("test1: {} {}", a, b);
    }
    
}
~~~
修改请求参数，为不同别名设置不通类型

    http://xxxxx/1?name=test&age=12&face=mm&speed=1233&height=11&a=top.shenluw.sldp.BDog&b=top.shenluw.sldp.Cat

#### 为类型设置别名
修改yaml,将top.shenluw.sldp.Dog别名修改为myName
~~~yaml
sldp:
  # 为类型设置别名
  type-alias:
      myName: top.shenluw.sldp.Dog
~~~

把请求参数中的sldp值修改为myName即可实现

    http://xxxxx/1?sldpJson={"name":"test name","age":12}&sldp=myName

#### 添加加密设置

~~~yaml
sldp:
  # 开启加密，默认关闭
  enable-security: true
  # 开启加密后是否使用全局加密，默认只对设置了SlSecurity注解的方法使用加密
  default-security: true
~~~

当前加密只支持json方式，默认只提供了base64的测试样例

添加新的加密方法只需要实现Encryptor接口，然后配置成bean既可以
~~~java
@Bean
public Encryptor myEncryptor(){
    return MyEncryptor();
}
~~~


#### 设置默认配置

~~~yaml
sldp:
  enable: true # 是否启用
  # 设置默认处理器，下面这个为参数默认以json方式解析
  default-processor: top.shenluw.sldp.processor.JacksonDynamicParamsMethodProcessor
  # 设置json解析时携带数据的参数名称，就是把上面的sldpJson改为mydata 
  json-data-name: mydata
  # 设置携带实际类型的参数名称，就是把上面的sldp改为mytype
  type-name: mytype
  # 为类型设置别名
  type-alias:
      myName: top.shenluw.sldp.Dog
~~~

#### Jackson多态配置

可以在字段或者类上配置 @JsonTypeInfo,如果要直接使用配置文件中的type-alias，则需要添加 @JsonTypeResolver(TypeAliasResolverBuilder.class)

~~~java
public class Mix extends Animal {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonTypeResolver(TypeAliasResolverBuilder.class)
    private Animal dog;
}
~~~