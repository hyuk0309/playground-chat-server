# playground-chat-server

## Develop simple chat server

## Tech

Spring, Kotlin, WebSocket

## Ref
- Example Code
  - Blog : https://www.daddyprogrammer.org/post/4077/spring-websocket-chatting/
  - Bolg : https://velog.io/@dvmflstm/Spring-Webflux-Kotlin-Coroutine-Actor%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Websocket-Server-%EB%A7%8C%EB%93%A4%EA%B8%B0 

- Official Docs
  - Spring Docs : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html 


## Learned Concept

- WebSocket Protocol & Spring Support
  - ref : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#:~:text=26-,.%C2%A0WebSocket%20Support,-This%20part%20of

- STOMP & Spring Support
  - ref : https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#:~:text=26.4-,STOMP,-The%20WebSocket%20protocol
  

## Issue
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