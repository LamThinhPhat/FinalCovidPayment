package SSLServer;

import PaymentFrame.PaymentFrame;
import table.payment_history;
import table.payment_user;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static PaymentFrame.PaymentFrame.def;

public class Server {
    static PaymentFrame frame;
    public static void main(String[] args) {
        final int portNumber = 33000;
        Server server = new Server(portNumber);
        server.run();
    }

    ServerSocket server;
    HashMap<String, Socket> clients;
    HashMap<String, InputStream> clientInputStreams;
    HashMap<String, BufferedReader> bufferedReaders;
    HashMap<String, OutputStream> clientOutputStreams;
    HashMap<String, PrintWriter> printWriters;

    Server(int portNumber) {
        server = null;
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("Cannot open socket." + e);
            System.exit(1);
        }
        System.out.println("ServerSocket is created" + server);

        clients = new HashMap<>();
        clientInputStreams = new HashMap<>();
        bufferedReaders = new HashMap<>();
        clientOutputStreams = new HashMap<>();
        printWriters = new HashMap<>();
        displayUI();

    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for connection request...");
                Socket client = server.accept();

                String threadName = "Client-" + (clients.size() + 1);
                clients.put(threadName, client);
                int clientNum=clients.size()+1;
                ThreadSendMessage threadSendMessage = new ThreadSendMessage(threadName, client);
                threadSendMessage.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized private static void displayUI() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame= new PaymentFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class ThreadSendMessage extends Thread {
        private final Socket client;
        private String username;
        private final String threadName;

        ThreadSendMessage(String name, Socket client) {
            super(name);
            this.threadName = name;
            this.client = client;
        }

        @Override
        public void run() {
            String host = client.getInetAddress().getHostAddress();
            int port = client.getPort();
            System.out.println(">>> Client(host: " + host + ", port: " + port + ") is connected");

            boolean isInitInputStream = false;
            boolean isInitOutputStream = false;
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;
            PrintWriter printWriter = null;
            try {
                inputStream = client.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                clientInputStreams.put(threadName, inputStream);
                bufferedReaders.put(threadName, bufferedReader);
                isInitInputStream = true;

                outputStream = client.getOutputStream();
                printWriter = new PrintWriter(outputStream, true);
                clientOutputStreams.put(threadName, outputStream);
                printWriters.put(threadName, printWriter);
                isInitOutputStream = true;

                String username = bufferedReader.readLine();

                if (username != null) {
                    this.username = username;
                    payment_user user=getDB.PaymentUser.FunctionPaymentUser.GetPaymentAccount(username);
                    JOptionPane.showMessageDialog(frame,username+" has connected");

                    while (true) {
                        String messageFromClient = bufferedReader.readLine();

                        if (messageFromClient != null) {
                            if (messageFromClient.trim().equalsIgnoreCase("{quit}")) {
                                break;
                            } else {
                                int balancepay = Integer.parseInt( messageFromClient);
                                System.out.println(balancepay);
                                user.setDebt(user.getDebt() - balancepay);
                                user.setBalance(user.getBalance() - balancepay);
                                getDB.PaymentUser.FunctionPaymentUser.UpdateDebtBalance(user);
                                getDB.PaymentHistory.FunctionPaymentHistory.UpdatePaymentHistory(user.getUsername(), balancepay, user.getBalance());

                                ArrayList<payment_history> ListHistory = getDB.PaymentHistory.FunctionPaymentHistory.GetAllHistory();
                                def.setRowCount(0);
                                for(payment_history i : ListHistory)
                                {
                                    def.addRow(new Object[] {
                                            i.getUsername(), i.getDate_create(), i.getDebt_pay(), i.getNew_balance()
                                    });
                                }

                                for (String name: printWriters.keySet()) {
                                    if (name.equals(this.threadName))
                                        printWriters.get(name).println("{success}");
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clients.remove(this.threadName);
                    System.out.println(">>> " + this.username + " disconnected");
                    client.close();

                    if (isInitInputStream) {
                        clientInputStreams.remove(this.threadName);
                        bufferedReaders.remove(this.threadName);
                        inputStream.close();
                        bufferedReader.close();
                    }
                    if (isInitOutputStream) {
                        clientOutputStreams.remove(this.threadName);
                        printWriters.remove(this.threadName);
                        outputStream.close();
                        printWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

