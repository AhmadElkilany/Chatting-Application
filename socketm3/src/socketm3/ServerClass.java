package socketm3;




public class ServerClass {
	public static void main(String[]args){
		Server server1 =new Server();
		server1.start();

		OtherServer server2 = new OtherServer();
		server2.start();
		
	}
	
	public ServerClass() {
		Server server1 =new Server();
		server1.start();

		OtherServer server2 = new OtherServer();
		server2.start();
	}

}

