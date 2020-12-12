import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
class chatform1 implements Runnable
{
DataInputStream rin;
DataOutputStream out;
int n=0;
String user,mess;
Vector v=new Vector();
static int z=0;
byte b[]=new byte[50];
quitting q;
chatform1()
{
try
{
Socket cli=new Socket("10.8.1.15",143);
rin=new DataInputStream(cli.getInputStream());
out=new DataOutputStream(cli.getOutputStream());
InetAddress inet=InetAddress.getLocalHost();
user=inet.getHostAddress();
byte x[]=("Theusername, "+user).getBytes();
out.write(x,0,x.length);

         rin.read(b);
         String str=new String(b).trim();
        // System.out.println(str);

System.out.println("Type quit to exit from server");
Thread t=new Thread(this);
q=new quitting(out,user);
t.start();
}catch(Exception e){System.out.println("Server Presently Not Running");}

}
public void run()
{
String msg;
try
{
while(true)
{
byte b[]=new byte[200];
        if(rin.read(b)>=1)
        {
        msg=new String(b).trim();
       // System.out.println("Comming message :"+msg);

        if(msg.indexOf("sizel")>=1)
        {
        int flag=0;
        System.out.println(msg);
        q.suspend();
        StringTokenizer str=new StringTokenizer(msg," ");
        String sender=str.nextToken();
        str.nextToken();
        int size=Integer.parseInt(str.nextToken());
        byte b1[]=new byte[size];
        try
        {
        String pat=str.nextToken();
        System.out.println("\nA File with path ' "+pat+" ' is coming with size "+size+ " Bytes ");
        FileOutputStream fout=new FileOutputStream(pat);
        out.flush();
        double time=size/7000;
        System.out.println("\nThis may take "+time+" Seconds Minimum,\n Please wait...\n");

        rin.readFully(b1,0,size);
        fout.write(b1);
        System.out.println("File Captured");
        out.write(("captured "+" "+user+" "+sender).getBytes());
        q.resume();
        }catch(Exception e){flag=1;}

        if(flag==1)
        {
        out.flush();
        out.write(("nopath "+" "+user+" "+sender).getBytes());
        }
        }
        else
	if(msg.endsWith("%"))
        {
        StringTokenizer s1=new StringTokenizer(msg," ");
        String mess=s1.nextToken("%");
        new clientform(mess);
    	}
	else
        if(msg.endsWith("mustquit"))
        {
        System.out.println("You have been Forcibly removed from server");
        System.exit(0);
        }


        }


        
}
}catch(Exception e){System.out.println("anand2 "+e);}
}
public static void main(String ds[])
{
new chatform1();
}
}

class quitting extends Thread
{
DataOutputStream out;
BufferedReader kin;
String user;
quitting(DataOutputStream out,String user)
{
this.out=out;
this.user=user;
kin=new BufferedReader(new InputStreamReader(System.in));
start();
}
public void run()
{
while(true)
{
        try
        {

        String ls=kin.readLine();
 //       System.out.println(ls);
        if(ls.equalsIgnoreCase("quit"))
        {

        /*byte z[]=(user+" exited123 ").getBytes();
        out.flush();                          
        out.write(z);
        //setVisible(false);*/
        Runtime r=Runtime.getRuntime();
        Process p=null;
        p=r.exec("sub");
        p.waitFor();
        System.exit(1);
        
        }
        }catch(Exception e){System.out.println(e);}

}}
}
