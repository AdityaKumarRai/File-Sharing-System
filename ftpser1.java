import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
class ftpser1
{
public static void main(String ds[])
{
server3 s=new server3();
}
}


class server3 extends Thread
{
ServerSocket ser;
Socket cli;
static int no=0;
static Vector v1=new Vector(10);
Vector v=new Vector(10);
server3()
{
try
{
ser=new ServerSocket(143);
start();
//setDaemon(true);
}catch(Exception e){System.out.println(e);}
}

public void run()
{
while(true)
{
try
{
cli=ser.accept();
v.insertElementAt(new mul2(cli,this,no),no);
Runtime t=Runtime.getRuntime();
t.gc();
}catch(Exception e){System.out.println(e);}
}
}

void send(String msg)
{
for(int i=0;i<=no;i++)
{
mul2 m=(mul2)v.elementAt(i);
m.out1(msg);
}
}
void senderr(String msg)
{
for(int i=0;i<=no;i++)
{
mul2 m=(mul2)v.elementAt(i);
m.out1(msg);
}
}
void send1(String msg)
{
for(int i=0;i<no;i++)
{
mul2 m=(mul2)v.elementAt(i);
m.num=i;
m.out1(msg);
}
}
void send2(int no,String msg)
{
mul2 m=(mul2)v.elementAt(no);
m.out1(msg);
}
void send2sim(int no,byte msg[],int size)
{
mul2 m=(mul2)v.elementAt(no);
m.out2(msg,size);
}
void sendall(int n,String msg)
{
for(int i=0;i<no;i++)
{
if(i==n)
continue;
mul2 m=(mul2)v.elementAt(i);
m.out1(msg);
}
}
void exit1(int n)
{
String s=v1.elementAt(n)+"  exited123";
v.removeElementAt(n);
v1.removeElementAt(n);
Runtime t=Runtime.getRuntime();
t.gc();
no--;
send1(s);
}
}


class mul2 extends Thread
{
Socket client;
DataInputStream rin;
DataOutputStream out;
Statement stmt;
server3 ser;
String s;
String users;
byte b[]=new byte[200];
int num;
mul2(Socket cli,server3 ser,int num)
{
try
{
client=cli;
this.ser=ser;
this.num=num;
rin=new DataInputStream(cli.getInputStream());
out=new DataOutputStream(cli.getOutputStream());
start();
}catch(Exception e){System.out.println(e);}
}

public void run()
{
String msg="";
StringTokenizer str;
try
{
while(true)
{
byte b[]=new byte[200];
if(rin.read(b)>=1)
{
msg=new String(b).trim();
System.out.println("Hello");
System.out.println("Comming : "+msg);
if(msg.endsWith(" mustquit"))
{
str=new StringTokenizer(msg," ");
s=str.nextToken();
int in=server3.v1.indexOf(s);
System.out.println("The number is "+in);
ser.send2(in," mustquit");
} 
else
if(msg.indexOf("Theusername,")==0)
{
str=new StringTokenizer(msg," ");
str.nextToken();
String user=str.nextToken();
users="";
server3.v1.insertElementAt(user,server3.no);
System.out.println(" user "+user+" Connected... with no "+server3.no);
for(int i=0;i<=server3.no;i++)
users=users+server3.v1.elementAt(i)+" ";
ser.send(users+"allusers");
server3.no++;
}
else
if(msg.indexOf("transfering ")==0 || msg.indexOf("transfferingtoall ")==0)
{

str=new StringTokenizer(msg," ");
str.nextToken();
int size=Integer.parseInt(str.nextToken().trim());
String sender=str.nextToken().trim();

String all=str.nextToken("*");
String path=str.nextToken();
System.out.println("The size :"+size);
byte b1[]=new byte[size];
rin.readFully(b1,0,size);

System.out.println("came out");
StringTokenizer str1=new StringTokenizer(all," ");
System.out.println(path);
String mess=sender+" "+"sizel "+size+" "+path;
int i=1;
while(i<str1.countTokens())
{
int rec=server3.v1.indexOf(str1.nextToken().trim());
sending(rec,b1,mess);
}
}
else
if(msg.indexOf("nopath")>=0)
{
System.out.println("Entered in nopath");
str=new StringTokenizer(msg," ");
str.nextToken();
String rece=str.nextToken();
int x=server3.v1.indexOf(str.nextToken());
ser.send2(x,"nopath in "+rece);
}
if(msg.startsWith("theinstantmessage")||msg.startsWith("themessagetoall"))
{
str=new StringTokenizer(msg," ");
str.nextToken();
int n=server3.v1.indexOf(str.nextToken());
String msg1=str.nextToken("*");
System.out.println(n+"   "+msg1);
if(msg.startsWith("theinstantmessage"))
ser.send2(n,msg1);
else
if(msg.startsWith("themessagetoall"))
{System.out.println("For All users");ser.sendall(n,msg1);}
}
if(msg.indexOf("captured")>=0)
{
System.out.println("Entered in captured");
str=new StringTokenizer(msg," ");
str.nextToken();
String rece=str.nextToken();
int x=server3.v1.indexOf(str.nextToken());
ser.send2(x,"captured in "+rece);
}
}
}
}catch(Exception e) {
/*System.out.println(num);
System.out.println(e);*/
String sr=(String)server3.v1.elementAt(num);
System.out.println("\nThe user "+sr+" exited");
ser.exit1(num); }
}

void sending(int rec,byte b[],String mess)
{
try
{
byte z[]=mess.getBytes();
ser.send2sim(rec,z,z.length);
Thread.sleep(1000); //5000
FileOutputStream fout=new FileOutputStream("d:/ismaster/temp.mp3");
fout.write(b);
System.out.println(mess);
/*System.out.println("sender no"+sen);
System.out.println("rece no"+rec);*/
out.flush();
out.flush();
ser.send2sim(rec,b,b.length);
out.flush();
}catch(Exception e){System.out.println(e);}
}
void out1(String msg)
{
try{
b=msg.getBytes();
out.flush();
out.write(b);
out.flush();
}catch(Exception e){}
}
void out2(byte msg[],int size) 
{
try{
out.flush();
//System.out.println(new String(msg));
out.write(msg,0,size);
out.flush();
}catch(Exception e){}
}
} 

  
