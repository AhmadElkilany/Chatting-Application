package socketm3;

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class OtherServer extends Thread implements Runnable {

	ArrayList<ClientProfile> cpy = new ArrayList<>();

	static ArrayList<ClientProfile> cp2 = new ArrayList<>();
	static ArrayList<Server> ser = new ArrayList<>();

	@SuppressWarnings("resource")
	public void run() {

		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(5445);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataInputStream dis2 = null;
		DataOutputStream dos2 = null;

		Socket u;
		try {
			u = new Socket("localhost", 5443);
			dis2 = new DataInputStream(u.getInputStream());
			dos2 = new DataOutputStream(u.getOutputStream());

			dos2.writeUTF("s");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("Please Connect to Server1 for the first usage");
		}

		// }

		Socket s = null;
		while (true) {
			try {
				s = welcomeSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataInputStream dis = null;
			try {
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataOutputStream dos = null;
			try {
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				String name = dis.readUTF();
				if (!(name.equals("s"))) {
					boolean ishere = false;
					for(int i=0;i<Server.cp.size();i++)
						if(Server.cp.get(i).name.equals(name)) {
							ishere = true;
							break;
						}
					for(int i=0;i<cp2.size();i++)
						if(cp2.get(i).name.equals(name)) {
							ishere = true;
							break;
						}
					if(ishere) {
						dos.writeUTF("alreadyhere");
					}
					else {
						dos.writeUTF("ok");
						ClientProfile x = new ClientProfile(2, s, name, dis, dos, dis2, dos2);
						//System.out.println(x.name + " has joined the session");
						// System.out.println("You can chat with "+cp2.size() +" users from this server
						// and " + x.Sercp.size()+" users from server1");
						cp2.add(x);
						//System.out.println(cp2.size());
						//System.out.println(Server.cp.size());
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
