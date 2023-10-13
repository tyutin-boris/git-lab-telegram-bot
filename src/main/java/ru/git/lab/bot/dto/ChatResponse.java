package ru.git.lab.bot.dto;

import lombok.Data;

@Data
public class ChatResponse {

    private Long chatId;
    private String text;

    ChatResponse(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public static ChatResponseBuilder builder() {
        return new ChatResponseBuilder();
    }

    public static class ChatResponseBuilder {
        private Long chatId;
        private String text;

        ChatResponseBuilder() {
        }

        public ChatResponseBuilder chatId(Long chatId) {
            this.chatId = chatId;
            return this;
        }

        public ChatResponseBuilder text(String text) {
            this.text = text;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(chatId, text);
        }

        public String toString() {
            return "ChatResponse.ChatResponseBuilder(chatId=" + this.chatId + ", text=" + this.text + ")";
        }
    }
}
