package ru.git.lab.bot.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private Long chatId;
    private String text;
    private List<KeyboardButton> buttons;

    public static ChatResponseBuilder builder() {
        return new ChatResponseBuilder();
    }

    public Long getChatId() {
        return this.chatId;
    }

    public String getText() {
        return this.text;
    }

    public List<KeyboardButton> getButtons() {
        if (Objects.isNull(buttons)) {
            return new ArrayList<>();
        }
        return buttons;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class ChatResponseBuilder {
        private Long chatId;
        private String text;
        private List<KeyboardButton> buttons = new ArrayList<>();

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

        public ChatResponseBuilder buttons(List<KeyboardButton> buttons) {
            if (Objects.nonNull(buttons)) {
                this.buttons = buttons;
            }
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(this.chatId, this.text, this.buttons);
        }

        public String toString() {
            return "ChatResponse.ChatResponseBuilder(chatId=" + this.chatId + ", text=" + this.text + ", buttons=" + this.buttons + ")";
        }
    }
}
