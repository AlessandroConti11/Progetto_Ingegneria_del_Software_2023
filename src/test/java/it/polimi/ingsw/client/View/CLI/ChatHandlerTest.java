package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.utils.Chat;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

class ChatHandlerTest {
    @Mock
    private CLIMain cli;
    @Mock
    private ClientState clientState;
    @Mock
    private ReadShell readShell;
    @Mock
    private CLIPrint cliPrint;
    @Mock
    private ChatController chatController;
    @Captor
    private ArgumentCaptor<ChatMessage> chatMessageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void settingForPublicChat1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "Test message\n#exit";
        ChatMessage message = new ChatMessage(username, "Test message");

        // Converte la stringa di input in un oggetto InputStream
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Salva l'input attuale per poterlo ripristinare successivamente
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Imposta l'input simulato come l'input di sistema
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Ripristina l'input di sistema originale
            System.setIn(originalInputStream);
        }

        ChatMessage capturedMessage = chatMessageCaptor.getValue();

        Assertions.assertEquals(message, capturedMessage);
    }

    @Test
    void settingForPublicChat2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "#help\n#exit";

        // Converte la stringa di input in un oggetto InputStream
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Salva l'input attuale per poterlo ripristinare successivamente
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Imposta l'input simulato come l'input di sistema
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Ripristina l'input di sistema originale
            System.setIn(originalInputStream);
        }

        //Assertions.assertEquals(expectedOutput, output);
        verify(cliPrint).helpForChat();
    }

    @Test
    void settingForPublicChat3() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "#help\n#exit";

        // Converte la stringa di input in un oggetto InputStream
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Salva l'input attuale per poterlo ripristinare successivamente
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Imposta l'input simulato come l'input di sistema
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Ripristina l'input di sistema originale
            System.setIn(originalInputStream);
        }

        //Assertions.assertEquals(expectedOutput, output);
        verify(cliPrint).helpForChat();
    }

    @Test
    void settingForPrivateChat() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String username = "yoda";
        String input = "yoda\nTest message\n#exit";
        ChatMessage message = new ChatMessage(username, "Test message");

        // Converte la stringa di input in un oggetto InputStream
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Salva l'input attuale per poterlo ripristinare successivamente
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat("yoda", true);
        when(chatController.getPrivateChat("yoda")).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Imposta l'input simulato come l'input di sistema
            System.setIn(inputStream);

            chatHandler.settingForPrivateChat();
        } finally {
            // Ripristina l'input di sistema originale
            System.setIn(originalInputStream);
        }

        ChatMessage capturedMessage = chatMessageCaptor.getValue();

        Assertions.assertEquals(message, capturedMessage);
    }

    @Test
    void newPublicMessage1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username1 = "yoda";
        String username2 = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + ": may the force be with you\n";
        ChatMessage chatMessage = new ChatMessage(username1, message);

        publicChat.setChatIsEnable(true);

        // Setting Mock's methods
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username2);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPublicMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
    }

    @Test
    void newPublicMessage2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String message = "may the force be with you";
        String expectedOutput1 = "*One new message from the PUBLIC CHAT*\n*2 new messages from the PUBLIC CHAT*\n";
        ChatMessage chatMessage = new ChatMessage(username, message);

        publicChat.setChatIsEnable(false);

        // Setting Mock's methods
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPublicMessage(chatMessage);
        chatHandler.newPublicMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput1, output);
    }

    @Test
    void newPrivateMessage1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        // Setting Mock's methods
        when(chatController.getPrivateChat(receivingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(forwardingUsername);

        chatHandler.newPrivateMessage(chatMessage);

        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
    }

    @Test
    void newPrivateMessage2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + ": may the force be with you\n";
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        privateChat.setChatIsEnable(true);

        // Setting Mock's methods
        when(chatController.getPrivateChat(forwardingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(receivingUsername);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPrivateMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
    }

    @Test
    void newPrivateMessage3() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = "*One new message from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + "*\n" +
                                "*2 new messages from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + "*\n";
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        privateChat.setChatIsEnable(false);

        // Setting Mock's methods
        when(chatController.getPrivateChat(forwardingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(receivingUsername);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPrivateMessage(chatMessage);
        chatHandler.newPrivateMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(1));
    }
}