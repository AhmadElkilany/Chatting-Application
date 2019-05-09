package socketm3;

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server extends Thread implements Runnable {
	public static ArrayList<ClientProfile> cp = new ArrayList<>();
	// ArrayList<Server> server = new ArrayList<>();

	@SuppressWarnings("resource")
	public void run() {

		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(5443);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataInputStream dis2 = null;
		DataOutputStream dos2 = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket s = null;
		while (true) {
			try {
				s = welcomeSocket.accept();
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Socket s2;
			try {
				s2 = new Socket("localhost", 5445);
				dis2 = new DataInputStream(s2.getInputStream());
				dos2 = new DataOutputStream(s2.getOutputStream());
				dos2.writeUTF("s");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				String name = dis.readUTF();
				if (!(name.equals("s"))) {
					boolean ishere = false;
					for(int i=0;i<cp.size();i++)
						if(cp.get(i).name.equals(name)) {
							ishere = true;
							break;
						}
					for(int i=0;i<OtherServer.cp2.size();i++)
						if(OtherServer.cp2.get(i).name.equals(name)) {
							ishere = true;
							break;
						}
					if(ishere) {
						dos.writeUTF("alreadyhere");
					}
					else {
						dos.writeUTF("ok");
						//System.out.println("Enter your username:");
						//Scanner scn = new Scanner(System.in);
						ClientProfile x = new ClientProfile(1, s, name, dis, dos, dis2, dos2);
						//System.out.println(x.name + " has joined the session");
						// System.out.println("You can chat with "+cp2.size() +" users from this server
						// and " + x.Sercp.size()+" users from server1");
						cp.add(x);
						//System.out.println(cp.size());
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
