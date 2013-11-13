package ru.apache_maven;

import java.net.InetAddress;

public class MessageParser {
    protected InetAddress address;
    protected String toUser;
    protected String code;
    protected String data;

    public MessageParser() {

    }

    public MessageParser(InetAddress address) {
        this.address = address;
    }

    public void parse(Message msg) {
        System.out.println(msg.getText());
        this.code = msg.getCommand();
        this.data = msg.getText();
        if (!msg.isBroadCast())
            this.toUser = msg.getToUser();
        /*if (this.code.length() != 0) {
            System.out.println("SYSMSG");
            if (this.code.equalsIgnoreCase(String.valueOf(CommonEnum.NAME)))
                Roster.setName(address, this.data);
            if (this.code.equalsIgnoreCase(String.valueOf(CommonEnum.ALL)))
                System.out.println("ALL");
            if (this.code.equalsIgnoreCase(String.valueOf(CommonEnum.ATTACH)))
                System.out.print("ATTACH");
        } else {
            System.out.println("MESSAGE");
            //todo
        }     */
        TempSender.sendvia(msg);

    }

}
