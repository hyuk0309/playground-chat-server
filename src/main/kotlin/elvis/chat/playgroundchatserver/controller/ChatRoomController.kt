package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
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
    private val chatRoomRepository: ChatRoomRepository,
) {
    @GetMapping("/room")
    fun rooms(model: Model) = "/chat/room"

    @GetMapping("/rooms")
    @ResponseBody
    fun room() = chatRoomRepository.findAllRoom()

    @PostMapping("/room")
    @ResponseBody
    fun createRoom(@RequestParam name: String) = chatRoomRepository.createChatRoom(name)

    @GetMapping("/room/enter/{roomId}")
    fun roomDetail(model: Model, @PathVariable roomId: String): String {
        model.addAttribute("roomId", roomId)
        return "/chat/roomdetail"
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    fun roomInfo(@PathVariable roomId: String) = chatRoomRepository.findRoomById(roomId)
}
