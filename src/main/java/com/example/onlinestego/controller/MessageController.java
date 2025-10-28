package com.example.onlinestego.controller;

import com.example.onlinestego.model.Message;
import com.example.onlinestego.model.User;
import com.example.onlinestego.service.MessageService;
import com.example.onlinestego.service.UserService;
import com.example.onlinestego.util.StegoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/msg")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        User me = userService.findByUsername(username).orElse(null);
        User other = userService.findById(id).orElse(null);

        model.addAttribute("me", me);
        model.addAttribute("other", other);

        List<Message> inbox = messageService.conversation(other, me);
        model.addAttribute("inbox", inbox);

        return "chat";
    }

    @PostMapping("/send/{id}")
    public String sendMessage(@PathVariable Long id,
                              @RequestParam("image") MultipartFile image,
                              @RequestParam("text") String text,
                              HttpSession session) throws Exception {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        User sender = userService.findByUsername(username).orElse(null);
        User receiver = userService.findById(id).orElse(null);

        if (image.isEmpty()) throw new IllegalArgumentException("Image required");

        Files.createDirectories(Paths.get(uploadDir));

        String original = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path origPath = Paths.get(uploadDir).resolve("orig_" + original);
        Files.copy(image.getInputStream(), origPath, StandardCopyOption.REPLACE_EXISTING);

        Path outPath = Paths.get(uploadDir)
                .resolve("enc_" + original.replaceAll("\\.[^.]+$", "") + ".png");
        StegoUtil.encodeText(origPath.toFile(), outPath.toFile(), text);

        Message m = new Message();
        m.setSender(sender);
        m.setReceiver(receiver);
        m.setImagePath("/uploads/" + outPath.getFileName().toString());
        m.setSentAt(LocalDateTime.now());
        messageService.save(m);

        return "redirect:/msg/chat/" + id;
    }

    @GetMapping("/decodeMessage")
    @ResponseBody
    public String decodeMessage(@RequestParam("path") String path) throws Exception {
        try {
            // Handle both absolute and relative paths
            File f;
            if (path.startsWith("/uploads/")) {
                // Convert /uploads/filename to actual file path
                String filename = path.substring("/uploads/".length());
                f = new File(uploadDir, filename);
            } else if (path.startsWith("uploads/")) {
                f = new File(uploadDir, path.substring("uploads/".length()));
            } else {
                f = new File(path);
            }

            if (!f.exists()) {
                System.err.println("File not found at: " + f.getAbsolutePath());
                return "<div class='alert alert-warning'>⚠ File not found. Path: " + path + "</div>";
            }

            System.out.println("Decoding file: " + f.getAbsolutePath());
            String msg = StegoUtil.decodeText(f);
            
            if (msg == null || msg.isEmpty()) {
                return "<div class='alert alert-info'>⚠ No hidden message found in this image.</div>";
            }

            // Escape HTML to prevent XSS
            String safeMsg = msg.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
            return "<div class='alert alert-success'><strong>💬 Hidden Message:</strong><br>" + safeMsg + "</div>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<div class='alert alert-danger'>⚠ Error decoding message: " + e.getMessage() + "</div>";
        }
    }
}
