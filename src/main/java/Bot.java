import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Bot extends TelegramLongPollingBot {


    public void sendMsg(String text, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        if(update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMsg("Привет" + " "  + update.getMessage().getFrom().getFirstName(), update.getMessage());
                    break;
                case "/setting":
                    sendMsg("proverka", update.getMessage());
                    break;

                default:
                    try {
                        sendMsg(Weather.getWeather(model, update.getMessage().getText()), update.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List <KeyboardRow> key = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Ангарск"));
        keyboardFirstRow.add(new KeyboardButton("Иркутск"));
        keyboardFirstRow.add(new KeyboardButton("Москва"));
        keyboardFirstRow.add(new KeyboardButton("Санкт-Петербург"));


        key.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(key);

    }


    @Override
    public String getBotUsername() {
        // TODO
        return "kamikaze38ru_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5286488753:AAG0MOi9sgi7OWVEVn94tQ9nuoOuyFYXXVM";
    }
}