package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatRoom
import elvis.chat.playgroundchatserver.model.LoginInfo
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/chat")
class ChatRoomController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val chatRoomRepository: ChatRoomRepository,
) {
    @GetMapping("/room")
    fun rooms(model: Model) = "/chat/room"

    @GetMapping("/room/enter/{roomId}")
    fun roomDetail(model: Model, @PathVariable roomId: String): String {
        model.addAttribute("roomId", roomId)
        return "/chat/roomdetail"
    }

    @GetMapping("/rooms")
    @ResponseBody
    fun room(): List<ChatRoom> {
        val chatRooms = chatRoomRepository.findAllRoom()
        chatRooms.forEach { room -> room.userCount = chatRoomRepository.getUserCount(room.roomId) }
        return chatRooms
    }

    @PostMapping("/room")
    @ResponseBody
    fun createRoom(@RequestParam name: String) = chatRoomRepository.createChatRoom(name)

    @GetMapping("/room/{roomId}")
    @ResponseBody
    fun roomInfo(@PathVariable roomId: String) = chatRoomRepository.findRoomById(roomId)

    @GetMapping("/user")
    @ResponseBody
    fun getUserInfo(): LoginInfo {
        val auth = SecurityContextHolder.getContext().authentication
        val name = auth.name
        return LoginInfo(name, jwtTokenProvider.generateToken(name))
    }
}
