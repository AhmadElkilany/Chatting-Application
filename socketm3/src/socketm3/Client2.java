package socketm3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client2 {
	public static JLabel allnames = new JLabel();
	static boolean namevalid = true;
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String args[]) throws Exception {
		JFrame window = new JFrame();//create frame
		String server = "";
		
		while(true) {//get server user wants to join
			server = JOptionPane.showInputDialog(window, "Enter Server Number:", "Select Server",
					JOptionPane.INFORMATION_MESSAGE);
			if(server ==null || !(server.equals("1") || server.equals("2")))
				JOptionPane.showMessageDialog(window, "Choose either server 1 or server 2");
			else
				break;
		}
		int servernumber = Integer.parseInt(server);
		Socket s;
		int portID = 0;
		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("1 -> server 1 or 2-> server 2");
		//Scanner scn = new Scanner(System.in);
		//int c = scn.nextInt();
		if (servernumber == 1) {
			portID = 5443;
		} else if (servernumber == 2) {
			portID = 5445;
		}

		s = new Socket("localhost", portID);
		DataInputStream x = new DataInputStream(s.getInputStream());
		DataOutputStream y = new DataOutputStream(s.getOutputStream());
		String clientName = "";
			clientName = JOptionPane.showInputDialog(window, "Enter your name:", "Select Username",
					JOptionPane.INFORMATION_MESSAGE);
		y.writeUTF(clientName);
		if(x.readUTF().equals("alreadyhere")) {//get user name and exit if it's already taken
			JPanel temp = new JPanel();
			JOptionPane.showMessageDialog(temp, "This name is already taken!","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		else {
			
	
		window.setTitle(clientName);
		
		window.setSize(900, 600);
		window.getContentPane().setLayout(new GridLayout(2,2));
		JButton view = new JButton("View Members");
		JPanel[][] elements = new JPanel[2][2];//set the dimensions of the chatting window
		for(int i=0;i<2;i++) {
			for(int j=0;j<2;j++) {
				elements[i][j] = new JPanel();
				window.getContentPane().add(elements[i][j]);
			}
		}
		elements[0][1].add(view);//view all members
		elements[1][1].add(allnames);
		elements[0][0].setLayout(new GridLayout(4,2));
		JPanel[][] chatSection = new JPanel[4][2];
		for(int i=0;i<4;i++) {
			for(int j=0;j<2;j++) {
				chatSection[i][j] = new JPanel();
				elements[0][0].add(chatSection[i][j]);
			}
		}
		JLabel askMsg = new JLabel("Message");//get the message
		chatSection[0][0].add(askMsg);
		JTextField getmsg = new JTextField(20);
		chatSection[0][1].add(getmsg);
		String message = getmsg.getText();
		
		JLabel askTarget = new JLabel("To");//get the receiver
		chatSection[1][0].add(askTarget);
		JTextField getTarget = new JTextField(20);
		chatSection[1][1].add(getTarget);
		String target = getTarget.getText();
		
		JLabel askTTL = new JLabel("TTL");//get the ttl
		chatSection[2][0].add(askTTL);
		JTextField getTTL = new JTextField(20);
		chatSection[2][1].add(getTTL);
		String ttl = getTTL.getText();
		
		JLabel messagesReceived = new JLabel();//initialize the section containing received messages
		elements[1][0].add(messagesReceived);
		
		JButton send = new JButton("Send");
		chatSection[3][0].add(send);
		
		JButton quit = new JButton("Quit");
		chatSection[3][1].add(quit);
		
		
		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finish gui
		
		

		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					y.writeUTF("getusers");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread sending = new Thread(new Runnable() {
					public void run() {
						String msg = "quit";
						try {
							y.writeUTF(msg);
						} catch (IOException e) {
							e.printStackTrace();
						}
						int in = JOptionPane.showConfirmDialog(null, "You Logged Out!",
								"Goodbye", JOptionPane.DEFAULT_OPTION);
						if (in == 0) 
							System.exit(0);		
					}
				});
				sending.start();
				
			}
		});
		
		send.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Thread sending = new Thread(new Runnable() {
				public void run() {
						
					
						String msg = "";
						msg = getmsg.getText()+"-"+getTTL.getText()+"-"+getTarget.getText();
						
						try {
							y.writeUTF(msg);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
				}
				});
				sending.start();
				
			}
		});
		
		ArrayList<String> mymessages = new ArrayList<>();
		Thread reading = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						String msg = x.readUTF();
						if(msg.substring(0, 7).equals("xnamesx")) {
							String out = msg.substring(7);
							allnames.setText(out);
						}
						else {
							mymessages.add(msg);
							String out = "<html>";
							for(int i=0;i<mymessages.size();i++)
								out+=""+mymessages.get(i)+"<br/>";
							out+="</html>";
							messagesReceived.setText(out);
						}
					} catch (IOException e) {
						System.out.println("You logged out!");
						break;
					}
				}
			}
		});

		reading.start();

	}}
}
