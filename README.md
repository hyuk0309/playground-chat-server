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


## 개념

### WebSocketSession
Client들은 Server에 접속하면 개별의 WebSocketSession을 갖는다.

### Issue
- FreeMarkerViewReolver's default suffix
  - dafult suffix : .htlh
    - ref : https://github.com/spring-projects/spring-boot/issues/15131