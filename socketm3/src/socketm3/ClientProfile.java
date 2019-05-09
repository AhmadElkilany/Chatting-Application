package socketm3;

import java.io.DataInputStream; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

 
class ClientProfile implements Runnable 
{

    String name;
    DataInputStream x;
    DataOutputStream y;
    Socket connectionSocket;
    boolean isloggedin;
    public  Thread thread;
    boolean found;
    int id;
    static ArrayList<String>names= new ArrayList<String>();;
    DataInputStream x2;
    DataOutputStream y2;
    ArrayList<ClientProfile> passed ;
    static int count;
    @SuppressWarnings("static-access")
	public ClientProfile(int id,Socket connectionSocket, String name, DataInputStream x, DataOutputStream y,DataInputStream x2,DataOutputStream y2 ) {
    	
    	this.isloggedin=true;
    	thread = new Thread(this);
    	this.x = x;
        this.y = y;
        this.x2 =x2;
   
        
        this.id =id;
        	

     /*   if(id == 1){
   
        this.Sercp=passedArray;}
        else
        	this.Ocp = passedArray; */
        
     if(y2 != null){
        try {
		y2.writeUTF("");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	};}
        this.y2=y2;
        this.name = name;
        this.names.add(this.name);
        this.connectionSocket = connectionSocket;
        count++;
        thread.start();
    }
 
    @SuppressWarnings("static-access")
	@Override
    public void run() {
 
        String received;
        String reciever="";
        
        while (true) 
        {  
            try
            {   found = false;
                received = x.readUTF();
                 if(received.equals("quit")){
                	 this.isloggedin=false;
                	 this.connectionSocket.close();
                	 this.names.remove(this.name);
                	 if(Server.cp.contains(this))
                		 Server.cp.remove(this);
                	 else
                		 OtherServer.cp2.remove(this);
                     break;
                 }else{
                	 if(received.equals("getusers")) {
                		 String out = "xnamesx<html>";
                		 for(int i=0;i<Server.cp.size();i++) {
                			 if(!(Server.cp.get(i).name.equals(this.name)))
                				 out+=""+Server.cp.get(i).name+"<br/>";
                		 }
                		 for(int i=0;i<OtherServer.cp2.size();i++) {
                			 if(!(OtherServer.cp2.get(i).name.equals(this.name)))
                			 	out+=""+OtherServer.cp2.get(i).name+"<br/>";
                		 }
                		 out+="</html>";
                		 this.y.writeUTF(out);
                	 }
                	 else {
                		 
                	 
              //  System.out.println(received);
                
                String[] WholeMsg = received.split("-");
                String Msg="";
                int ttl=0;
                try{
                	  ttl =Integer.parseInt(WholeMsg[1]);
                     System.out.println(ttl);
                    Msg = WholeMsg[0];
                reciever = WholeMsg[2];
                }
                
                catch(Exception e){
                	
                	//System.out.println("Please write in this format for example 'hi-ttl-name' ");
                	
                 
                
                }
                
                if(ttl>0) {
                	if(ttl>=3 || this.id==1){
                		for(int i = 0;i<Server.cp.size() ;i++)
                		{
                			ClientProfile p = Server.cp.get(i);
                			if((this.names!=null)&&!(this.names.contains(p.name)))
                				this.names.add(p.name);
                			if (p.name.equals(reciever))  
                			{
                				p.y.writeUTF(this.name+" : "+Msg);
                				found = true;
                				break;
                				
                			}
                			
                			
                			
                		}}
                	
                	if(!found){ 
                		if(ttl>=3 || this.id==2){
                			for(int j=0;j<OtherServer.cp2.size() ;j++){
                				
                				ClientProfile p2 = OtherServer.cp2.get(j);
                				if((this.names!=null)&&!(this.names.contains(p2.name)))
                					this.names.add(p2.name);
                				if (p2.name.equals(reciever))  
                				{   
                					p2.y.writeUTF(this.name+" : "+Msg);
                					found = true;
                					break;
                					
                				}
                				
                			} } 
                	}
                }
                
            
              
            }} }catch (IOException e) {
                 
                e.printStackTrace();
            }
             
        }
        try
        { GetMembers();
        	this.x2.close();
            this.y2.close();
            this.x.close();
            this.y.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings("static-access")
	public void GetMembers(){
    	System.out.println(this.names);
    	
    	
    }
  

}
