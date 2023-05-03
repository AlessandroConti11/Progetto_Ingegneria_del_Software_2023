package it.polimi.ingsw.client.View;

public enum ColorCLI {
    //colore di fine stringa e di reset
    RESET ("\u001b[0m"),
    CLEAR ("\033[H\033[2J"),

    //lettere colorate
    BLACK ("\u001b[30m"),
    GREEN ("\u001B[38;5;46m"),
    YELLOW ("\u001B[38;5;220m"),
    BLUE ("\u001B[38;5;21m"),
    PINK ("\u001B[38;5;204m"),
    LIGHT_BLUE ("\u001B[38;5;39m"),
    WHITE ("\u001B[38;5;231m"),
    EMPTY ("\u001b[38;5;94m"),

    //solo colore di background
    NOTALLOWEDBG (""),
    GREENBG ("\u001B[48;5;46m"), //145;165;65
    YELLOWBG ("\u001B[48;5;220m"), //223;159;69
    BLUEBG ("\u001B[48;5;21m"), //0;103;105
    PINKBG ("\u001B[48;5;204m"), //204;77;124
    LIGHT_BLUEBG ("\u001B[48;5;39m"), //106;183;183
    WHITEBG ("\u001B[48;5;231m"), //237;226;191
    EMPTYBG ("\u001b[48;5;94m"),

    //Bold, UnderLine, Reversed
    BOLD ("\u001b[1m"),
    UNDERLINE ("\u001b[4m"),
    REVERSED ("\u001b[7m"),

    GREENBG1 ("\u001B[48;2;145;165;65m"), //145;165;65
    YELLOWBG1 ("\u001B[48;2;223;159;69m"), //223;159;69
    BLUEBG1 ("\u001B[48;2;0;103;105m"), //0;103;105
    PINKBG1 ("\u001B[48;2;204;77;124m"), //204;77;124
    LIGHT_BLUEBG1 ("\u001B[48;2;106;183;183m"), //106;183;183
    WHITEBG1 ("\u001B[48;2;237;226;191m"), //237;226;191
    ;


    private final String code;

    ColorCLI(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

//todo scegliere colore EMPTY
//todo scegliere colore NOTALLOWED
//todo provare a fare i quadrati --> BIOS esempio
//todo javadoc