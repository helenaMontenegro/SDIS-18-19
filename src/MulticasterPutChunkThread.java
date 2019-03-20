import java.net.*;
import java.io.*;
import java.util.*;

public class MulticasterPutChunkThread implements Runnable {
    MulticastSocket mc_socket;
    private InetAddress mc_address;
    private int mc_port;
    private byte[] message;
    private int sent_messages_no;
    private Chunk chunk;

    MulticasterPutChunkThread(InetAddress mc_address, int mc_port, byte[] message, Chunk chunk) {
        this.mc_address = mc_address;
        this.mc_port = mc_port;
        this.mc_socket = mc_socket;
        this.message = message;
        this.sent_messages_no = 0;
        this.chunk = chunk;
    }

    @Override
    public void run(){
        while(this.chunk.get_curr_rep_degree() < this.chunk.get_rep_degree() && this.sent_messages_no < 5) {
            this.send_message();
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void send_message() {
        this.sent_messages_no++;
        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket sendPort = new DatagramPacket(this.message, this.message.length, this.mc_address, this.mc_port);
            socket.send(sendPort);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}