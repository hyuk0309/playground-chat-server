# playground-chat-server

## Intro & Motivation
This service is simple chatting server using websocket protocol.
The reason why I develop this service is to learn websocket concept.

## Tech

### Server
Kotlin, Spring(MVC, Websocket, Redis, Security, Freemarker), JJWT ..

### Front
Vue

If you want to know more information about my project's tech referencing [gradle file](build.gradle.kts)

## Architecture

[To Do]

## Feature

### HTTP
- `/chat/rooms` : Get all chat-room information
- `/chat/room/{roomId}` : Get specified chat-room information
- `/chat/room?name="chatRoomName` : create new chat-room
- `/chat/user` : get current user info

### STOMP(Websocket)
- `/ws-stomp` : connect stomp
- `/sub/chat/room/{roomId}` : subscribe roomId topic
- `/pub/chat/message` : publish message to specific topic (message include topic information)

## Get Start (local environment)
``` shell
# clone project and move to project folder
git clone https://github.com/hyuk0309/playground-chat-server.git
cd ./playground-chat-server/

# build and execute project
./gradlew clean build
cd ./build.libs
java -jar playground-chat-server-0.0.1-SNAPSHOT.jar
```

## What I Learn

### Learned Concept

- WebSocket Protocol & Spring Support
  - ref : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#:~:text=26-,.%C2%A0WebSocket%20Support,-This%20part%20of

- STOMP & Spring Support
  - ref : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#:~:text=26.4-,STOMP,-The%20WebSocket%20protocol

- spring data redis pub sub
  - ref
    - redis docs : https://redis.io/docs/manual/pubsub/
    - spring docs : https://spring.io/guides/gs/messaging-redis/
    - blog : https://www.baeldung.com/spring-data-redis-pub-sub
    - blog : https://brunch.co.kr/@springboot/374

- `@Resource` principle
  - code
  ``` kotlin
  @Repository
  class ChatRoomRepository {

    @Resource(name = "redisTemplate")
    private lateinit var hashOpsChatRoom: HashOperations<String, String, ChatRoom>

    @Resource(name = "redisTemplate")
    private lateinit var hashOpsEnterInfo: HashOperations<String, String, String>

    @Resource(name = "redisTemplate")
    private lateinit var valueOps: ValueOperations<String, String>
    ...
  }
  ``` 
  - Question? : How Spring Inject Dependency when `name` bean and target bean are different.
    - Answer : there is converter helping that task.
      - ref
        - logic start : org.springframework.beans.factory.support.AbstractBeanFactory.adaptBeanInstance
        - key class : org.springframework.data.redis.core.HashOperationsEditor


### Issue
- FreeMarkerViewReolver's default suffix
  - Reason : dafult suffix : .htlh
    - ref : https://github.com/spring-projects/spring-boot/issues/15131
  - Solution : change `ftl` extension to `ftlh`
- Can't find Webjar Dependency
  - Solution : add Resource Handler
    - ref : https://www.baeldung.com/maven-webjars
- SLF4J multi binding Issue
  - Reason : SLF4J will bind with one logging framework at a time. but my classpath have two binder(provider)
    - ref : https://www.baeldung.com/slf4j-classpath-multiple-bindings
  - Solution : exclude one logging framework using gradle.
    - ref :
      - Listing dependency tree : https://docs.gradle.org/7.3/userguide/viewing_debugging_dependencies.html#sec:listing_dependencies
      - exclude specific module : https://docs.gradle.org/current/userguide/dependency_downgrade_and_exclude.html#sec:excluding-transitive-deps
        - how to know group and module name : https://stackoverflow.com/questions/43582247/how-to-know-if-duplicate-library-is-module-or-group
- Spring Securit's WebSecurityConfigurerAdapter was deprecated
  - ref : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
- JJWT(jsonwebtoken) deprecated api
  - Reason : confused api
  - ref : https://stackoverflow.com/questions/40252903/static-secret-as-byte-key-or-string/40274325#40274325
  
## Reference
- Example Code
  - Blog : https://www.daddyprogrammer.org/post/4077/spring-websocket-chatting/
  - Bolg : https://velog.io/@dvmflstm/Spring-Webflux-Kotlin-Coroutine-Actor%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Websocket-Server-%EB%A7%8C%EB%93%A4%EA%B8%B0 

- Official Docs
  - Spring Docs : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html 
  