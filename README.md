# spring-dynamic-params

spring boot mvc参数类型转换，支持转换为子类对象访问以及参数验证

### 使用示例：

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
~~~